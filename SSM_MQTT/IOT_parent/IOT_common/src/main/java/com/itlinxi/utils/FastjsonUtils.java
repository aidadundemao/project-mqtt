package com.itlinxi.utils;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FastjsonUtils {

    public static ParameterizedType makeJavaType(Type rawType, Type... typeArguments) {
        return new ParameterizedTypeImpl(typeArguments, null, rawType);
    }

    public static String toString(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return toJSONString(value);
    }

    public static String toJSONString(Object value) {
        return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String toPrettyString(Object value) {
        return JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect
                , SerializerFeature.PrettyFormat);
    }

    public static Object fromJavaObject(Object value) {
        Object result = null;
        if (Objects.nonNull(value) && (value instanceof String)) {
            result = parseObject((String) value);
        } else {
            result = JSON.toJSON(value);
        }
        return result;
    }

    public static Object parseObject(String content) {
        /*
        try {
            return JSON.parseObject(content);
        } catch (JSONException ex) {
            Throwable cause = ex.getCause();
            if (Objects.nonNull(cause)
                    && cause instanceof ClassCastException
                    && cause.getMessage().contains("JSONArray")) {
                return JSON.parseArray(content);
            }
            throw ex;
        }
        */
        return JSON.parseObject(content, Object.class);
    }

    public static Object getJsonElement(JSONObject node, String name) {
        return node.get(name);
    }

    public static Object getJsonElement(JSONArray node, int index) {
        return node.get(index);
    }

    public static <T> T toJavaObject(JSON node, Class<T> clazz) {
        return node.toJavaObject(clazz);
    }

    public static <T> T toJavaObject(JSON node, Type type) {
        return node.toJavaObject(type);
    }

    public static <T> T toJavaObject(JSON node, TypeReference<T> typeReference) {
        return node.toJavaObject(typeReference);
    }

    public static <E> List<E> toJavaList(JSON node, Class<E> clazz) {
        return node.toJavaObject(new TypeReference<List<E>>(clazz){});
    }

    public static List<Object> toJavaList(JSON node) {
        return node.toJavaObject(new TypeReference<List<Object>>(){});
    }

    public static <V> Map<String, V> toJavaMap(JSON node, Class<V> clazz) {
        return node.toJavaObject(new TypeReference<Map<String, V>>(clazz){});
    }

    public static Map<String, Object> toJavaMap(JSON node) {
        return node.toJavaObject(new TypeReference<Map<String, Object>>(){});
    }

    public static <T> T toJavaObject(String content, Class<T> clazz) {
        return JSON.parseObject(content, clazz);
    }

    public static <T> T toJavaObject(String content, Type type) {
        return JSON.parseObject(content, type);
    }

    public static <T> T toJavaObject(String content, TypeReference<T> typeReference) {
        return JSON.parseObject(content, typeReference);
    }

    public static <E> List<E> toJavaList(String content, Class<E> clazz) {
        return JSON.parseObject(content, new TypeReference<List<E>>(clazz){});
    }

    public static List<Object> toJavaList(String content) {
        return JSON.parseObject(content, new TypeReference<List<Object>>(){});
    }

    public static <V> Map<String, V> toJavaMap(String content, Class<V> clazz) {
        return JSON.parseObject(content, new TypeReference<Map<String, V>>(clazz){});
    }

    public static Map<String, Object> toJavaMap(String content) {
        return JSON.parseObject(content, new TypeReference<Map<String, Object>>(){});
    }

}
