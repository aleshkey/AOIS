package org.example;

import org.example.array.AssociativeArray;
import org.example.util.Util;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> v = Arrays.asList(1, 0, 1);
        AssociativeArray aa = new AssociativeArray();
        System.out.println("Matrix:");
        Util.print(aa.getArray());
        aa.generateDiagonal();
        System.out.println("Diagonal:");
        Util.print(aa.getDiagonal());
        System.out.println();
        System.out.println("7th word in matrix: "+aa.getAddress(7));
        System.out.println("\n");
        aa.operations(v);
        Util.print(aa.getDiagonal());
        System.out.println("\n");
        aa.functions(0, 1, 2,2);
        aa.functions(3, 4, 5, 7);
        aa.functions(6, 7, 8,8);
        aa.functions(9, 10, 11, 13);
        Util.print(aa.getDiagonal());
        System.out.println("\n");
        int start = 4;
        int finish = 7;
        aa.border(start, finish);
    }
}