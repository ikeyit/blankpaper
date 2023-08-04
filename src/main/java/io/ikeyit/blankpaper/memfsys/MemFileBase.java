package io.ikeyit.blankpaper.memfsys;

/**
 * 文件和目录的基类
 */
public class MemFileBase {
    protected String name;

    protected MemDirectory parent;

    public MemFileBase(String name, MemDirectory parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public MemDirectory getParent() {
        return parent;
    }


    public String path() {
        if (parent == null) {
            return "/";
        }
        return parent.parent == null ? parent.path() + name : parent.path() + "/" + name;
    }
}
