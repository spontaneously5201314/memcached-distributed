package com.spon.memcached.distributed.node;

import com.spon.memcached.MemcachedStatus;
import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by a on 2016/8/27.
 */
public class MemcachedNode {
    private String address;
    private int port;
    private String status;

    public MemcachedNode(String address, int port) {
        this.address = address;
        this.port = port;
        this.status = MemcachedStatus.IDLE;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemcachedNode that = (MemcachedNode) o;

        if (port != that.port) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + port;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemcachedNode{" +
                "address='" + address + '\'' +
                ", port=" + port +
                ", status='" + status + '\'' +
                '}';
    }

    public static final MemcachedClient parseNodeToClient(MemcachedNode memcachedNode) throws IOException {
        MemcachedClient client = new MemcachedClient(new InetSocketAddress(memcachedNode.getAddress(), memcachedNode.getPort()));
        return client;
    }
}
