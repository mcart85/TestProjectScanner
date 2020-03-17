package Items;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class TestClass {

    private String className;
    private String classPackage;
    private Map<String,String> parameters = new HashMap<String, String>();
    private Map<String, TestMethodObject> methodMap = new HashMap<>();

    public TestClass(String canonicalClassName) {
        int index = canonicalClassName.lastIndexOf(".");
        classPackage = (index >0) ? canonicalClassName.substring(0, index) : "";
        className = (index > 0) ? canonicalClassName.substring(index + 1) : canonicalClassName;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getClassName() {
        return className;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String,TestMethodObject> getMethodList() {
        return methodMap;
    }


    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json =  mapper.writeValueAsString( this );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
