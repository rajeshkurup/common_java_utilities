package org.rajeshkurup.common.fileops;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import lombok.NonNull;

public class FileOperator implements FileOperations {

    @Override
    public long size(@NonNull final String path) throws IOException {
        return Files.size(Paths.get(path));
    }

    @Override
    public boolean createFolder(@NonNull final String path) throws IOException {
        if(isExist(path)) {
            return true;
        }
        Path sysPath = Paths.get(path);
        if(!createFolder(sysPath.getParent().toString())) {
            throw new IOException("Unable to create parent folder");
        }
        return Files.createDirectory(sysPath).toString().equalsIgnoreCase(path);
    }

    @Override
    public boolean delete(@NonNull final String path) throws IOException {
        if(isFolder(path)) {
            listFiles(path).ifPresent(files -> files.forEach(file -> {
                try {
                    delete(path + File.separator + file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        return Files.deleteIfExists(Paths.get(path));
    }

    @Override
    public boolean copy(@NonNull final String source, @NonNull final String destination) throws IOException {
        if(!isExist(destination)) {
            createFolder(destination);
        }
        Path srcPath = Paths.get(source);
        if(isFolder(source)) {
            return copyFolder(source, destination + File.separator + srcPath.getFileName().toString());
        }
        String dest = destination;
        if(isFolder(destination)) {
            dest = destination + File.separator + srcPath.getFileName().toString();
        }
        return Files.copy(srcPath, Paths.get(dest), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES).toString().equalsIgnoreCase(dest);
    }

    private boolean copyFolder(@NonNull final String source, @NonNull final String destination) throws IOException {
        if(!isExist(destination)) {
            createFolder(destination);
        }
        listFiles(source).ifPresent(files -> files.forEach(file -> {
            try {
                String src = source + File.separator + file;
                if(isFolder(src)) {
                    copyFolder(src, destination + File.separator + file);
                } else  {
                    copy(src, destination);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        return true;
    }

    @Override
    public boolean move(@NonNull final String source, @NonNull final String destination) throws IOException {
        boolean result = moveFile(source, destination);
        if(result && isFolder(source)) {
            delete(source);
        }
        return result;
    }

    private boolean moveFile(@NonNull final String source, @NonNull final String destination) throws IOException {
        if(!isExist(destination)) {
            createFolder(destination);
        }
        Path srcPath = Paths.get(source);
        if(isFolder(source)) {
            return moveFolder(source, destination + File.separator + srcPath.getFileName().toString());
        }
        String dest = destination;
        if(isFolder(destination)) {
            dest = destination + File.separator + srcPath.getFileName().toString();
        }
        return Files.move(srcPath, Paths.get(dest), StandardCopyOption.REPLACE_EXISTING).toString().equalsIgnoreCase(dest);
    }

    private boolean moveFolder(@NonNull final String source, @NonNull final String destination) throws IOException {
        if(!isExist(destination)) {
            createFolder(destination);
        }
        listFiles(source).ifPresent(files -> files.forEach(file -> {
            try {
                String src = source + File.separator + file;
                if(isFolder(src)) {
                    moveFolder(src, destination + File.separator + file);
                } else  {
                    move(src, destination);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        return true;
    }

    @Override
    public boolean write(@NonNull final String path, @NonNull final String data, boolean append) throws IOException {
        Path filePath = Paths.get(path);
        Path parent = filePath.getParent();
        if(!Files.exists(parent)) {
            if(!createFolder(parent.toString())) {
                throw new IOException("Unable to create parent folder");
            }
        }
        try(BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, append ? StandardOpenOption.APPEND : StandardOpenOption.WRITE)) {
            writer.write(data);
            writer.newLine();
        }
        return true;
    }

    @Override
    public boolean writeLines(@NonNull final String path, @NonNull final List<String> data, boolean append) throws IOException {
        Path filePath = Paths.get(path);
        Path parent = filePath.getParent();
        if(!Files.exists(parent)) {
            if(!createFolder(parent.toString())) {
                throw new IOException("Unable to create parent folder");
            }
        }
        for(String line : data) {
            try(BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, append ? StandardOpenOption.APPEND : StandardOpenOption.WRITE)) {
                writer.write(line);
                writer.newLine();
            }
        }
        return true;
    }

    @Override
    public Optional<SortedSet<String>> listFiles(@NonNull final String path) throws IOException {
        return Optional.ofNullable(Files.list(Paths.get(path)).map(file -> file.getFileName().toString())
                .collect(Collectors.toCollection(TreeSet::new)));
    }

    @Override
    public Optional<String> read(@NonNull final String path) throws IOException {
        if(!isExist(path)) {
            throw new IOException("File not found");
        }
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            StringBuilder sb = new StringBuilder();
            for(String line = reader.readLine(); Objects.nonNull(line); line = reader.readLine()) {
                sb.append(line).append(System.lineSeparator());
            }
            return Optional.of(sb.toString());
        }
    }

    @Override
    public Optional<List<String>> readLines(@NonNull final String path) throws IOException {
        if(!isExist(path)) {
            throw new IOException("File not found");
        }
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            return Optional.ofNullable(reader.lines().collect(Collectors.toList()));
        }
    }

    @Override
    public boolean isExist(@NonNull final String path) {
        return Files.exists(Paths.get(path));
    }

    @Override
    public boolean isFolder(@NonNull final String path) throws IOException {
        return Files.isDirectory(Paths.get(path));
    }

}
