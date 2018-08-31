package com.shy.iot.wulian.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 加解密工具类
 *
 * @author  wangl
 * @version  [版本号, 2017年3月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecretUtil
{
    private static Charset CHARSET = Charset.forName("utf-8");
    
    /**
     * 对明文进行加密
     */
    public static String encryptAES(String key, String message)
        throws Exception
    {
        ByteGroup byteCollector = new ByteGroup();
        byte[] textBytes = message.getBytes(CHARSET);
        byteCollector.addBytes(textBytes);
        // ... + pad: 使用自定义的填充方式对明文进行补位填
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);
        // 获得最终的字节流, 未加密
        byte[] unencrypted = byteCollector.toBytes();
        try
        {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            byte[] aesKey = Base64.decodeBase64(key + "==");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            // 加密
            byte[] encrypted = cipher.doFinal(unencrypted);
            // 使用BASE64对加密后的字符串进行编码
            Base64 base64 = new Base64();
            String base64Encrypted = base64.encodeToString(encrypted);
            return base64Encrypted;
        }
        catch (Exception e)
        {
            throw new Exception("", e);
        }
    }
    
    /**
     * 对密文进行解密.
     */
    public static String decryptAES(String key, String message)
        throws Exception
    {
        byte[] original;
        try
        {
            // 设置解密模式为AES的ECB模式
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            byte[] aesKey = Base64.decodeBase64(key + "==");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key_spec);
            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(message);
            // 解密
            original = cipher.doFinal(encrypted);
        }
        catch (Exception e)
        {
            throw new Exception("", e);
        }
        String xmlContent;
        try
        {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            xmlContent = new String(Arrays.copyOfRange(bytes, 0, bytes.length), CHARSET);
        }
        catch (Exception e)
        {
            throw new Exception("",e);
        }
        return xmlContent;
        
    }

    /** 
     * 数据加密
     * 对要发送的消息进行AES-ECB加密
     */
    public static String encrypt(String key, String message) throws Exception
    {
        // 加密
        String encrypt = encryptAES(key, message);
        String timeStamp = Long.toString(System.currentTimeMillis());
        String nonce = StringUtils.lowerCase(RandomStringUtils.randomAlphanumeric(6));
        String signature = SHA256.getSHA256(key, timeStamp, nonce, encrypt);
        JSONObject encryptMsgJson = new JSONObject();
        encryptMsgJson.put("msgContent", encrypt);
        encryptMsgJson.put("signature", signature);
        encryptMsgJson.put("timestamp", timeStamp);
        encryptMsgJson.put("nonce", nonce);
        return encryptMsgJson.toString();
    }

    /** 
     * 数据解密
     * @author  wangl
     * @version  [版本号, 2017年2月23日]
     * @param message 解密数据
     * @param key 密钥
     * @param timestamp 时间戳
     * @param nonce 随机码
     * @param sign 签名
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static String decrypt(String key, String timestamp, String nonce, String sign, String message)
        throws Exception
    {
        // 验证安全签名
        String signature = SHA256.getSHA256(key, timestamp, nonce, message);
        if (!signature.equals(sign))
        {
            throw new Exception("");
        }
        // 解密
        return decryptAES(key, message);
    }

/*    public static void main(String[] args) throws Exception{
        String msg = "UuowBL26N5JeYFkP74sBKTz7+71SHLU3wizBpZ8SPacqTYW30czMBJ+w/MknWKgIFUiyhyBsFKvieWlZZxZ1VwRcq+VZEFdu9kEcdaD90WuWN0vBE2KDU17pmNM1Wt5Qb4FSDIPcZktd3sxFpAqftNPM+2bTPLFowuFTrifUXjA=";
        String key = "esy7flqzfs9qsmc6pvjvl0";
        String nonce = "prbvvg";
        String timestamp = "1524029664055";
        String sign = "19a89242888378d02c4d5005696d0ba42dfe853b691447e8fee8370063c5bc07";
        String deMsg = decrypt(key,timestamp,nonce,sign,msg);
        System.out.println(deMsg);
    }*/
    public static String decrypt(String callbackString) throws Exception{
        JSONObject j = JSONObject.parseObject(callbackString);
        String timestamp = j.get("timestamp")==null?"":j.get("timestamp").toString();
        String nonce =j.get("nonce")==null?"":j.get("nonce").toString();
        String signature = j.get("signature")==null?"":j.get("signature").toString();
        String msgContent = j.get("msgContent")==null?"":j.get("msgContent").toString();
        String key = "esy7flqzfs9qsmc6pvjvl0"; //后期写成常量
        String decryptMsg = SecretUtil.decrypt(key,timestamp,nonce,signature,msgContent);
        return decryptMsg;
    }
}