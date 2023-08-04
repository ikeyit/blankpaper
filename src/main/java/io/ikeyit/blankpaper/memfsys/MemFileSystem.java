package io.ikeyit.blankpaper.memfsys;

/**
 * List<String> ls(String path)
 * Given a path in string format.
 * If it is a file path, return a list that only contains this file's name.
 * If it is a directory path, return the list of file and directory names in this directory.
 * Your output (file and directory names together) should in lexicographic order.
 *
 * void mkdir(String path)
 * Given a directory path that does not exist, you should make a new directory according to the path.
 * If the middle directories in the path don't exist either, you should create them as well.
 * This function has void return type.
 *
 * Given a file path and file content in string format.
 * If the file doesn't exist, you need to create that file containing given content.
 * If the file already exists, you need to append given content to original content.
 * This function has void return type.
 *
 * String readContentFromFile(String path)
 * Given a file path, return its content in string format.
 *
 * void addContentToFile(String path, String content)
 */
public class MemFileSystem {
    private static MemDirectory root = new MemDirectory("/", null);
    public static void ls(String path) {
        MemFileBase file = findFile(path);
        if (file == null) {
            System.out.println("not exists");
        }
        if (file instanceof MemFile) {
            System.out.println(file.path());
        } else if (file instanceof MemDirectory) {
            MemDirectory directory = (MemDirectory) file;
            for (MemFileBase child : directory.listChildren()) {
                System.out.println(child.path());
            }
        }
    }

    public static MemDirectory mkDir(MemFilePath filePath) {
        MemDirectory currentDir = root;
        for (String name : filePath.getNames()) {
            currentDir = currentDir.addDirectory(name);
        }
        return currentDir;
    }

    public static MemDirectory mkDir(String path) {
        MemFilePath filePath = new MemFilePath(path);
        return mkDir(filePath);
    }

    public static MemFile mkFile(String path) {
        MemFilePath filePath = new MemFilePath(path);
        MemFilePath parentFilePath = filePath.getParentPath();
        if (parentFilePath == null) {
            throw new IllegalArgumentException("invalid file path");
        }
        MemDirectory parentDirectory = mkDir(parentFilePath);
        return parentDirectory.addFile(filePath.getLastName());
    }

    public static String readContentFromFile(String path) {
        MemFileBase file = findFile(path);
        if (file instanceof MemFile) {
            return ((MemFile)file).read();
        } else {
            throw new IllegalArgumentException("File doesn't exist");
        }
    }

    public static MemFileBase findFile(String path) {
        MemFilePath filePath = new MemFilePath(path);
        MemFilePath parentFilePath = filePath.getParentPath();
        if (parentFilePath == null) {
            throw new IllegalArgumentException("Invalid path");
        }
        MemDirectory currentDir = root;
        // 先查目录
        for (String name : parentFilePath.getNames()) {
            MemFileBase child = currentDir.findChild(name);
            if (child == null) {
                throw new IllegalArgumentException("File doesn't exist");
            }
            if (!(child instanceof MemDirectory)) {
                throw new IllegalArgumentException("Path is invalid");
            }
            currentDir = (MemDirectory) child;
        }
        // 路径最后的名字应当对应为一个文件
        return currentDir.findChild(filePath.getLastName());
    }

    public static MemFile addContentToFile(String path, String content) {
        MemFilePath filePath = new MemFilePath(path);
        MemFilePath parentFilePath = filePath.getParentPath();
        if (parentFilePath == null) {
            throw new IllegalArgumentException("Invalid path");
        }
        MemDirectory parentDirectory = mkDir(parentFilePath);
        MemFileBase foundFile = parentDirectory.findChild(filePath.getLastName());
        MemFile file = null;
        if (foundFile instanceof MemDirectory) {
            throw new IllegalArgumentException("Directory with the same name exists!");
        } else if (foundFile instanceof MemFile) {
            file = (MemFile) foundFile;
        } else if (foundFile == null) {
            file = parentDirectory.addFile(filePath.getLastName());
        }
        file.write(content);
        return file;
    }

    public static void main(String[] args) {
        System.out.println(mkDir("/").path());
        System.out.println(mkDir("/a").path());
        System.out.println(mkDir("/b").path());
        System.out.println(mkDir("/a/b/c").path());
        System.out.println(addContentToFile("/a/b/a1", "hello").path());
        System.out.println(readContentFromFile("/a/b/a1"));
        ls("/a/b/a1");
        ls("/a/b");
    }
}
