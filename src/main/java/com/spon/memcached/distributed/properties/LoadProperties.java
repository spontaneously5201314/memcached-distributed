package com.spon.memcached.distributed.properties;

import com.spon.memcached.distributed.node.manager.NodeManagerFactory;
import com.spon.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class for load properties file that user must specified.
 * Now this class is deprecated
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
        //TODO  这种使用getResourceAsStream的方式我不会用，还有就是相对路径和绝对路径的问题我还没搞清楚
//        InputStream stream = Object.class.getResourceAsStream(filePath);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File(filePath));
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String serverIp = prop.getProperty("serverIp");
        if(serverIp == null || serverIp.length() == 0){
            logger.info("load memcached node properties has occur error");
        }
        String[] strings = StringUtils.split(serverIp, ",");

        //TODO  这个是正式的，和下面的那个只能存在一个
//        for (String key : strings) {
//            nodeManagerFactory.addNode(key);
//        }

        //TODO  这种是测试的时候为了方便写的
//        for (String s : strings) {
//            DefaultAlgorithm algorithm = new DefaultAlgorithm();
//            algorithm.setAlgorithmName("NATIVE_HASH");
//            ConsistentNodeManagerFactory factory;
//            factory = new ConsistentNodeManagerFactory(algorithm, "./memcached.properties");
//            factory.addNode(s);
//            factory.getNodeByKey(s).toString();
//        }
    }
}
