package com.youxigu.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by a on 2016/8/27.
 */
public class StringUtils {

    /**
     * 一个静态的模式仓库，减少Pattern的compile 次数
     */
    private static final Map<String, Pattern> patterns = new HashMap<String, Pattern>();
    static {
        patterns.put(",", Pattern.compile(","));
        patterns.put(":", Pattern.compile(":"));
        patterns.put(";", Pattern.compile(";"));
        patterns.put(".", Pattern.compile("\\."));
        patterns.put("\n", Pattern.compile("\n"));
        patterns.put("_", Pattern.compile("_"));
    }

    public static boolean match(String input, Pattern pattern) {
        Matcher m = pattern.matcher(input);
        return m.matches();
    }

    public static boolean match(String input, String regex) {
        Pattern pattern = patterns.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            patterns.put(regex, pattern);
        }
        Matcher m = pattern.matcher(input);
        return m.matches();
    }

    public static int getStringGBKLen(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return str.getBytes("GBK").length;
        } catch (Exception e) {
            return str.getBytes().length;
        }
    }

    public static String[] split(String source, String regex) {
        //这里缓存compile后的Pattern 应该是threadSafe的，我不是100%确认
        Pattern pattern = patterns.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            patterns.put(regex, pattern);
        }
        return pattern.split(source, 0);
    }

    public static int[] split2Int(String source, String regex) {
        Pattern pattern = patterns.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            patterns.put(regex, pattern);
        }
        String[] tmp = pattern.split(source, 0);
        int len = tmp.length;
        int[] datas = new int[len];
        for (int i = 0; i < len; i++) {
            datas[i] = Integer.parseInt(tmp[i]);
        }
        return datas;

    }

    public static Map<String, String> split2Map(String source, String regex) {
        Pattern pattern = patterns.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            patterns.put(regex, pattern);
        }
        String[] tmp = pattern.split(source, 0);
        int len = tmp.length;
        Map<String, String> datas = new HashMap<String, String>(len);
        for (String key : tmp) {
            datas.put(key, key);
        }
        return datas;

    }

    public static String join(Collection datas, String regex) {
        StringBuilder sb = new StringBuilder();
        int len = datas.size();
        for (Object data : datas) {
            sb.append(data);
            if (len != 1) {
                sb.append(regex);
            }
            len--;
        }
        return sb.toString();

    }
    public static String join(String[] datas, String regex) {
        StringBuilder sb = new StringBuilder();
        int len = datas.length;
        for (Object data : datas) {
            sb.append(data);
            if (len != 1) {
                sb.append(regex);
            }
            len--;
        }
        return sb.toString();

    }
    public static String join(long[] ids, String regex) {
        StringBuilder sb = new StringBuilder();
        int len = ids.length;
        for (long data : ids) {
            sb.append(data);
            if (len != 1) {
                sb.append(regex);
            }
            len--;
        }
        return sb.toString();

    }

    public static String join(int[] ids, String regex) {
        StringBuilder sb = new StringBuilder();
        int len = ids.length;
        for (int data : ids) {
            sb.append(data);
            if (len != 1) {
                sb.append(regex);
            }
            len--;
        }
        return sb.toString();

    }

    /**
     * 替换秒/分/小时,这里写的比较土鳖,需要好好考虑一下
     * @param cronExpress
     * @return
     */
    public static String replaceCropExpress(String cronExpress,
                                            String[] replExpress) {
        String[] express = cronExpress.split(" ");
        for (int i = 0; i < 3; i++) {
            if (replExpress != null && replExpress.length > i) {
                express[i] = replExpress[i];
            } else {
                express[i] = _replaceCropExpress(express[i]);
            }

        }
        StringBuilder sb = new StringBuilder(20);

        int len = express.length;
        for (String data : express) {
            sb.append(data);
            if (len != 1) {
                sb.append(" ");
            }
            len--;
        }
        return sb.toString();
    }

    private static String _replaceCropExpress(String tmp) {
        if ("*".equals(tmp)) {
            tmp = "0";
        } else {

            int pos = tmp.indexOf("-");
            if (pos > 0) {
                tmp = tmp.substring(0, pos);
            }
            pos = tmp.indexOf(",");
            if (pos > 0) {
                tmp = tmp.substring(0, pos);
            }
        }
        return tmp;
    }



}
