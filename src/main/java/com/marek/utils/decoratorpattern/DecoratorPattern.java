package com.marek.utils.decoratorpattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Created by marek.papis on 2016-04-20.
 */

//component
class Email {

    private String content;

    public Email(String content) {
        this.content = content;
    }

    public String getContents() {
        return content;
    }
}

public class DecoratorPattern {
    static  Logger log = LoggerFactory.getLogger(Email.class);

    static Function<String, Email> emailNew = e -> new Email(e);
    static Function<Email, String> emailDecorate = e -> e.getContents() + "Company Disclaimer";

    private DecoratorPattern(){}

    static String  emailSend(String s, Function<String,String> f){
        log.info(f.apply(s));
        return f.apply(s);
    }
    //emailSend("Message",emailNew.andThen(emailDecorate))

}



