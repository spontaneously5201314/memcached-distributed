package com.spon.rpc.service;

/**
 * Created by a on 2016/9/17.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello" + name;
    }
}
