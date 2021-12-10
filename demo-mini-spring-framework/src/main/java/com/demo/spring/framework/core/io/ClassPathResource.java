package com.demo.spring.framework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author shengweisong
 * @date 2021/12/9
 **/
public class ClassPathResource implements Resource {


    private final String path;

    public ClassPathResource(String path) {

        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(path);
        if (resourceAsStream == null) {
            throw new IOException("no exist resource:" + path);
        }
        return resourceAsStream;
    }
}