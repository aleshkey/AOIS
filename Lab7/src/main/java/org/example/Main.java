package org.example;

import org.example.searches.Search;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
// код для генерации нового массива значений
//        for (int i = 0; i< 20; i++){
//            System.out.print((int)(Math.random()*100)+", ");
//        }
//        System.out.println();
        var arr = Arrays.asList(98, 52, 9, 56, 64, 59, 7, 26, 80, 18, 45, 25, 79, 79, 53, 33, 96, 70, 98, 35);
        Search.getNearest(arr, 10, true);

        Search.getInterval(arr, 1, 25);
    }
}