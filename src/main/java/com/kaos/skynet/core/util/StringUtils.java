package com.kaos.skynet.core.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.util.DigestUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class StringUtils {
    /**
     * gson包装器
     */
    final static GsonWrapper gsonWrapper = new GsonWrapper();

    /**
     * 空字符串
     */
    public final static String EMPTY = "";

    /**
     * 比较两个Integer对象是否相等
     * 
     * @param s1
     * @param s2
     * @return
     */
    public static boolean equals(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }

    /**
     * 比较两个Integer的大小
     * 
     * @param int1
     * @param int2
     * @param nullIsLess
     * @return
     */
    public static int compare(String s1, String s2, boolean nullIsLess) {
        if (s1 == s2) {
            return 0;
        }
        if (s1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (s2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return s1.compareTo(s2);
    }

    /**
     * 比较两个数大小
     * 
     * @param int1
     * @param int2
     * @return
     */
    public static int compare(String s1, String s2) {
        return compare(s1, s2, true);
    }

    /**
     * 复读
     * 
     * @param repeatChar
     * @return
     */
    public static String repeat(char repeatChar, Integer cnt) {
        char[] buf = new char[cnt];
        Arrays.fill(buf, repeatChar);

        return new String(buf);
    }

    /**
     * 左拼接字符
     * 
     * @param str     原始字符串
     * @param size    拼接后的长度
     * @param padChar 用于拼接的字符
     * @return
     */
    public static String leftPad(String str, int size, char padChar) {
        // 如果本就满足要求，直接返回
        if (str == null || str.length() >= size) {
            return str;
        }

        // 计算待补数量
        return repeat(padChar, size - str.length()).concat(str);
    }

    /**
     * 格式化
     * 
     * @param jsonStr
     * @return
     */
    public static String format(String jsonStr) {
        return gsonWrapper.format(jsonStr);
    }

    /**
     * base64加密
     * 
     * @param source
     * @return
     */
    public static String base64Encode(String source) {
        if (source == null) {
            return null;
        }

        try {
            return Base64.getEncoder().encodeToString(source.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * base64加密
     * 
     * @param source
     * @return
     */
    public static String base64Decode(String source) {
        if (source == null) {
            return null;
        }

        try {
            return new String(Base64.getDecoder().decode(source), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * MD5加密
     * 
     * @param source
     * @return
     */
    public static String md5DigestAsHex(String source) {
        if (source == null) {
            return null;
        }

        try {
            return DigestUtils.md5DigestAsHex(source.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
}
