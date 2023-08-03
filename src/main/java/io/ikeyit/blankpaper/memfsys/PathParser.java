package io.ikeyit.blankpaper.memfsys;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class PathParser {
    /**
     * 输入一个路径/a/b/c返回对应分割后的文件名列表
     * @param path
     * @return
     */
    public List<String> parse(String path) {
        // 先简单实现
        return Arrays.asList(path.split("/"));
    }

}
