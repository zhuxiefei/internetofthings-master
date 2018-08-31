package com.shy.iot.wulian.common.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class MessageUtil {
	private static String language = PropertiesUtil.getProperty("language");
    private static Properties _prop;

    /**
     * 读取配置文件
     * @param fileName
     */
    public static void readProperties(String fileName){
        try {
            InputStream in = MessageUtil.class.getResourceAsStream("/"+fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            _prop.load(bf);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 根据key读取对应的value
     * @param key
     * @return
     */
    public static String getMsg(String key){
    	if(_prop == null){
    		_prop = new Properties();
    		String file = "message/message_"+language+".properties";
    		readProperties(file);
    	}
        return _prop.getProperty(key);
    }
    public static void main(String[] args) {
    	String url =MessageUtil.getMsg("00000");
    	System.out.println(url);
	}
    
}
