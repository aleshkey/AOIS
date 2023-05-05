package utils;

import constants.Constants;

import java.util.List;

public class Util {
    public static void printSummatorTable(){
        var table = Constants.SUMMATOR_TABLE;
        System.out.format("%10s%10s%10s |%10s%10s\n", "A", "B", "C", "P", "S");
        for (int i =0; i<table.size(); i++){
            for (var elem : table.get(i)){
                System.out.format("%10s", elem);
            }
            System.out.format(" |%10s", Constants.CARRY.get(i));
            System.out.format("%10s", Constants.RESULT.get(i));
            System.out.println();
        }
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
