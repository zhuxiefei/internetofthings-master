package com.shy.iot.wulian.common.utils;

import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MD5 {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MD5.class);
    public static String Md5(String message) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(message.getBytes());
            for (byte b : result) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }

        } catch (Exception e) {
            log.error("Md5加密异常",e);
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}

