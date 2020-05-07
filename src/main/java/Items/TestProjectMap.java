package Items;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class TestProjectMap {
    public Map<String, TestClass> classList = new HashMap<String, TestClass>();

    public Map<String, TestClass> getClassList() {
        return classList;
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}


