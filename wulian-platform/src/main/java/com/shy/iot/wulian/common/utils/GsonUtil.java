/*
 *   ©2016 ALL Rights Reserved DHX
 *  　　   ┏┓   ┏┓
 *  　　 ┏━┛┻━━━┛┻━┓
 *   　　┃         ┃
 *   　　┃    ━    ┃
 *   　　┃  ┳┛ ┗┳  ┃
 *   　　┃         ┃
 *   　　┃    ┻    ┃
 *   　　┗━┓     ┏━┛
 *         ┃    ┃  Code is far away from bug with the animal protecting
 *         ┃    ┃    神兽保佑,代码无bug
 *         ┃    ┗━━━━━┓
 *         ┃          ┣┓
 *         ┃          ┏┛
 *         ┗┓┓┏━━━━┓┓┏┛
 *          ┃┫┫    ┃┫┫
 *          ┗┻┛    ┗┻┛
 *   ━━━━━━感觉萌萌哒━━━━━━
 *
 */

package com.shy.iot.wulian.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <p>
 * Gson转换工具
 * </p>
 * ClassName: GsonUtil <br/>
 * Author: Du.Hx  <br/>
 * Date: 2017/5/10 18:40 <br/>
 * Version: 1.0 <br/>
 */
public class GsonUtil {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting()
            .disableHtmlEscaping().create();

    /**
     * <p>
     * 将对象转换成string
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/10 18:42
     *
     * @param obj 转换对象
     * @return json字符串
     */
    public static String object2Gson(Object obj) {
        String str;
        str = gson.toJson(obj);
        return str;
    }

    /**
     * <p>
     * 将string转换成指定类型对象
     * </p>
     * Author: Du.hx <br/>
     * Date: 2017/5/10 18:42
     *
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
