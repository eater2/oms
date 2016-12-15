package com.marek.utils.others;

import com.marek.Application;
import org.h2.util.IntArray;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by marek.papis on 2016-04-14.
 */
public class ProjectEulerTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProjectEulerTest.class);

    Function<Integer, Boolean> isPentagonal = i -> {
        Double d = (1.0 + Math.sqrt(1 + 24 * i)) / 6.0;
        return i == 0 ? false : d == d.intValue();
    };

    Function<Integer, Integer> calcPentagonal = i -> i * (3 * i - 1) / 2;

    BinaryOperator<Integer> sumAndMinus = (a, m) -> isPentagonal.apply(a + m) ? (isPentagonal.apply(m - a) ? 1 : 0) : 0;

    @Ignore
    @Test
    public void shouldCheckIfIsPentagonal() throws Exception {
        assertTrue(isPentagonal.apply(1));
    }

    @Ignore
    @Test
    public void shouldCheckIfIsNotPentagonal() throws Exception {
        assertFalse(isPentagonal.apply(2));
    }

    @Ignore
    @Test
    public void shouldCalculatePentagonal() throws Exception {
        assertEquals((Integer)1,calcPentagonal.apply(1));
    }

    @Ignore
    @Test
    public void shouldCalculatePentagonal1() throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");

        String s = strings.stream()
                .filter(e -> e.equals("3"))
                .findAny()
                .orElse(new String("3"));

        IntStream.range(1,10)
                .forEach(e->strings.add(String.valueOf(e)));

        assertThat(s).isEqualTo("3");
    }

    @Ignore
    @Test
    public void projectEuler44() throws Exception {
        boolean found = false;
        for (int i = 2; found == false; i++) {
            //wieksze p1
            log.info("iterate:" + i);
            Integer p1 = calcPentagonal.apply(i);
            for (int k = i - 1; k > 0 && !found; k--) {
                //mniejsze p2
                Integer p2 = calcPentagonal.apply(k);
                found = sumAndMinus.apply(p2, p1) == 1 ? true : false;
                if (found) {
                    log.info("found i:" + i + "|k:" + k);
                    log.info("found P1:" + p1 + "|P2:" + p2);
                    log.info("found P1 - P2 :" + (p1 - p2));
                }
            }
        }
    }

}
