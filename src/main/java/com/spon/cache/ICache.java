package com.spon.cache;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * 操作缓存的接口的定义，其中定义了所有操作
 * Created by myg on 2016/9/6.
 */
public interface ICache<K, V> {
    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value);

    /**
     * 保存有有效期的数据
     *
     * @param key
     * @param value
     * @param 有效期(取的是客户端时间)
     * @return
     */
    public V put(K key, V value, Date expiry);

    /**
     * 保存有有效期的数据
     *
     * @param key
     * @param value
     * @param 设置有效期为距离当前时间后TTL秒。
     * @return
     */
    public V put(K key, V value, int TTL);

    /**
     * 获取缓存数据
     *
     * @param key
     * @return
     */
    public V get(K key);

    /**
     * 移出缓存数据
     *
     * @param key
     * @return
     */
    public V remove(K key);

    /**
     * 删除所有缓存内的数据
     *
     * @return
     */
    public boolean clear();

    /**
     * 缓存数据数量（Memcached接口当前不支持）
     *
     * @return
     */
    public int size();

    /**
     * 缓存所有的key的集合
     *
     * @return
     */
    public Set<K> keySet();

    /**
     * 缓存的所有value的集合
     *
     * @return
     */
    public Collection<V> values();

    /**
     * 是否包含了指定key的数据
     *
     * @param key
     * @return
     */
    public boolean containsKey(K key);

    /**
     * 释放Cache占用的资源
     */
    public void destroy();

}
