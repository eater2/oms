package com.marek.utils.decoratorpattern;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-04-20.
 */
public class DecoratorPatternTest {

    @Ignore
    @Test
    public void emailSendShouldBeDecorated() throws Exception {
        assertEquals("MessageCompany Disclaimer",DecoratorPattern.emailSend("Message",
                DecoratorPattern.emailNew.andThen(DecoratorPattern.emailDecorate)));
    }


}