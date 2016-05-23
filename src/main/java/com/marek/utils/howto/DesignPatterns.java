package com.marek.utils.howto;

/**
 * Created by marek.papis on 2016-05-13.
 */
public class DesignPatterns {

    public static void whatIsTheAdapterDesignPattern() {
        //...
    }


    public static void whenIsTheAdapterDesignPatternUsed() {

     /*
        -When we want to interact with existing system using incompatible interface.
        -When a new system wants to interact with legacy(old) system using new interface
        which is not compatible with interface of legacy system.

        int[] numbers = new int[]{34, 2, 4, 12, 1};
        Sorter sorter = new SortListAdapter();
        sorter.sort(numbers);
    */
    }

    public static void howToCodeTheAdapterDesignPattern() {
    /*
    //This is a third party implementation of a  number sorter that deals with Lists, not arrays.

    public class NumberSorter{
        public List<Integer> sort(List<Integer> numbers)
        {
            //sort and return
            return new ArrayList<Integer>();
        }
    }
    public class SortListAdapter
    {
        @Override
        public int[] sort(int[] numbers)
        {
            List<Integer> numberList = new ArrayList<Integer>(Arrays.asList(numbers));

            NumberSorter sorter = new NumberSorter();
            numberList = sorter.sort(numberList);

            Integer[] sortedNumbers = numberList.stream().toArray(Integer[]::new);
            //or   Integer[] sortedNumbers = numberList.toArray();
            return sortedNumbers;
        }
    }
    */
    }
}
