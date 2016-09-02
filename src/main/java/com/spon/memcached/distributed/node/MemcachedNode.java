package com.spon.memcached.distributed.node;

/**
 * Created by a on 2016/8/27.
 */
public class MemcachedNode {
    private String address;
    private int port;

    public MemcachedNode(String address, int port) {
        this.address = address;
        this.port = port;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemcachedNode that = (MemcachedNode) o;

        if (getPort() != that.getPort()) return false;
        return getAddress().equals(that.getAddress());

    }

    @Override
    public int hashCode() {
        int result = getAddress().hashCode();
        result = 31 * result + getPort();
        return result;
    }

    @Override
    public String toString() {
        return "MemcachedNode{" +
                "address='" + address + '\'' +
                ", port=" + port +
                '}';
    }
}
