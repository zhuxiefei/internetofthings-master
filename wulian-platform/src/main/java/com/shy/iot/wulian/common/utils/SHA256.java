package com.shy.iot.wulian.common.utils;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * SHA256工具类
 *
 * @author  wangl
 * @version  [版本号, 2017年2月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SHA256
{
    /**
     * 用SHA256算法生成安全签名
     * 
     * @param deviceKey 票据
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @param encrypt 密文
     * @return 安全签名
     * @throws Exception
     */
    public static String getSHA256(String deviceKey, String timestamp, String nonce, String encrypt)
        throws Exception
    {
        try
        {
            String[] array = new String[] {deviceKey, timestamp, nonce, encrypt};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < 4; i++)
            {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            
            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++)
            {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2)
                {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        }
        catch (Exception e)
        {
            throw new Exception("", e);
        }
    }
}
