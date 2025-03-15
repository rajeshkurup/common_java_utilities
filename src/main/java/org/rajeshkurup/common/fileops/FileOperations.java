package org.rajeshkurup.common.fileops;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

public interface FileOperations {

    long size(String path) throws IOException;

    boolean isFolder(String path) throws IOException;

    boolean createFolder(String path) throws IOException;

    Optional<SortedSet<String>> listFiles(String path) throws IOException;

    boolean delete(String path) throws IOException;

    boolean copy(String source, String destination) throws IOException;

    boolean move(String source, String destination) throws IOException;

    Optional<String> read(String path) throws IOException;

    Optional<List<String>> readLines(String path) throws IOException;

    boolean write(String path, String data, boolean append) throws IOException;

    boolean writeLines(String path, List<String> data, boolean append) throws IOException;

    boolean isExist(String path);

}
