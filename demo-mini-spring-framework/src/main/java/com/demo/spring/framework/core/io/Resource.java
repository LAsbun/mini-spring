package com.demo.spring.framework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源
 *
 * @author shengweisong
 * @date 2021/12/9
 **/
public interface Resource {

    /**
     * 获取输入流
     *
     * @return
     */
    InputStream getInputStream() throws IOException;
}