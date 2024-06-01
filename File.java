public class File {
    String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "File: " + name;
    }
}
