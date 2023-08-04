package io.ikeyit.blankpaper.memfsys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class MemFilePath {
    private String path;
    private List<String> names;

    /**
     * 输入一个路径/a/b/c返回对应分割后的文件名列表
     * @param path
     */
    public MemFilePath(String path) {
        if (path == null || path.isEmpty() || !path.startsWith("/")) {
            throw new IllegalArgumentException("Invalid Path");
        }
        this.path = path;
        if (path.equals("/")) {
            this.names = Collections.emptyList();
        } else {
            String[] words = path.split("/");
            this.names = new ArrayList<>(words.length - 1);
            for (int i = 1; i < words.length; i++) {
                if (words[i].isBlank()) {
                    throw new IllegalArgumentException("invalid file name");
                }
                names.add(words[i]);
            }
        }
    }

    public MemFilePath(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }

    public String getLastName() {
        return names.size() == 0 ? null : names.get(names.size() - 1);
    }

    public MemFilePath getParentPath() {
        if (names.size() == 0)
            return null;
        return new MemFilePath(names.subList(0, names.size() - 1));
    }

}
