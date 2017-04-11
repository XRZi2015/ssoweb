package com.imooc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/3/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-active-accept.xml"})
public class SpringActivemqAccept {

    @Test
    public void testSpringActiveMq() throws Exception {
        //等待(其实启动spring容器就好了)
        System.in.read();
    }
}
