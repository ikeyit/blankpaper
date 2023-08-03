package io.ikeyit.blankpaper.memfsys;

/**
 * 文件和目录的基类
 */
public class FileElement {
    private String name;

    public FileElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
