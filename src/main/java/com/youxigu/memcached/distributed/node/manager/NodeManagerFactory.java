package com.youxigu.memcached.distributed.node.manager;

import com.youxigu.memcached.distributed.algorithm.Algorithm;
import com.youxigu.memcached.distributed.node.MemcachedNode;

/**
 * Created by a on 2016/8/27.
 */
public interface NodeManagerFactory {

    MemcachedNode parseKeyToNode(String key);

    MemcachedNode getNodeByKey(String key);

    void addNode(String key);

    void addNodeList(String... keys);

    void deleteNode(String key);

    void deleteNodeList(String... keys);

    void replaceNode(String oldKey, String newKey);
}
