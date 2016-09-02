package com.spon.memcached.pool;

import com.spon.constants.Constants;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 主要是对在节点管理中添加的节点的同时，检测节点是否可以连接，并建立连接，对这些连接进行统一管理，并进行心跳，保证连接的可用
 * 这个类就写成懒汉式的单例
 * Created by myg on 2016/8/30.
 */
public class MemcachedConnectionPool {

    private static final Logger logger = LoggerFactory.getLogger(MemcachedConnectionPool.class);

    private SortedMap<Long, MemcachedClient> connetions = null;

    private static long KEEP_ALIVED_TIME = Constants.KEEP_ALIVED_TIME;

    private ScheduledThreadPoolExecutor stp = null;

    private static MemcachedConnectionPool pool;

    private MemcachedConnectionPool(){}

    public static synchronized MemcachedConnectionPool getInstance(){
        if(pool == null){
            pool = new MemcachedConnectionPool();
        }
        return pool;
    }

    public void init(){
        if(connetions == null){
            connetions = new TreeMap<Long, MemcachedClient>();
        }
        stp = new ScheduledThreadPoolExecutor(5);
        KeepAlivedTask task = new KeepAlivedTask();
        stp.schedule(task, KEEP_ALIVED_TIME, TimeUnit.SECONDS);
    }

    public void addNodeByKey(Long hash, String host, int port){
        MemcachedClient memcachedClient = null;
        try {
            memcachedClient = new MemcachedClient(new InetSocketAddress(host,port));
            if(connetions.containsKey(hash)){
                logger.info("add depulite nodes will not save the older node");
            }
            //TODO 用来测试连通性，这样应该是有问题的，如果要存储的就是这个值的话
            memcachedClient.add("test", 0, 1);
            connetions.put(hash, memcachedClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MemcachedClient getConnection(Long hash){
        SortedMap<Long, MemcachedClient> connectionsMap = connetions.tailMap(hash);
        if(connectionsMap == null || connectionsMap.size() == 0){
            return connectionsMap.get(connectionsMap.firstKey());
        }
        return connectionsMap.get(connectionsMap.firstKey());
    }

    public void testAllConnections(){
        Collection<MemcachedClient> clients = connetions.values();
        if(clients == null || clients.size() == 0){
            return;
        }
        for (MemcachedClient client : clients) {
            Object test = client.get("test");

            //TODO  先用这种方法定时测试连通性，后面想到好的方法再改动
            if(test == null || ((Integer) test) != 0){
                //说明已经断开，需要重新连接
                client.add("test", 0, 1);
            }
        }
    }

    /**
     * 需要一个方法，返回现有factory中管理的所有的连接
     */
    public List<MemcachedClient> getAllClients(){
        List<MemcachedClient> clients = null;
        Set<Map.Entry<Long, MemcachedClient>> entries = connetions.entrySet();
        if(connetions.size() != 0){
            clients = new ArrayList<>(connetions.size());
        }
        for (Map.Entry<Long, MemcachedClient> entry : entries) {
            MemcachedClient value = entry.getValue();
            if(value != null){
                clients.add(value);
            }
        }
        if(clients != null || clients.size() != 0){
            return clients;
        }
        return null;
    }

    /**
     * 使用ScheduledThreadPoolExecutor来做一个定时的任务
     */
   class KeepAlivedTask implements Runnable{
        @Override
        public void run() {
            MemcachedConnectionPool instance = MemcachedConnectionPool.getInstance();
            instance.testAllConnections();
        }
    }
}
