package com.binovizer.redisson.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Map;

/**
 * The type StringSerializer
 *
 * @author Mohd Nadeem
 */
@UtilityClass
public class StringSerializer {

    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Serialize string.
     *
     * @param object the object
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public static String serialize(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
    /**
     * Deserialize t.
     *
     * @param <T>           the type parameter
     * @param data          the data
     * @param typeReference the type reference
     * @return the t
     * @throws IOException the io exception
     */
    public static <T> T deserialize(String data, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(data, typeReference);
    }

    /**
     * Deserialize t.
     *
     * @param <T>    the type parameter
     * @param data   the data
     * @param tClass the t class
     * @return the t
     * @throws IOException the io exception
     */
    public static <T> T deserialize(String data, Class<T> tClass) throws IOException {
        return objectMapper.readValue(data, tClass);
    }

    /**
     * Deserialize t.
     *
     * @param <T>    the type parameter
     * @param data   the data
     * @param tClass the t class
     * @return the t
     * @throws IOException the io exception
     */
    public static <T> T deserialize(Map<String,Object> data, Class<T> tClass) {
        return objectMapper.convertValue(data, tClass);
    }


    /**
     * Deserialize t.
     *
     * @param <T>    the type parameter
     * @param bytes  the bytes
     * @param tClass the t class
     * @return the t
     * @throws IOException the io exception
     */
    public static <T> T deserialize(byte[] bytes, Class<T> tClass) throws IOException {
        return objectMapper.readValue(bytes, tClass);
    }
}
