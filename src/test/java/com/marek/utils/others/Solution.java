package com.marek.utils.others;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String args[] ) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        List<String> elements = new ArrayList<String>(Arrays.asList(input.split("\\s")));

        String element = br.readLine();

        ElementFinder elementFinder = new LastElement();
        System.out.println("Last element: >" + elementFinder.findElement(elements, element) + "<");
        elementFinder = new FirstElement2();
        System.out.println("First element: >" + elementFinder.findElement(elements, element) + "<");
    }
}

interface ElementFinder {
    public String findElement(List<String> list, String element);
}

class FirstElement extends LastElement {
    public String findElement(List<String> list, String element) {
        String result = "";
        String foundElement = super.findElement(list, element);

        while(foundElement != "") {
            result = foundElement;
            list.remove(result);
            foundElement = super.findElement(list, element);
        }
        return result;
    }
}

class LastElement implements ElementFinder {
    public String findElement(List<String> list, String element) {
        int i = list.size() - 1;
        String result = "";

        while(i > 0) {
            if(list.get(i).startsWith(element)) {
                result = list.get(i);
                return result;
            }
            i--;
        }
        return result;
    }
}

class FirstElement2 implements ElementFinder {
    public String findElement(List<String> list, String element) {
        int i = 0;
        String result = "";

        while(i <= list.size() - 1) {
            if(list.get(i).startsWith(element)) {
                result = list.get(i);
                return result;
            }
            i++;
        }
        return result;
    }
}