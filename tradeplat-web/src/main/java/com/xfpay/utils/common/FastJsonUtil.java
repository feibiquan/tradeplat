package com.xfpay.utils.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * fastjson工具类
 * @version:1.0.0
 */
public class FastJsonUtil {

    private static final SerializerFeature[] FEATURES = {

    };


    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj, FEATURES);
    }


    public static Object toBean(String jsonStr) {
        return JSON.parse(jsonStr);
    }

    public static <T> T toBean(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    // 转换为数组
    public static <T> Object[] toArray(String jsonStr) {
        return toArray(jsonStr, null);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    // 转换为List
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * json字符串转化为map
     * @param s
     * @return
     */
    public static Map toMap(String s) {
        return JSONObject.parseObject(s);
    }

    /**
     * 将map转化为string
     * @param m
     * @return
     */
    public static String mapToString(Map m) {
        return JSONObject.toJSONString(m);
    }


    public static <T>T MapToBean(Map m,Class<T> clazz){
        return toBean(mapToString(m),clazz);
    }

}
