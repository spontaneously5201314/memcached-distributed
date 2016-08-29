package com.youxigu.memcached.distributed.properties;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by a on 2016/8/27.
 */
public class LoadPropertiesTest {

    @Test
    public void testLoadProperties() throws Exception {
        new LoadProperties().loadProperties("/memcached.properties");
    }
}