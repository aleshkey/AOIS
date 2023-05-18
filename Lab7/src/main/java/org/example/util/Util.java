package org.example.util;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<String> makeUnique(List<String> arr) {
        List<String> uniqueVars = new ArrayList<>();
        for(var str : arr){
            if (!uniqueVars.contains(str) && !str.equals("")){
                uniqueVars.add(str);
            }
        }
        return uniqueVars;
    }
}
