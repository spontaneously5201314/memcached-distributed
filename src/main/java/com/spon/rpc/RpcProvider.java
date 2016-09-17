package com.spon.rpc;

import com.spon.rpc.service.HelloServiceImpl;

import java.io.IOException;

/**
 * Created by a on 2016/9/17.
 */
public class RpcProvider {
    public static void main(String[] args) throws IOException {
        HelloServiceImpl service = new HelloServiceImpl();
        RpcFrameWork.export(service, 1234);
    }
}
