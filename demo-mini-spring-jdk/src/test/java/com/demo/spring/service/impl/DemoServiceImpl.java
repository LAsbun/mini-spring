package com.demo.spring.service.impl;

import com.demo.spring.bean.Component;
import com.demo.spring.service.DemoService;

/**
 * DemoService
 *
 * @author shengweisong
 * @date 2021-11-26 2:08 PM
 **/
@Component
public class DemoServiceImpl implements DemoService {


    public String helloWorld() {
        return "helloWorld";
    }

}