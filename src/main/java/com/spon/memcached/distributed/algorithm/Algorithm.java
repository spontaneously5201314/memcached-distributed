package com.spon.memcached.distributed.algorithm;

/**
 * Created by a on 2016/8/27.
 */
public interface Algorithm {

    /**
     * 根据key找到对应的memcached服务器
     * @param key
     * @return
     */
    public long hash(String key);
}
