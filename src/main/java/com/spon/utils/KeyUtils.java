package com.spon.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by a on 2016/8/27.
 */
public final class KeyUtils {

    private KeyUtils(){

    }

    public static byte[] getKeyBytes(String k) {
        try {
            return k.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<byte[]> getKeyBytes(Collection<String> keys) {
        Collection<byte[]> rv = new ArrayList<byte[]>(keys.size());
        for (String s : keys) {
            rv.add(getKeyBytes(s));
        }
        return rv;
    }
}
