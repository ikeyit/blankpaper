package io.ikeyit.blankpaper.memfsys;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DirectoryNode extends FileElement {
    private List<FileElement> children = new ArrayList<>();

    public DirectoryNode(String name) {
        super(name);
    }

    public synchronized DirectoryNode addDirectory(String name) {
        FileElement child = findChild(name);
        if (child != null) {
            throw new IllegalArgumentException("File exists!");
        }
        DirectoryNode directoryNode = new DirectoryNode(name);
        children.add(directoryNode);
        return directoryNode;
    }

    public DirectoryNode mkDirIfNecessary(String name) {
        FileElement child = findChild(name);
        if (child != null && child instanceof FileNode) {
            throw new IllegalArgumentException("File exists with the name " + name);
        }
        if (child == null) {
            child = new DirectoryNode(name);
            children.add(child);
        }

        return (DirectoryNode) child;
    }

    public FileNode addFileNode(String name) {
        FileElement child = findChild(name);
        if (child != null) {
            throw new IllegalArgumentException("File exists!");
        }
        FileNode fileNode = new FileNode(name);
        children.add(fileNode);
        return fileNode;
    }

    public List<FileElement> listChildren() {
        return children;
    }

    // 查找指定名称的子文件
    public synchronized FileElement findChild(String name) {
        return children.stream().filter(child -> name.equals(child.getName())).findFirst().orElse(null);
    }


}
