package com.demo.spring.controller;

import com.demo.spring.bean.AutoWired;
import com.demo.spring.service.DemoService;
import com.demo.spring.web.mvc.Controller;
import com.demo.spring.web.mvc.RequestMapping;

/**
 * DemoController
 *
 * @author shengweisong
 * @date 2021-11-26 2:08 PM
 **/
@Controller
public class DemoController {

    @AutoWired
    private DemoService demoServiceImpl;

    @RequestMapping("/")
    public String index() {
        return "This is Index.这是首页";
    }

    @RequestMapping("/hello")
    public String helloWorld() {
        return demoServiceImpl.helloWorld();
    }
}