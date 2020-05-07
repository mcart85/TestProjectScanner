public class Main {

    public static void main(String[] args) {
        ProjectSanner scanner = new ProjectSanner();
        String path = "C:\\Users\\DELL\\Desktop\\Interview Task\\PostCodesApp";
        scanner.scanProject(path);
        System.out.println(scanner.projectMap.toJsonString());
    }
}
