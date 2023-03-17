package operations;

import java.util.List;

import static java.lang.Math.pow;

public class BinaryOperations {
    public static String fromBoolToBinaryCode(List<Boolean> values){
        StringBuilder res = new StringBuilder();
        for(var v: values){
            if (v)
                res.append(1);
            else
                res.append(0);
        }
        return res.toString();
    }

    public static String reverse(String binaryCode){
        StringBuilder res = new StringBuilder();
        for(int i = 0; i<binaryCode.length(); i++){
            if (binaryCode.charAt(i)=='1')
                res.append('0');
            else
                res.append('1');
        }
        return res.toString();
    }

    public static int fromBinaryToInt(String binaryCode){
        int res = 0;
        for(int i = binaryCode.length()-1; i>=0; i--){
            res += Integer.parseInt(String.valueOf(binaryCode.charAt(i))) * pow(2, i);
        }
        return res;
    }

    public static String fromPartOfFormulaToBinaryCode(String formula){
        StringBuilder res= new StringBuilder();
        for(int i = 0; i< formula.length(); i++){
            if (formula.charAt(i)>'A' && formula.charAt(i)<'Z'){
                if (formula.charAt(i-1)=='!'){
                    res.append(0);
                }
                else res.append(1);
            }
        }
        return res.toString();
    }
}
