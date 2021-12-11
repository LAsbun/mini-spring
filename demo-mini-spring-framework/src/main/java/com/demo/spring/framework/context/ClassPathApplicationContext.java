package com.demo.spring.framework.context;

/**
 * @author shengweisong
 * @date 2021/12/11
 **/
public class ClassPathApplicationContext extends AbstractXmlApplicationContext {

    String[] configLocations;

    public ClassPathApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    public ClassPathApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }


    @Override
    protected String[] getLocations() {
        return configLocations;
    }
}