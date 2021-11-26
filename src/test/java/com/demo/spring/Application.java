package com.demo.spring;

import com.demo.spring.starter.MiniApplication;

/**
 * 启动Application
 *
 * @author shengweisong
 * @date 2021-11-26 2:07 PM
 **/
public class Application {

    public static void main(String[] args) {
        MiniApplication.run(Application.class, args);
    }
}