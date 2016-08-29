package com.youxigu.memcached.distributed.properties;

import com.youxigu.memcached.distributed.node.manager.NodeManagerFactory;
import com.youxigu.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class for load properties file that user must specified.
 * Created by a on 2016/8/27.
 */
public class LoadProperties {

    private static final Logger logger = LoggerFactory.getLogger(LoadProperties.class);

    private NodeManagerFactory nodeManagerFactory;

    public NodeManagerFactory getNodeManagerFactory() {
        return nodeManagerFactory;
    }

    public void setNodeManagerFactory(NodeManagerFactory nodeManagerFactory) {
        this.nodeManagerFactory = nodeManagerFactory;
    }

    public void loadProperties(String filePath){
        logger.info("start to load memcached node properties");
        Properties prop = new Properties();
        InputStream stream = Object.class.getResourceAsStream(filePath);
        try {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String serverIp = prop.getProperty("serverIp");
        if(serverIp == null || serverIp.length() == 0){
            logger.info("load memcached node properties has occur error");
        }
        String[] strings = StringUtils.split(serverIp, ",");

        for (String s : strings) {
            nodeManagerFactory.parseKeyToNode(s);
        }
    }
}
