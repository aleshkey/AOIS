package org.example.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Util {

    private static boolean hasAddition = false;

    public static String toBinary(List<Boolean> arr){
        String res = "";
        for (var elem : arr){
            res = elem ? res + '1' : res + '0';
         }
        return res;
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

    public static List<List<Integer>> intersection(List<List<Integer>> arr1, List<List<Integer>> arr2){
        List<List<Integer>> res = new ArrayList<>();
        for (var elem : arr1){
            if (arr2.contains(elem)){
                res.add(elem);
            }
        }
        return res;
    }

    public static char addingDigits(char digit1, char digit2) {
        char res;
        int sumOfDigits = parseInt(String.valueOf(digit1)) + parseInt(String.valueOf(digit2));

        if (hasAddition) {
            sumOfDigits++;
        }

        if (sumOfDigits < 2) {
            res = (char) (sumOfDigits + '0');
            hasAddition = false;
        } else {
            hasAddition = true;
            res = (char) (sumOfDigits - 2 + '0');
        }

        return res;
    }

    public static List<Integer> sum(List<Integer> number1, List<Integer> number2){
        var num1 = listToBinary(number1);
        var num2 = listToBinary(number2);
        StringBuilder resultInStr = new StringBuilder();
        for (int i = num1.length() - 1; i >= 0; i--) {
            resultInStr.insert(0, addingDigits(num1.charAt(i), num2.charAt(i)));
        }
        hasAddition = false;
        var result = binaryToList(resultInStr.toString());
        return result;
    }

    private static String listToBinary(List<Integer> arr){
        StringBuilder binary = new StringBuilder();
        for(var elem : arr){
            binary.append(elem);
        }
        return binary.toString();
    }

    private static List<Integer> binaryToList(String str){
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < str.length(); i++){
            list.add(Character.getNumericValue(str.charAt(i)));
        }
        return list;
    }

    public static List<Boolean> intToBoolean(List<Integer> arr){
        List<Boolean> res = new ArrayList<>();
        for(var elem : arr){
            res.add(intToBoolean(elem));
        }
        return res;
    }

    public static Boolean intToBoolean(int num){
        return num==1;
    }

    public static List<Integer> booleanToInt(List<Boolean> arr){
        List<Integer> res = new ArrayList<>();
        for(var elem : arr){
            res.add(booleanToInt(elem));
        }
        return res;
    }

    public static int booleanToInt(Boolean elem){
        return elem ? 1 : 0;
    }

    public static List<List<Integer>> sort(List<List<Integer>> arr){
        for (int i = 0; i < arr.size() - 1; i++)
            for (int j = i + 1; j < arr.size(); j++)
                if (comparison(arr.get(i), arr.get(j)))
                    Collections.swap(arr, i, j);
        return arr;
    }

    public static boolean comparison(List<Integer> word_1, List<Integer> word_2)
    {
        boolean g = false, l = false;

        for (int i = 0; i < word_1.size(); i++) {

            if (g || (word_1.get(i) == 0 && word_2.get(i) == 1 && !l))
            g = true;
		else
            g = false;

            if (l || (word_1.get(i) == 1 && word_2.get(i) == 0 && !g))
            l = true;
		else
            l = false;
        }

        if (g)
            return true;

        if (l)
            return false;
        return false;
    }

    public static void print(List<List<Integer>> list){
        for (var objects : list) {
            for (var object : objects) {
                System.out.format("%3s", object);
            }
            System.out.println();
        }
    }
}

