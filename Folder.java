import java.util.ArrayList;
import java.util.List;

public class Folder {
    String name;
    List<Folder> subFolders;
    List<File> files;
    Folder parent;

    public Folder(String name, Folder parent) {
        this.name = name;
        this.subFolders = new ArrayList<>();
        this.files = new ArrayList<>();
        this.parent = parent;
    }

    public void addSubFolder(Folder folder) {
        subFolders.add(folder);
    }

    public void addFile(File file) {
        files.add(file);
    }

    public boolean removeSubFolder(String name) {
        return subFolders.removeIf(folder -> folder.name.equals(name));
    }

    public boolean removeFile(String name) {
        return files.removeIf(file -> file.name.equals(name));
    }

    @Override
    public String toString() {
        return "Folder: " + name;
    }

    public void listContents() {
        System.out.println("Contents of folder: " + name);
        for (Folder subFolder : subFolders) {
            System.out.println(subFolder);
        }
        for (File file : files) {
            System.out.println(file);
        }
    }
}
