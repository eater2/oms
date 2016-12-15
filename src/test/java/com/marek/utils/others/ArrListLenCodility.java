package com.marek.utils.others;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by marek.papis on 04.08.2016.
 */
public class ArrListLenCodility {

    int[] lista_intow = {1, 4, -1, 3, 2};

    int solution(int[] lista) {

        //List<Integer> lista_kol = IntStream.of(lista).boxed().collect(Collectors.toList());
        return godeep(lista,1,lista[0]);
    }

    int godeep(int[] input,int count,int next)
    {

        System.out.println("------------------------");
        System.out.println("count" + count);
        System.out.println("next" + next);
        System.out.println("------------------");

        System.out.println("-----------------");
        System.out.println("counter" + (count+1));
        System.out.println("next" + input[next]);
        System.out.println("------------------------");

        if(input[next] == -1)
            return ++count;
        else
            return godeep(input,++count,input[next]);
    }


    @Test
    public void shoudlReturnCorrect() {
        Assert.assertEquals(4,solution(lista_intow));
    }

    public static void main(String[] args) {
        ArrListLenCodility test1 = new ArrListLenCodility();
        System.out.println("returned" + test1.solution(test1.lista_intow));
    }

}
