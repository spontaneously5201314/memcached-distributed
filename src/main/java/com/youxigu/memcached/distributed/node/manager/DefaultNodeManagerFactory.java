package com.youxigu.memcached.distributed.node.manager;

import com.youxigu.memcached.distributed.algorithm.Algorithm;
import com.youxigu.memcached.distributed.node.MemcachedNode;
import com.youxigu.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by a on 2016/8/27.
 */
public class DefaultNodeManagerFactory implements NodeManagerFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultNodeManagerFactory.class);

    private Map<Long, MemcachedNode> memcachedNodeMap = new ConcurrentHashMap<Long, MemcachedNode>();

    private Algorithm algorithm;

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public MemcachedNode getNodeByKey(String key) {
        MemcachedNode memcachedNode = memcachedNodeMap.get(algorithm.hash(key));
        if(memcachedNode != null){
            return memcachedNode;
        }else{
            logger.info("No memcachedNode found");
            return null;
        }
    }

    @Override
    public void addNode(String key) {
        if(key == null){
            logger.info("please check your memcachednode properties for ensure host and port is not null");
        }
        long hashKey = algorithm.hash(key);
        MemcachedNode node = parseKeyToNode(key);
        if(memcachedNodeMap.get(hashKey) != null){
            //TODO  这里还需要做一些事情，不然会导致数据不一致
        }
        memcachedNodeMap.put(hashKey, node);
    }

    @Override
    public void addNodeList(String... keys) {
        for (String key : keys) {
            addNode(key);
        }
    }

    @Override
    public void deleteNode(String key) {
        if(key == null){
            logger.info("please check your memcachednode properties for ensure host and port is not null");
        }
        long hashKey = algorithm.hash(key);
        MemcachedNode node = parseKeyToNode(key);
        if(memcachedNodeMap.get(hashKey) != null){
            //TODO  在删除一个节点之前需要把数据移走
        }
        memcachedNodeMap.remove(hashKey);
    }

    @Override
    public void deleteNodeList(String... keys) {
        for (String key : keys) {
            deleteNode(key);
        }
    }

    @Override
    public void replaceNode(String oldKey, String newKey) {
        if(oldKey == null || newKey == null){
            logger.info("please check your memcachednode properties for ensure both host and port is not null");
        }
        long oldHash = algorithm.hash(oldKey);
        if(memcachedNodeMap.containsKey(oldHash)){
            deleteNode(oldKey);
        }
        //替换node
        memcachedNodeMap.put(algorithm.hash(newKey), parseKeyToNode(newKey));
    }

    @Override
    public MemcachedNode parseKeyToNode(String key) {
        MemcachedNode memcachedNode = null;
        String[] strings = StringUtils.split(key, ":");
        if(strings == null || strings.length != 2){
            logger.error("parse MemcachedNode occur error, please check your properties file");
        }
        for (int i = 0; i < strings.length; i++) {
            String host = strings[0];
            String[] split = StringUtils.split(host, ":");
            if(split == null || split.length != 4){
                logger.error("parse MemcachedNode occur error, please check your hostName");
            }
            int port = Integer.valueOf(strings[1]);
            if(port < 0 || port > 65535){
                logger.error("parse MemcachedNode occur error, please check your port");
            }
            memcachedNode = new MemcachedNode(host, port);
        }
        return memcachedNode;
    }
}
