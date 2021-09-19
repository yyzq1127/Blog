package com.yk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * JacksonUtil工具类
 * 类型转换工具类
 * @author yk
 * @version 1.0
 * @date 2021/4/2 20:41
 */
public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T readValue(InputStream src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }
}
