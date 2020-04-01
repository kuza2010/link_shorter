package ru.adanil.shorter;

import java.io.File;
import java.nio.file.Path;

public class PathUtils {

    public static String getFileName(String path) {
        return new File(path).getName();
    }

    public static String getParent(Path path) {
        return path.getParent().toString();
    }

    public static String getParent(String path) {
        return getParent(new File(path).toPath());
    }
}
