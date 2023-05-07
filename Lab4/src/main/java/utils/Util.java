package utils;

import constants.Constants;
import minimization.Minimizer;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static void printConverterTable(){
        for (int i = Constants.CONVERTER_TABLE.get(0).size()-1; i>=0; i--){
            System.out.format("%10s |", (char)('A'+i));
            for(int j =0; j<Constants.CONVERTER_TABLE.size(); j++){
                System.out.format("%10s", Constants.CONVERTER_TABLE.get(j).get(i));
            }
            System.out.println();
        }
        System.out.println("-----------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (int i = Constants.Y_TABLE.get(0).size()-1; i>=0; i--){
            System.out.format("%10s |", "Y"+(i+1));
            for(int j =0; j<Constants.Y_TABLE.size(); j++){
                System.out.format("%10s", Constants.Y_TABLE.get(j).get(i));
            }
            System.out.println();
        }
    }

    public static void convert(){
        List<List<Boolean>> table = new ArrayList<>(Constants.CONVERTER_TABLE.subList(0, Constants.Y_TABLE.size()));
        for (int i = 0; i<Constants.Y_TABLE.get(0).size(); i++){
            var yValues = getYValues(i);
            var SKNF = toNF(Arrays.asList("A", "B", "C", "D"), table, yValues, true);
            System.out.println("SKNF (Y"+(i+1)+") = "+SKNF);
            Minimizer.getShortForm(SKNF.replaceAll(" ", ""), false);

        }
    }

    private static List<Boolean> getYValues(int index){
        List<Boolean> res = new ArrayList<>();
        for (var list : Constants.Y_TABLE){
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
