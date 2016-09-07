package com.spon.memcached.servlet;

import com.spon.memcached.pool.MemcachedConnectionPool;
import net.spy.memcached.MemcachedClient;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by myg on 2016/8/31.
 */
public class DistributedServlet extends HttpServlet {

//    private

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        MemcachedConnectionPool instance = MemcachedConnectionPool.getInstance();
        List<MemcachedClient> allClients = instance.getAllClients();
        //TODO  在这里需要做的是类似proxool的那种可以给一个页面，查看现在管理的所有的连接情况
        //TODO  先做测试，就只写这一点东西
        out.println("<html><header><title>Distributed Admin</title></header><body BGCOLOR=\"#eeeeee\">");
        for (MemcachedClient client : allClients) {
            out.print("<p>" + client.toString() + "</p>");
        }
        out.print("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
