package io.ikeyit.blankpaper.memfsys;

/**
 *
 */
public class FileNode extends FileElement {
    private String content;

    public FileNode(String name) {
        super(name);
    }

    public void write(String content) {
        this.content = content;
    }

    public String read() {
        return content;
    }
}
