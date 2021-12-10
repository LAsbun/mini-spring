package com.demo.spring.framework.core.io;

/**
 * @author shengweisong
 * @date 2021/12/9
 **/
public class DefaultResourceLoader implements ResourceLoader {

    public static final String CLASS_PATH_PREFIX = "classpath:";

    @Override
    public Resource loadResource(String location) {
        if (location.startsWith(CLASS_PATH_PREFIX)) {
            return new ClassPathResource(location.substring(CLASS_PATH_PREFIX.length()));
        } else {
            // 默认就是本地文件
            return new FileSystemResource(location);
        }
    }
}