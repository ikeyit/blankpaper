package io.ikeyit.blankpaper.memfsys;

import java.util.List;

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
public class FileSystem {
    private static DirectoryNode root = new DirectoryNode("/");
    private static PathParser pathParser = new PathParser();
    public static void ls(String path) {
        String[] names = path.split("/");
        // 从根目录开始查找文件系统树
        for(String name : names) {

        }
    }

    public static void mikDir(String path) {
        List<String> namesInPath = pathParser.parse(path);
        DirectoryNode currentDir = root;
        for (String name : namesInPath) {
            currentDir = currentDir.mkDirIfNecessary(name);
        }
    }

    public static String readContentFromFile(String path) {
        List<String> namesInPath = pathParser.parse(path);
        DirectoryNode currentDir = root;
        // 先查目录
        for (int i = 0; i < namesInPath.size() - 1; i++) {
            FileElement child = currentDir.findChild(namesInPath.get(i));
            if (child == null) {
                throw new IllegalArgumentException("File doesn't exist");
            }
            if (!(child instanceof DirectoryNode)) {
                throw new IllegalArgumentException("Path is invalid");
            }
        }
        // 路径最后的名字应当对应为一个文件
        FileElement fileElement = currentDir.findChild(namesInPath.get(namesInPath.size() - 1));
        if (fileElement instanceof FileNode) {
            FileNode fileNode = (FileNode) fileElement;
            return fileNode.read();
        } else {
            throw new IllegalArgumentException("File doesn't exist");
        }
    }

    public static void addContentToFile(String path, String content) {

    }
}
