package com.marek.utils.others;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by marek.papis on 04.08.2016.
 */
public class CodingArithSliceCountCodility {


    int[] lista_intow = {-1, 1, 3, 3, 3, 2, 1, 0};
    int minAmount = 3;

    int solution(int[] lista) {

        //List<Integer> lista_kol = IntStream.of(lista).boxed().collect(Collectors.toList());

        for (int counter = 0; counter < lista.length - 2; counter++) {

            System.out.println("+++++++++++++++++" + counter);

            for (int counter2 = counter; counter2 < lista.length -2 ; counter2++) {

                System.out.println("+++++++++++++++++***********" + counter2);

                if (Math.abs(lista[counter + 1] - lista[counter2]) == Math.abs(lista[counter2 + 2] - lista[counter2 + 1])) {
                    System.out.println("found " + lista[counter2] + " and " + lista[counter2 + 2]);
                }
            }

        }
        return 0;
    }


    @Test
    public void shoudlReturnCorrect() {
        Assert.assertEquals(4, solution(lista_intow));
    }

    public static void main(String[] args) {
        CodingArithSliceCountCodility test1 = new CodingArithSliceCountCodility();
        System.out.println("returned" + test1.solution(test1.lista_intow));
    }


}
