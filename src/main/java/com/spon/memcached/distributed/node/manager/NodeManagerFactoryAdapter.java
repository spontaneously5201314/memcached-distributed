package com.spon.memcached.distributed.node.manager;

import com.spon.utils.StringUtils;
import com.spon.memcached.distributed.node.MemcachedNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by myg on 2016/8/30.
 */
public abstract class NodeManagerFactoryAdapter implements NodeManagerFactory {

    private static final Logger logger = LoggerFactory.getLogger(NodeManagerFactoryAdapter.class);

    @Override
    public MemcachedNode parseKeyToNode(String key) {
        MemcachedNode memcachedNode = null;
        String[] strings = StringUtils.split(key, ":");
        if(strings == null || strings.length != 2){
            logger.error("parse MemcachedNode occur error, please check your properties file");
        }
        for (int i = 0; i < key.length(); i++) {
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
