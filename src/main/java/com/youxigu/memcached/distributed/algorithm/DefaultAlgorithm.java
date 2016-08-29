package com.youxigu.memcached.distributed.algorithm;

import com.youxigu.utils.KeyUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * Created by a on 2016/8/27.
 */
public class DefaultAlgorithm extends AlgorithmAdapter{

    private static final String NATIVE_HASH = "NATIVE_HASH";
    private static final String CRC_HASH = "CRC_HASH";
    private static final String FNV1_64_HASH = "FNV1_64_HASH";
    private static final String FNV1A_64_HASH = "FNV1A_64_HASH";
    private static final String FNV1_32_HASH = "FNV1_32_HASH";
    private static final String FNV1A_32_HASH = "FNV1A_32_HASH";
    private static final String KETAMA_HASH = "KETAMA_HASH";

    private static final long FNV_64_INIT = 0xcbf29ce484222325L;
    private static final long FNV_64_PRIME = 0x100000001b3L;
    private static final long FNV_32_INIT = 2166136261L;
    private static final long FNV_32_PRIME = 16777619;
    private static MessageDigest md5Digest = null;

    static {
        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
    }

    @Override
    public long hash(String k) {
        long rv = 0;
        int len = k.length();
        String algorithmName = this.getAlgorithmName();
        switch (algorithmName) {
            case NATIVE_HASH:
                rv = k.hashCode();
                break;
            case CRC_HASH:
                // return (crc32(shift) >> 16) & 0x7fff;
                CRC32 crc32 = new CRC32();
                crc32.update(KeyUtils.getKeyBytes(k));
                rv = (crc32.getValue() >> 16) & 0x7fff;
                break;
            case FNV1_64_HASH:
                // Thanks to pierre@demartines.com for the pointer
                rv = FNV_64_INIT;
                for (int i = 0; i < len; i++) {
                    rv *= FNV_64_PRIME;
                    rv ^= k.charAt(i);
                }
                break;
            case FNV1A_64_HASH:
                rv = FNV_64_INIT;
                for (int i = 0; i < len; i++) {
                    rv ^= k.charAt(i);
                    rv *= FNV_64_PRIME;
                }
                break;
            case FNV1_32_HASH:
                rv = FNV_32_INIT;
                for (int i = 0; i < len; i++) {
                    rv *= FNV_32_PRIME;
                    rv ^= k.charAt(i);
                }
                break;
            case FNV1A_32_HASH:
                rv = FNV_32_INIT;
                for (int i = 0; i < len; i++) {
                    rv ^= k.charAt(i);
                    rv *= FNV_32_PRIME;
                }
                break;
            case KETAMA_HASH:
                byte[] bKey = computeMd5(k);
                rv = ((long) (bKey[3] & 0xFF) << 24)
                        | ((long) (bKey[2] & 0xFF) << 16)
                        | ((long) (bKey[1] & 0xFF) << 8)
                        | (bKey[0] & 0xFF);
                break;
            default:
                assert false;
        }
        return rv & 0xffffffffL; /* Truncate to 32-bits */

    }

    public static byte[] computeMd5(String k) {
        MessageDigest md5;
        try {
            md5 = (MessageDigest) md5Digest.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone of MD5 not supported", e);
        }
        md5.update(KeyUtils.getKeyBytes(k));
        return md5.digest();
    }
}
