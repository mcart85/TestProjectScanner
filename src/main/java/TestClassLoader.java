import java.io.*;

public class TestClassLoader extends ClassLoader {
    private String pathtobin;
    private String pathToProject;

    public TestClassLoader(String pathToProject, ClassLoader parent) {
        super(parent);
        this.pathToProject = pathToProject;
    }

    public void setPathToClass(String path) {
        this.pathtobin = path;
    }

    private String getFullClassName(String className) {
        String classPath = pathtobin.split("classes")[1];
        if (classPath != "\\") {
            //In case if class not in the package
            classPath = classPath.substring(1).replace(File.separator, ".");
        }
        return classPath + className;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte b[] = fetchClassFromFS(pathtobin + className + ".class");
            className = getFullClassName(className);
            return defineClass(className, b, 0, b.length);
        } catch (FileNotFoundException ex) {
            return super.findClass(className);
        } catch (IOException ex) {
            return super.findClass(className);
        }

    }

    private byte[] fetchClassFromFS(String path) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(new File(path));

        // Get the size of the file
        long length = new File(path).length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + path);
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;

    }
}





