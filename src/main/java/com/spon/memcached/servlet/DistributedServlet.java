package com.spon.memcached.servlet;

import com.spon.memcached.pool.MemcachedConnectionPool;
import net.spy.memcached.MemcachedClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by myg on 2016/8/31.
 */
public class DistributedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MemcachedConnectionPool instance = MemcachedConnectionPool.getInstance();
        List<MemcachedClient> allClients = instance.getAllClients();
        //TODO  在这里需要做的是类似proxool的那种可以给一个页面，查看现在管理的所有的连接情况
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
