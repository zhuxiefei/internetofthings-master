package com.shy.tools.utils;

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

        return new String(buf).toLowerCase();
    }

    public static byte[] hexStringToByte(String str) {
        /*int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;*/
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
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
//        System.out.println(Arrays.toString(hexStringToByte("04001F00")));
//        System.out.println(bytesToHex(new byte[]{4, 0, 31, 0}));
        //System.out.println("1f 00 0a 00 19 00 4c 6f 67 69 6e 20 4f 4b 21 0d 0a 43 6f 6e 74 72 6f 6c 20 4d 6f 64 65 0d 0a".replace(" ",""));
//        System.out.println(Arrays.toString(hexStringToByte("400050003a005b46656269742046436c6f7564205365727665722056322e302e305d0d0a5b4e6f726d616c20536f636b6574204d6f64655d0d0a4c6f67696e3a")));
//        System.out.println(bytesToHex(new byte[]{64, 0, 80, 0, 58, 0, 91, 70, 101, 98, 105, 116, 32, 70, 67, 108, 111, 117, 100, 32, 83, 101, 114, 118, 101, 114, 32, 86, 50, 46, 48, 46, 48, 93, 13, 10, 91, 78, 111, 114, 109, 97, 108, 32, 83, 111, 99, 107, 101, 116, 32, 77, 111, 100, 101, 93, 13, 10, 76, 111, 103, 105, 110, 58}));
//        System.out.println("40 00 50 00 3a 00 5b 46 65 62 69 74 20 46 43 6c 6f 75 64 20 53 65 72 76 65 72 20 56 32 2e 30 2e 30 5d 0d 0a 5b 4e 6f 72 6d 61 6c 20 53 6f 63 6b 65 74 20 4d 6f 64 65 5d 0d 0a 4c 6f 67 69 6e 3a".replace(" ",""));
    }
}
