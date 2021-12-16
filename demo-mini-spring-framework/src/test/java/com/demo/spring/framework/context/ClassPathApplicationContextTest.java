package com.demo.spring.framework.context;

import com.demo.spring.framework.beans.factory.support.Person;
import org.junit.Test;

public class ClassPathApplicationContextTest {

    @Test
    public void testClassPathApplicaitonTest() {
        ClassPathApplicationContext classPathApplicationContext = new ClassPathApplicationContext("classpath:factory-bean-with-before-advice.xml");
        Person person = classPathApplicationContext.getBean("ff", Person.class);
        Person personx = classPathApplicationContext.getBean("personx", Person.class);

        System.out.println(person.getAge());
        System.out.println(personx.getAge());

        classPathApplicationContext.registerShutdownHook();
    }
}