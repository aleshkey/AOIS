package org.example.searches;

import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Search {

    public static String comparison(String num1, String num2, int counter, boolean isGreater){
        if (num1.length() == num2.length()) {
            if (num1.charAt(counter) == num2.charAt(counter)) {
                if (counter == num1.length()-1)
                    return num1;
                return comparison(num1, num2, counter + 1, isGreater);
            } else {
                return (num1.charAt(counter) > num2.charAt(counter) && isGreater) ||
                       (num1.charAt(counter) < num2.charAt(counter) &&!isGreater) ? num1 : num2;
            }
        }
        else
            return (num1.length() > num2.length() && isGreater) ||
                   (num1.length() < num2.length() &&!isGreater) ? num1 : num2;
    }

    public static String findMinMax(List<String> arr, boolean isMax){
        if(arr.size()>0){
            var searched_value = String.valueOf(arr.get(0));
            for (var elem : arr){
                searched_value = comparison(searched_value, String.valueOf(elem), 0, isMax);
            }
            return searched_value;
        }
        else return "";
    }

    public static void getNearest(List<Integer> arr, int numToSearch, boolean isGreater){
        List<String> allSearchedValues = new ArrayList<>();
        for (int i =0; i< arr.size(); i++) {
            if (arr.get(i) != numToSearch) {
                allSearchedValues.add(comparison(String.valueOf(arr.get(i)), String.valueOf(numToSearch), 0, isGreater));
            }
            else {
                System.out.println(arr.get(i));
                return;
            }
        }
        allSearchedValues = Util.makeUnique(allSearchedValues);
        allSearchedValues.remove(String.valueOf(numToSearch));
        var searchedValue = findMinMax(allSearchedValues, !isGreater);
        if (!searchedValue.equals("")){
            System.out.println(searchedValue);
        }
        else {
            System.out.println("--");
        }
    }

    public static void getInterval(List<Integer> arr, int start, int finish){
        List<Integer> res = new ArrayList<>();
        for(var elem : arr){
            if (!comparison(String.valueOf(elem), String.valueOf(start), 0, true).equals(String.valueOf(start)) &&
                !comparison(String.valueOf(elem), String.valueOf(finish), 0, false).equals(String.valueOf(finish))){
                res.add(elem);
            }
        }
        System.out.println(res);
    }

}
