package com.shy.iot.wulian.common.utils;

//byte数组转换成十六进制字符串
public class Bytes2Hex {
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String bytesToHex(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for (byte b : bytes) { // 使用除与取余进行转换
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }

            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }

        return new String(buf);
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }
    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
    //16进制转10进制
    public static int hex2ten(String str){
        StringBuilder sb=new StringBuilder(str);
        sb.reverse();
        str=sb.toString().toUpperCase();
        int sum=0;
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;i++){
            if(chars[i]>='A'&&chars[i]<='F'){
                sum+=((int)chars[i]-55)* Math.pow(16,i);
            }else {
                sum+=((int)chars[i]-48)* Math.pow(16,i);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(hex2ten("45"));
    }
}
