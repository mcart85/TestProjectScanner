package Items;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

public class TestMethodObject {
    private TestClass classContainer;
    private String methodName;
    private String testName;
    private String description;
    private String className;
    private String classPackage;
    private Map<String,String> parameterNames;
    private String dependencies;
    private int id;

    public TestMethodObject(String methodName,TestClass  classContainer, int methodId) {
        this.methodName = methodName;
        this.classContainer = classContainer;
        className = classContainer.getClassName();
        classPackage = classContainer.getClassPackage();
        classContainer.getMethodList().put(methodName,this);
        id = methodId;
    }

    public void setParameterNames(String[] parameterNames) {
       Map<String,String> param = new HashMap();
       Arrays.stream(parameterNames).forEach(p-> param.put(p,""));
       this.parameterNames = param;
        classContainer.getParameters().putAll(param);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDependencies(String[] dependencies) {
        this.dependencies = Arrays.stream(dependencies).collect(Collectors.joining(","));
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }


    public String getClassName() {
        return className;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public String getDependencies() {
        return dependencies;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getTestName() {
        return testName;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getParameterNames() {
        return parameterNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
