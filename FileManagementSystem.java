import java.util.Scanner;

public class FileManagementSystem {
    Folder root;
    Folder currentFolder;

    public FileManagementSystem() {
        root = new Folder("root", null);
        currentFolder = root;
    }

    public void createFolder(String name) {
        Folder newFolder = new Folder(name, currentFolder);
        currentFolder.addSubFolder(newFolder);
        System.out.println("Folder created: " + name);
    }

    public void createFile(String name) {
        File newFile = new File(name);
        currentFolder.addFile(newFile);
        System.out.println("File created: " + name);
    }

    public void changeDirectory(String name) {
        if (name.equals("..")) {
            if (currentFolder.parent != null) {
                currentFolder = currentFolder.parent;
            } else {
                System.out.println("Already at root directory.");
            }
        } else {
            boolean found = false;
            for (Folder folder : currentFolder.subFolders) {
                if (folder.name.equals(name)) {
                    currentFolder = folder;
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Folder not found: " + name);
            }
        }
    }

    public void listContents() {
        currentFolder.listContents();
    }

    public void removeFile(String name) {
        if (currentFolder.removeFile(name)) {
            System.out.println("File removed: " + name);
        } else {
            System.out.println("File not found: " + name);
        }
    }

    public void removeFolder(String name) {
        if (currentFolder.removeSubFolder(name)) {
            System.out.println("Folder removed: " + name);
        } else {
            System.out.println("Folder not found: " + name);
        }
    }

    public void rename(String oldName, String newName) {
        for (Folder folder : currentFolder.subFolders) {
            if (folder.name.equals(oldName)) {
                folder.name = newName;
                System.out.println("Folder renamed to: " + newName);
                return;
            }
        }
        for (File file : currentFolder.files) {
            if (file.name.equals(oldName)) {
                file.name = newName;
                System.out.println("File renamed to: " + newName);
                return;
            }
        }
        System.out.println("File or Folder not found: " + oldName);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManagementSystem fms = new FileManagementSystem();
        String command;

        System.out.println("File Management System Commands:");
        System.out.println("mkdir <foldername> - Create a folder");
        System.out.println("touch <filename> - Create a file");
        System.out.println("cd <foldername> - Change directory");
        System.out.println("cd .. - Move to parent directory");
        System.out.println("ls - List contents of current directory");
        System.out.println("rmfile <filename> - Remove a file");
        System.out.println("rmdir <foldername> - Remove a folder");
        System.out.println("rename <oldname> <newname> - Rename a file or folder");
        System.out.println("exit - Exit the program");

        while (true) {
            System.out.print(">> ");
            command = scanner.nextLine();
            String[] commandParts = command.split(" ");

            switch (commandParts[0]) {
                case "mkdir":
                    if (commandParts.length == 2) {
                        fms.createFolder(commandParts[1]);
                    } else {
                        System.out.println("Usage: mkdir <foldername>");
                    }
                    break;
                case "touch":
                    if (commandParts.length == 2) {
                        fms.createFile(commandParts[1]);
                    } else {
                        System.out.println("Usage: touch <filename>");
                    }
                    break;
                case "cd":
                    if (commandParts.length == 2) {
                        fms.changeDirectory(commandParts[1]);
                    } else {
                        System.out.println("Usage: cd <foldername> or cd ..");
                    }
                    break;
                case "ls":
                    fms.listContents();
                    break;
                case "rmfile":
                    if (commandParts.length == 2) {
                        fms.removeFile(commandParts[1]);
                    } else {
                        System.out.println("Usage: rmfile <filename>");
                    }
                    break;
                case "rmdir":
                    if (commandParts.length == 2) {
                        fms.removeFolder(commandParts[1]);
                    } else {
                        System.out.println("Usage: rmdir <foldername>");
                    }
                    break;
                case "rename":
                    if (commandParts.length == 3) {
                        fms.rename(commandParts[1], commandParts[2]);
                    } else {
                        System.out.println("Usage: rename <oldname> <newname>");
                    }
                    break;
                case "exit":
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
