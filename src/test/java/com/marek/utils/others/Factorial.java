package com.marek.utils.others;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by marek.papis on 2016-07-25.
 */
public class Factorial {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Factorial.class);

    int factor(int n){
        log.info(String.valueOf(n));
        return n<=1?1:IntStream.rangeClosed(1,n).reduce(1,(x,y)->x*y);
    }


    @Test
    public void factorFrom1ShouldReturn1(){
        assertThat(factor(1)).isEqualTo(1);
    }

    @Test
    public void factorFrom0ShouldReturn1(){
        assertThat(factor(0)).isEqualTo(1);
    }

    @Test
    public void factorFrom4ShouldReturn24(){
        assertThat(factor(4)).isEqualTo(24);
    }

}
