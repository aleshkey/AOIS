package org.example.searches;

import org.example.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Search {

   private static boolean comparison(String word1, String word2){
       boolean g = false;
       boolean l = false;
       for (int i = 0; i< word1.length(); i++){
           if (g || (word1.charAt(i) == '0' && word2.charAt(i) == '1' && !l)){
               g =true;
           }
           else
               g = false;
           if (l || (word1.charAt(i) == '1' && word2.charAt(i) == '0' && !g)){
               l = true;
           }
           else
               l= false;
       }
       if (g)
           return true;

       if (l)
           return false;
        return false;
   }

    public static List<String> sort(List<String> arr, boolean MAX_TO_MIN){
       for (int i =0; i< arr.size()-1; i++){
           for(int j=i+1;j<arr.size();j++){
               if((comparison(arr.get(i),arr.get(j)) && MAX_TO_MIN) ||(!comparison(arr.get(i),arr.get(j)) && !MAX_TO_MIN)) {
                   String temp = arr.get(i);
                   arr.set(i, arr.get(j));
                   arr.set(j, temp);
               }
           }
       }
       return arr;
    }

    private static List<String> findMinMax(String word, List<String> arr, boolean isMax){
       List<String> res = new ArrayList<>();
       for(var elem : arr){
           if ((comparison(word, elem) && isMax) || (!comparison(word, elem) && !isMax)){
               res.add(elem);
           }
       }
       return res;
    }

    public static String getNearest(int value, List<String> arr, boolean isGreater){
        var binary = Util.toBinary(value);
        var searched_value = sort(findMinMax(binary, arr, isGreater), !isGreater);
        return searched_value.size()!=0 ? searched_value.get(0) : "";
    }

    public static List<String> border(int start, int finish, List<String> arr){
       var binaryStart = Util.toBinary(start);
       var binaryFinish = Util.toBinary(finish);

       var maxBorder = borderFind(binaryStart, arr, true);
       var minBorder = borderFind(binaryFinish, arr, false);
       return sort(Util.intersection(maxBorder, minBorder),false);
    }

    private static List<String> borderFind(String value, List<String> arr, boolean isMax){
       List<String> res = new ArrayList<>();
        for (String elem : arr) {
            if ((comparison(value, elem) && isMax) || (!comparison(value, elem) && !isMax)) {
                res.add(elem);
            }
        }
       return res;
    }
}
