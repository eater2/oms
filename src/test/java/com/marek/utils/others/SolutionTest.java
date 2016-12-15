package com.marek.utils.others;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by marek.papis on 04.08.2016.
 */
public class SolutionTest {

    private Solution solution = new Solution();

    @Test
    public void testGDC1() {

        int[] arr = new int[]{1,2,33,4,2,1};
        //int[] arr = new int[]{1,1};

        int counter = 0;
        Map m = new HashMap<Integer,Integer>();

        for(int i : arr){
            if(m.containsKey(i) )
                m.put(i,(Integer)m.get(i)+1);
            else
                m.put(i,1);
            //if((Integer)m.get(i) >=2)
            //    counter++;
        }

        for(Object k: m.values())
        {
            if((Integer)k>=2)
                counter++;
        }
        System.out.println(m);
        System.out.println(counter);
    }


}
