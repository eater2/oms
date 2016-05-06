package com.marek.utils.executearoundmethodpattern;

import com.marek.Application;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-04-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ExecuteAroundMethodPatternTest {

    @Ignore
    @Test
    public void shouldReturn(){
        Resource.use(r -> r.op1().op2());
    }

}