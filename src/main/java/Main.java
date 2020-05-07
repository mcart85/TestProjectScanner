public class Main {

    public static void main(String[] args) {
        ProjectSanner scanner = new ProjectSanner();
        scanner.scanProject(args[0]);
        System.out.println(scanner.projectMap.toJsonString());
    }
}
