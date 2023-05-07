package org.example.util;

import org.example.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<List<Boolean>> unionTable(){
        List<List<Boolean>> temp = new ArrayList<>();
        for (var row : Constants.VARS_TABLE) {
            temp.add(new ArrayList<>(row));
        }
        for (int i =0; i< temp.size(); i++){
            temp.get(i).add(Constants.V_VALUES.get(i));
        }
        return temp;
    }

    public static List<Boolean> getHValues(int index){
        List<Boolean> res = new ArrayList<>();
        for (var list : Constants.H_Values){
            for (int i =0; i<list.size(); i++){
                if (i==index){
                    res.add(list.get(i));
                }
            }
        }
        return res;
    }

    public static String toNF(List<String> vars, List<List<Boolean>> table, List<Boolean> result, boolean isSKNF){
        StringBuilder res = new StringBuilder();
        String stringOperator = isSKNF ? "&" : "|";
        for (int index = 0; index < result.size(); index++) {
            if ((result.get(index) && !isSKNF) || (!result.get(index) && isSKNF)) {
                if (!res.toString().equals("")) {
                    res.append(" ").append(stringOperator).append(" ");
                }
                res.append('(');
                res.append(createSNF(vars, table.get(index), isSKNF));
                res.append(')');
            }
        }
        return res.toString();
    }

    private static String createSNF(List<String> vars, List<Boolean> booleans, boolean isSKNF){
        StringBuilder snf = new StringBuilder();
        String reversedStringOperator = isSKNF ? "|" : "&";
        for (int i = 0; i < vars.size(); i++) {
            if (i != 0) {
                snf.append(" ").append(reversedStringOperator).append(" ");
            }
            if ((booleans.get(i) && !isSKNF) || (!booleans.get(i) && isSKNF)) {
                snf.append(vars.get(i));
            } else {
                snf.append("!").append(vars.get(i));
            }
        }
        return snf.toString();
    }
}
