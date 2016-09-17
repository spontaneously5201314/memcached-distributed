package com.spon.rpc;

import com.spon.rpc.service.HelloService;

/**
 * Created by a on 2016/9/17.
 */
public class RpcConsumer {
    public static void main(String[] args) throws Exception {
        HelloService service = RpcFrameWork.refer(HelloService.class, "127.0.0.1", 1234);
        for (int i = 0; i < 1024; i++) {
            String hello = service.hello("world " + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}
