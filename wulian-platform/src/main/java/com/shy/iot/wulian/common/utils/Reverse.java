package com.shy.iot.wulian.common.utils;

//用于十六进制高低位反转：低字节在前
public class Reverse {
    //四字节16进制取反,1字节2个16进制字符串
    public static String reverse4(String str) {
        str=str.toUpperCase();
        StringBuffer stringBuffer = new StringBuffer();
        String str1 = str.substring(0, 2);
        String str2 = str.substring(2, 4);
        String str3 = str.substring(4, 6);
        String str4 = str.substring(6, 8);
        stringBuffer.append(str4);
        stringBuffer.append(str3);
        stringBuffer.append(str2);
        stringBuffer.append(str1);
        return stringBuffer.toString();
    }
    //二字节16进制取反，1字节2个16进制字符串
    public static String reverse2(String str){
        str=str.toUpperCase();
        StringBuffer sb=new StringBuffer();
        String str1=str.substring(0,2);
        String str2=str.substring(2,4);
        sb.append(str2);
        sb.append(str1);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(reverse4("1121f404"));
        System.out.println(reverse2("7074"));
    }
}