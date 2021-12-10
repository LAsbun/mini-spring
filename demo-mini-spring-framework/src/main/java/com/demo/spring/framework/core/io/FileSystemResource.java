package com.demo.spring.framework.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author shengweisong
 * @date 2021/12/9
 **/
public class FileSystemResource implements Resource {

    private String filePath;

    public FileSystemResource(String path) {

        this.filePath = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        Path path = new File(filePath).toPath();
        return Files.newInputStream(path);
    }
}