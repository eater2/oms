package com.marek.utils.singletonpattern;

import com.marek.Application;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.*;

/**
 * Created by marek.papis on 2016-04-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SingletonTest {

    @Ignore
    @Test
    public void enumShouldReturnInstance() {
        assertTrue(SingletonEnum.getInstance()!=null);
    }

    @Ignore
    @Test
    public void emuShouldReturnSameInstanceTwice() {
        SingletonEnum instance = SingletonEnum.getInstance();
        SingletonEnum instance2 = SingletonEnum.getInstance();
        assertTrue(instance == instance2);
    }

    @Ignore
    @Test
    public void lazyShouldReturnSameInstanceTwice() {
        SingletonLazy instance = SingletonLazy.getInstance();
        SingletonLazy instance2 = SingletonLazy.getInstance();
        assertTrue(instance == instance2);
    }


}