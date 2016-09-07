package com.spon.memcached;

import java.io.Serializable;

/**
 * Created by myg on 2016/9/6.
 */
public class MemcachedStatus implements Serializable {

    private String status;

    public static final String IDLE = "IDLE";

    public MemcachedStatus() {
        this.status = IDLE;
    }

    public MemcachedStatus(String status) {
        this.status = status;
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

        MemcachedStatus that = (MemcachedStatus) o;

        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override
    public int hashCode() {
        return status != null ? status.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MemcachedStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
