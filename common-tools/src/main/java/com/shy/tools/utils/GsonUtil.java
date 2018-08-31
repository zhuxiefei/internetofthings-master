package com.shy.tools.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson转换工具
 * ClassName: GsonUtil <br/>
 * Version: 1.0 <br/>
 */
public class GsonUtil {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting()
            .disableHtmlEscaping().create();

    /**
     * 将对象转换成string
     * @param obj 转换对象
     * @return json字符串
     */
    public static String object2Gson(Object obj) {
        String str;
        str = gson.toJson(obj);
        return str;
    }

    /**
     * 将string转换成指定类型对象
     * @param str   json字符串
     * @param clazz 指定对象
     * @param <T>   对象类型
     * @return 转换后对象
     */
    public static <T> T gson2Object(String str, Class<T> clazz) {
        T t;
        t = gson.fromJson(str, clazz);
        return t;
    }
}
