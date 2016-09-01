package com.youxigu.memcached.distributed.properties;

import org.junit.Test;

/**
 * Created by a on 2016/8/27.
 */
public class LoadPropertiesTest {

    @Test
    public void testLoadProperties() throws Exception {
        new LoadProperties().loadProperties("D:\\Develop\\IntelliJ IDEA 15.0.4\\workspace_idea\\memcached-distributed\\src\\test\\java\\com\\youxigu\\memcached\\distributed\\properties\\memcached.properties");
//        new LoadProperties().loadProperties("./memcached.properties");
    }
}