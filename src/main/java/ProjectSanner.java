import Items.TestClass;
import Items.TestMethodObject;
import Items.TestProjectMap;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProjectSanner {
    int methodId = 1000;
    public TestProjectMap projectMap = new TestProjectMap();


    public void scanProject(String pathToProject) {
        pathToProject = verifyPath(pathToProject);
        TestClassLoader loader = new TestClassLoader(pathToProject, ClassLoader.getSystemClassLoader());
        List<File> fileList = scanFolderRecursive(new ArrayList<File>(), new File(pathToProject));

        for (File file : fileList) {
            String fileName = file.getName();
            if (fileName.contains(".class")) {
                try {
                    String className = "" + fileName.split(".class")[0];
                    loader.setPathToClass(file.getParentFile().getAbsolutePath() + File.separator);
                    Class clazz = loader.loadClass(className);
                    if (clazz.isInterface()) {
                        continue;
                    }
                    TestClass testClass = new TestClass(clazz.getCanonicalName());
                    Method[] methods = clazz.getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.isAnnotationPresent(org.testng.annotations.Test.class)) {
                            projectMap.getClassList().put(clazz.getCanonicalName(), testClass);
                            TestMethodObject methodItem = new TestMethodObject(method.getName(), testClass, generate());
                            Annotation annotation = method.getAnnotation(org.testng.annotations.Test.class);
                            methodItem.setTestName(((Test) annotation).testName());
                            methodItem.setDescription(((Test) annotation).description());
                            methodItem.setDependencies(((Test) annotation).dependsOnMethods());
                            if (method.isAnnotationPresent(org.testng.annotations.Parameters.class)) {
                                methodItem.setParameterNames(method.getAnnotation(org.testng.annotations.Parameters.class).value());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }

    public String verifyPath(String path) {
        String separator = "\\";
        path = path.replace(separator, File.separator);
        path = (path.endsWith(File.separator)) ? path : path + File.separator;
        return path;
    }

    static List<File> scanFolderRecursive(List<File> fileList, File file) {
        if (file.isFile()) {
            if (file.getAbsolutePath().contains("target" + File.separator + "test-classes")) {
                fileList.add(file);
            }
        } else {
            File[] fileArray = file.listFiles();
            for (File fileItem : fileArray) {
                scanFolderRecursive(fileList, fileItem);
            }
        }
        return fileList;
    }

    public int generate() {
        return methodId++;
    }

}