package com.youxigu.memcached.distributed.algorithm;

/**
 * Created by a on 2016/8/27.
 */
public abstract class AlgorithmAdapter implements Algorithm {

    protected String algorithmName;

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
