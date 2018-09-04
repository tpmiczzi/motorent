package ua.motorent.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonHelper {

    public String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromString(String string, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(string, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + string + " cannot be transformed to Json object");
        }
    }

    public Object readPath(String json, String path) {
        return JsonPath.parse(json).read(path);
    }
}
