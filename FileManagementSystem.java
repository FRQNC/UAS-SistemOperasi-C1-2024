import java.util.Scanner;

public class FileManagementSystem {
    Folder root; // Folder teratas (root directory)
    Folder currentFolder; // Folder saat ini (current directory)

    // Constructor
    public FileManagementSystem() {
        // Buat folder dengan nama root dan parent null karena folder root adalah folder teratas
        root = new Folder("root", null);
        // Set lokasi folder saat ini ke root
        currentFolder = root;
    }

    // Fungsi untuk membuat folder baru
    public void createFolder(String name) {
        // Buat objek Folder baru dengan parent adalah folder saat ini
        Folder newFolder = new Folder(name, currentFolder);
        // Tambahkan folder baru ke subFolder dari folder saat ini
        currentFolder.addSubFolder(newFolder);
        // Tampilkan pesan bahwa folder telah dibuat
        System.out.println("Folder created: " + name);
    }

    // Fungsi untuk membuat file baru
    public void createFile(String name) {
        // Buat objek File baru
        File newFile = new File(name);
        // Tambahkan file baru ke daftar file dari folder saat ini
        currentFolder.addFile(newFile);
        // Tampilkan pesan bahwa file telah dibuat
        System.out.println("File created: " + name);
    }

    // Fungsi untuk berpindah directory
    public void changeDirectory(String name) {
        // Jika perintah adalah "..", pindah ke parent directory
        if (name.equals("..")) {
            if (currentFolder.parent != null) {
                currentFolder = currentFolder.parent;
            } else {
                System.out.println("Already at root directory.");
            }
        } else {
            // Cari subFolder dengan nama yang sesuai
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

    // Fungsi untuk menampilkan isi dari folder saat ini
    public void listContents() {
        currentFolder.listContents();
    }

    // Fungsi untuk menghapus file
    public void removeFile(String name) {
        if (currentFolder.removeFile(name)) {
            System.out.println("File removed: " + name);
        } else {
            System.out.println("File not found: " + name);
        }
    }

    // Fungsi untuk menghapus folder
    public void removeFolder(String name) {
        if (currentFolder.removeSubFolder(name)) {
            System.out.println("Folder removed: " + name);
        } else {
            System.out.println("Folder not found: " + name);
        }
    }

    // Fungsi untuk mengganti nama file atau folder
    public void rename(String oldName, String newName) {
        // Cari folder dengan nama lama di subFolders
        for (Folder folder : currentFolder.subFolders) {
            if (folder.name.equals(oldName)) {
                folder.name = newName;
                System.out.println("Folder renamed to: " + newName);
                return;
            }
        }
        // Cari file dengan nama lama di files
        for (File file : currentFolder.files) {
            if (file.name.equals(oldName)) {
                file.name = newName;
                System.out.println("File renamed to: " + newName);
                return;
            }
        }
        System.out.println("File or Folder not found: " + oldName);
    }

    // Fungsi untuk mendapatkan path dari folder saat ini
    private static String getCurrentPath(Folder folder) {
        StringBuilder path = new StringBuilder();
        while (folder != null) {
            path.insert(0, folder.name + "/");
            folder = folder.parent;
        }
        // Return path tanpa karakter '/' terakhir
        return path.length() > 0 ? path.substring(0, path.length() - 1) : "/";
    }

    // Fungsi main untuk menjalankan sistem manajemen file
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
            // Dapatkan path folder saat ini dan tampilkan di prompt
            String path = getCurrentPath(fms.currentFolder);
            System.out.print(path + " >> ");
            // Baca input dari pengguna
            command = scanner.nextLine();
            // Pisahkan perintah dan argumen
            String[] commandParts = command.split(" ");

            // Lakukan aksi berdasarkan perintah yang diberikan
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
                        // Dukungan untuk navigasi multi-level
                        String[] targetDir = commandParts[1].split("/");
                        for (String dirName : targetDir) {
                            fms.changeDirectory(dirName);
                        }
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
                    // Tutup scanner dan keluar dari program
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
