package io.ikeyit.blankpaper.memfsys;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MemDirectory extends MemFileBase {
    private List<MemFileBase> children = new ArrayList<>();

    public MemDirectory(String name, MemDirectory parent) {
        super(name, parent);
    }

    public MemDirectory addDirectory(String name) {
        MemFileBase child = findChild(name);
        if (child != null && child instanceof MemFile) {
            throw new IllegalArgumentException("File exists with the name " + name);
        }
        if (child == null) {
            child = new MemDirectory(name, this);
            children.add(child);
        }

        return (MemDirectory) child;
    }

    public MemFile addFile(String name) {
        MemFileBase child = findChild(name);
        if (child != null) {
            throw new IllegalArgumentException("File exists!");
        }
        MemFile fileNode = new MemFile(name, this);
        children.add(fileNode);
        return fileNode;
    }

    public List<MemFileBase> listChildren() {
        return children;
    }

    // 查找指定名称的子文件
    public MemFileBase findChild(String name) {
        return children.stream().filter(child -> name.equals(child.getName())).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "MemDirectory{" +
                "children=" + children +
                ", name='" + name + '\'' +
                '}';
    }
}
