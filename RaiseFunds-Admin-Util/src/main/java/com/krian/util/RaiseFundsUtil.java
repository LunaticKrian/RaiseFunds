package com.krian.util;

import com.krian.constant.RaiseFundsConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RaiseFundsUtil {

    /**
     * 判断当前请求是否为AjAX请求
     *
     * @param request
     * @return
     */
    public static boolean judgeRequestType(HttpServletRequest request) {

        // 1.获取请求消息头：
        String acceptHeader = request.getHeader("Accept");
        String XRequestHeader = request.getHeader("X-Requested-With");

        // 2.判断是否为AJAX请求：
        return (acceptHeader != null && acceptHeader.contains("application/json")) || (XRequestHeader != null && XRequestHeader.equals("XMLHttpRequest"));
    }

    /**
     * 对明文字符串进行md5加密
     *
     * @param source 需要被加密的明文字符串
     * @return 密文
     */
    public static String md5(String source){
        // 1.判断source是否有效：
        if(source == null || source.length() == 0){
            throw new RuntimeException(RaiseFundsConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 2.获取MessageDigest对象，JDK中用来执行加密算法的类：
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 4.获取明文字符串对应的字节数组：
            byte[] bytes = source.getBytes(StandardCharsets.UTF_8);

            // 5.执行加密：
            byte[] output = messageDigest.digest(bytes);

            // 6.创建BigInteger对象：
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);

            // 7.按照16进制将bigInteger的值转换为字符串：
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            // 8.返回密文：
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
