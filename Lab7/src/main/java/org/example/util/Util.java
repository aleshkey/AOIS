package org.example.util;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Integer> generateArray(int size){
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i< size; i++){
            arr.add((int)(Math.random()*100));
        }
        return arr;
    }

    public static List<String> makeUnique(List<String> arr) {
        List<String> uniqueVars = new ArrayList<>();
        for(var str : arr){
            if (!uniqueVars.contains(str) && !str.equals("")){
                uniqueVars.add(str);
            }
        }
        return uniqueVars;
    }

    public static String toBinary(int num){
        String binary = "";
        while (num >= 1){
            binary = (num%2)+binary;
            num = num / 2;
        }
        var length = binary.length();
        for (int i =0 ; i < 8-length; i++){
            binary = '0'+binary;
        }
        return binary;
    }

    public static List<String> toBinary(List<Integer> arr){
        List<String> binary = new ArrayList<>();
        for(var elem : arr){
            binary.add(Util.toBinary(elem));
        }
        return binary;
    }

    public static List<Integer> toDec(List<String> arr){
        List<Integer> binary = new ArrayList<>();
        for(var elem : arr){
            binary.add(Util.toDec(elem));
        }
        return binary;
    }

    public static int toDec(String binary){
        int res = 0;
        for (int i = 0; i<binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                var step = binary.length() - i - 1;
                res += Math.pow(2, step);
            }
        }
        return res;
    }

    public static List<String> intersection(List<String> arr1, List<String> arr2){
        List<String> res = new ArrayList<>();
        for (var elem : arr1){
            if (arr2.contains(elem)){
                res.add(elem);
            }
        }
        return res;
    }

}
