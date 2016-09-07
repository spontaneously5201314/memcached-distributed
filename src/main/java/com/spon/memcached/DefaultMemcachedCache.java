package com.spon.memcached;

import com.spon.memcached.distributed.algorithm.Algorithm;
import com.spon.memcached.distributed.node.MemcachedNode;
import com.spon.memcached.distributed.node.manager.NodeManagerFactory;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by myg on 2016/9/6.
 */
//TODO  这里到底是作为一个缓存还是作为一个费关系型数据库呢？先作为缓存
public class DefaultMemcachedCache implements IMemcachedCache<String, Object> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMemcachedCache.class);

    private NodeManagerFactory nodeManagerFactory = null;
    private Algorithm algorithm = null;
    private AtomicLong counter = new AtomicLong(0l);
    private Map<String, AtomicLong> map = new HashMap<>();

    public DefaultMemcachedCache(NodeManagerFactory nodeManagerFactory, Algorithm algorithm) {
        this.nodeManagerFactory = nodeManagerFactory;
        this.algorithm = algorithm;
    }

    @Override
    public Object get(String key, int localTTL){
        MemcachedNode node = nodeManagerFactory.getNodeByKey(key);
        MemcachedClient client = null;
        Object object = null;
        try {
             client = MemcachedNode.parseNodeToClient(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(client != null){
            object = client.get(key);
        }
        if(object != null){
            return object;
        }
        return null;
    }

    @Override
    public Object[] getMultiArray(String[] keys) {
        Object[] objs = new Object[keys.length];
        for (int i = 0; i < keys.length; i++) {
            objs[i] = get(keys[i], 0);
        }
        return objs;
    }

    @Override
    public Map<String, Object> getMulti(String[] keys) {
        Map<String, Object> map = new HashMap<>();
        for (String key : keys) {
            map.put(key, get(key, 0));
        }
        return map;
    }

    @Override
    public long incr(String key, long inc) {

        return 0;
    }

    @Override
    public long decr(String key, long decr) {
        return 0;
    }

    @Override
    public long addOrIncr(String key, long inc) {
        return 0;
    }

    @Override
    public long addOrDecr(String key, long decr) {
        return 0;
    }

    @Override
    public void storeCounter(String key, long count) {
        AtomicLong counter = map.get(key);
        if(counter == null){
            counter = new AtomicLong(0);
        }
        counter.getAndIncrement();
        map.put(key, counter);
    }

    @Override
    public long getCounter(String key) {
        return map.get(key).get();
    }

    @Override
    public Set<String> keySet(boolean fast) {

        return null;
    }

    @Override
    public MemcachedStatsSlab[] statsSlabs() {
        return new MemcachedStatsSlab[0];
    }

    @Override
    public MemcachedStatus[] stats() {
        List<MemcachedNode> allNodes = nodeManagerFactory.getAllNodes();
        MemcachedStatus[] statuses = new MemcachedStatus[allNodes.size()];
        for (int i = 0; i < allNodes.size(); i++) {
            MemcachedStatus status = new MemcachedStatus(allNodes.get(i).getStatus());
            statuses[i] = status;
        }
        return statuses;
    }

    @Override
    public Map statsItems() {

        return null;
    }

    @Override
    public MemcachedResponse statCacheResponse() {
        return null;
    }

    @Override
    public void setStatisticsInterval(long checkInterval) {

    }

    @Override
    public boolean add(String key, Object value) {
        return false;
    }

    @Override
    public boolean add(String key, Object value, Date expiry) {
        return false;
    }

    @Override
    public boolean replace(String key, Object value) {
        return false;
    }

    @Override
    public boolean replace(String key, Object value, Date expiry) {
        return false;
    }

    @Override
    public void asynPut(String key, Object value) {

    }

    @Override
    public void asynAddOrDecr(String key, long decr) {

    }

    @Override
    public void asynAddOrIncr(String key, long incr) {

    }

    @Override
    public void asynDecr(String key, long decr) {

    }

    @Override
    public void asynIncr(String key, long incr) {

    }

    @Override
    public void asynStoreCounter(String key, long count) {

    }

    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Override
    public Object put(String key, Object value, Date expiry) {
        return null;
    }

    @Override
    public Object put(String key, Object value, int TTL) {
        return null;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public Object remove(String key) {
        return null;
    }

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public boolean containsKey(String key) {
        return false;
    }

    @Override
    public void destroy() {

    }
}
