package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.factory.support.Person;
import org.junit.Test;

public class ClassPathApplicationContextTest {

    @Test
    public void testClassPathApplicaitonTest() {
        ClassPathApplicationContext classPathApplicationContext = new ClassPathApplicationContext("classpath:factory-bean.xml");
        Person person = classPathApplicationContext.getBean("ff", Person.class);

        System.out.println(person.getAge());
    }
}