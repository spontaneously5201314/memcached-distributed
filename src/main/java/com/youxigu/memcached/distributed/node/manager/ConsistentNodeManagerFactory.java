package com.youxigu.memcached.distributed.node.manager;

import com.youxigu.memcached.distributed.algorithm.Algorithm;
import com.youxigu.memcached.distributed.node.MemcachedNode;
import com.youxigu.memcached.pool.MemcachedConnectionPool;
import com.youxigu.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by myg on 2016/8/30.
 */
public final class ConsistentNodeManagerFactory extends NodeManagerFactoryAdapter{
    private static final Logger logger = LoggerFactory.getLogger(ConsistentNodeManagerFactory.class);

    private TreeMap<Long, MemcachedNode> nodeMap = null;//虚拟节点
    private List<MemcachedNode> shards = new ArrayList<>(); // 真实机器节点
    private List<MemcachedNode> backup = new ArrayList<>(); // 备份节点
    private final int NODE_NUM = 100; // 每个机器节点关联的虚拟节点个数

    private static final String SPLIT_1 = ",";
    private Algorithm algorithm = null;
    private String propertiesFilePath = null;

    public ConsistentNodeManagerFactory(Algorithm algorithm, String propertiesFilePath) {
        super();
        this.algorithm = algorithm;
        this.propertiesFilePath = propertiesFilePath;
        init();
    }

    private void init(){// 初始化一致性hash环
        logger.info("Start to init the consistent hash circle");
        nodeMap = new TreeMap<Long, MemcachedNode>();

        Properties prop = new Properties();
        //TODO  这种使用getResourceAsStream的方式我不会用，还有就是相对路径和绝对路径的问题我还没搞清楚
//        InputStream stream = Object.class.getResourceAsStream(propertiesFilePath);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File(propertiesFilePath));
            prop.load(stream);
        } catch (IOException e) {
            logger.error("load properties has occur error because of io error");
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String serverIp = prop.getProperty("serverIp");
        if(serverIp == null || serverIp.length() == 0){
            logger.info("load node properties has occur error");
        }
        String[] strings = StringUtils.split(serverIp, SPLIT_1);
        for (String key : strings) {
            final MemcachedNode node = parseKeyToNode(key);
            // 每个真实机器节点都需要关联虚拟节点
            for (int n = 0; n < NODE_NUM; n++) {
                // 一个真实机器节点关联NODE_NUM个虚拟节点
                nodeMap.put(algorithm.hash(node.toString() + n), node);
            }
            //建立起对每个node的连接，可以放在一个连接池中
            MemcachedConnectionPool connectionPool = MemcachedConnectionPool.getInstance();
            connectionPool.addNodeByKey(algorithm.hash(key), node.getAddress(), node.getPort());
        }
    }

    @Override
    public MemcachedNode getNodeByKey(String key) {
        long hash = algorithm.hash(key);
        MemcachedNode node = nodeMap.get(hash);
        if(node != null){
            return node;
        }
        return null;
    }

    public MemcachedNode getShardInfo(String key) {
        SortedMap<Long, MemcachedNode> tail = nodeMap.tailMap(algorithm.hash(key)); // 沿环的顺时针找到一个虚拟节点
        if (tail.size() == 0) {
            return nodeMap.get(nodeMap.firstKey());
        }
        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
    }

    @Override
    public void addNode(String key) {
        MemcachedNode node = parseKeyToNode(key);
        shards.add(node);//增加真实节点
        for (int n = 0; n < NODE_NUM; n++)
            // 一个真实机器节点关联NODE_NUM个虚拟节点
            nodeMap.put(algorithm.hash(node.toString() + n), node);
    }

    @Override
    public void addNodeList(String... keys) {
        List<String> list = Arrays.asList(keys);
        for (String key : list) {
            addNode(key);
        }
    }

    @Override
    public void deleteNode(String key) {
        MemcachedNode node = parseKeyToNode(key);
        shards.remove(node);
        for(int n = 0; n < NODE_NUM; n++)
            nodeMap.remove(algorithm.hash(key) + n);
    }

    @Override
    public void deleteNodeList(String... keys) {
        for (String key : keys) {
            deleteNode(key);
        }
    }

    @Override
    public void replaceNode(String oldKey, String newKey) {
        deleteNode(oldKey);
        addNode(newKey);
    }

    public void removeBackUp(String key){
        backup.remove(parseKeyToNode(key));
    }

    public void addBackUp(String key){
        backup.add(parseKeyToNode(key));
    }
}
