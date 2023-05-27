package org.example;

import org.example.searches.Search;
import org.example.util.Util;

public class Main {
    public static void main(String[] args) {
        var arr = Util.generateArray(10);
        System.out.println(arr);
        var binary = Util.toBinary(arr);
        System.out.println(Util.toDec(Search.getNearest(50, binary, false)));
        System.out.println(Util.toDec(Search.getNearest(50, binary, true)));
        System.out.println(Util.toDec(Search.border(25, 75, binary)));
    }
}