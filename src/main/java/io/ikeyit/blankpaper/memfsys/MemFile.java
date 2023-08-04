package io.ikeyit.blankpaper.memfsys;

/**
 *
 */
public class MemFile extends MemFileBase {
    private String content;

    public MemFile(String name, MemDirectory parent) {
        super(name, parent);
    }

    public void write(String content) {
        this.content = content;
    }

    public String read() {
        return content;
    }

    @Override
    public String toString() {
        return "MemFile{" +
                "content='" + content + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
