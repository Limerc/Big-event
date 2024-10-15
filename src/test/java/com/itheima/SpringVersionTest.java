package com.itheima;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class SpringVersionTest {

    @Test
    public void getSpringVersion() {
        String version = SpringVersion.getVersion();
        String version1 = SpringBootVersion.getVersion();
        System.out.println(version);
        System.out.println(version1);
    }
}
