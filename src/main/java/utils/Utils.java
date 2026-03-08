package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    private static ObjectMapper objectMapper;


    public static JsonNode readAsJson(String stringData) {
        objectMapper = new ObjectMapper();
        JsonNode data = null;
        try {
            data = objectMapper.readTree(stringData);

        } catch (IOException e) {
            System.out.println(e);
        }
        return data;
    }

    public static JsonNode readAsJsonResource(String resourcePath) {

        objectMapper = new ObjectMapper();

        try (InputStream is = Utils.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new RuntimeException("File not found in resources: " + resourcePath);
            }

            return objectMapper.readTree(is);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + resourcePath, e);
        }
    }

    public static <T> T deserialize(JsonNode Json, Class<T> modelClass) {
        try {
            objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, false);


            String JsonSTR = Json.toString();

            return objectMapper.readValue(JsonSTR, modelClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static <T> String serialize(T model) {
        try {
            objectMapper = new ObjectMapper();
            /*The following line excludes all default values that are set during deserialization,
            This leaves us with the attributes that are already in the payload that is deserialized! */
            objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
            objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_ABSENT);
            objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

            return objectMapper.writer().writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
