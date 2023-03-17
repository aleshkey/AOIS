package operations;

import java.util.List;
import java.util.Objects;

import static java.lang.Math.pow;

public class Operations {

    public static boolean operate (Boolean var1, Boolean var2, char operator){
        return chooseOperation(var1, var2, operator);
    }

    private static boolean chooseOperation(Boolean var1, Boolean var2, char operator){
        boolean res =false;
        switch (operator){
            case '|': {
                res = Boolean.logicalOr(var1, var2);
                break;
            }
            case '&':{
                res = Boolean.logicalAnd(var1, var2);
                break;
            }
        }
        return res;
    }

    public static boolean operate (Boolean var1, char operator){
        if (operator=='!'){
            return !var1;
        }
        return false;
    }


    public static String SDNF(String[] vars, List<List<Boolean>> table, boolean[] result){
        StringBuilder res = new StringBuilder();
        for(int index = 0; index<result.length; index++){
            if (result[index]){
                if (!res.toString().equals("")){
                    res.append(" | ");
                }
                res.append('(');
                res.append(createSDNF(vars, table.get(index)));
                res.append(')');
            }
        }
        return res.toString();
    }

    private static String createSDNF(String[] vars, List<Boolean> booleans) {
        StringBuilder sdnf = new StringBuilder();
        for(int i=0; i < vars.length; i++){
            if(i != 0){
                sdnf.append(" & ");
            }
            if (booleans.get(i)){
                sdnf.append(vars[i]);
            }
            else{
                sdnf .append("!").append(vars[i]);
            }
        }
        return sdnf.toString();
    }

    public static String SKNF(String[] vars, List<List<Boolean>> table, boolean[] result){
        StringBuilder res = new StringBuilder();
        for(int index = 0; index<result.length; index++){
            if (!result[index]){
                if (!res.toString().equals("")){
                    res.append(" & ");
                }
                res.append('(');
                res.append(createSKNF(vars, table.get(index)));
                res.append(')');

            }
        }
        return res.toString();
    }

    private static String createSKNF(String[] vars, List<Boolean> booleans) {
        StringBuilder sknf = new StringBuilder();
        for(int i=0; i < vars.length; i++){
            if (!booleans.get(i)){
                sknf.append(vars[i]);
            }
            else{
                sknf.append("!").append(vars[i]);
            }
            if(i != vars.length-1){
                sknf.append(" | ");
            }
        }
        return sknf.toString();
    }

    public static int toIndexForm(List< List<Boolean> > table, boolean[] results){
        int res = 0;
        for (List<Boolean> booleans : table) {
            res += pow(2, BinaryOperations.fromBinaryToInt(BinaryOperations.reverse(BinaryOperations.fromBoolToBinaryCode(booleans))));
        }
        return res;
    }

    public static String toNumberFormSDNF(String SDNF){
        String res = "|(";
        String[] parts = SDNF.split("[|]");
        for (var str : parts){
            if (!Objects.equals(res, "|(")){
                res =  res+", ";
            }
            res = res + BinaryOperations.fromBinaryToInt(BinaryOperations.fromPartOfFormulaToBinaryCode(str));
        }
        return res+")";
    }

    public static String toNumberFormSKNF(String SDNF){
        String res = "&(";
        String[] parts = SDNF.split("[\\&]");
        for (var str : parts){
            if (!Objects.equals(res, "&(")){
                res =  res+", ";
            }
            res = res + BinaryOperations.fromBinaryToInt(BinaryOperations.fromPartOfFormulaToBinaryCode(str));
        }
        return res+")";
    }
}
