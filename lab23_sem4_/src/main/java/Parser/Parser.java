package Parser;

import operations.Operations;
import util.Util;

import java.util.Stack;

public class Parser {

    public boolean[] getValues(int n, int length) {
        boolean[] values = new boolean[length];
        for (int i = 0; i < length; i++) {
            values[length - i - 1] = (n & (1 << i)) != 0;
        }
        return values;
    }

    public boolean isBinaryOperator(char operator){
        return  operator == '|' || operator == '&';
    }

    public boolean isUnaryOperator(char operator){
        return  operator == '!';
    }

    private boolean intToBoolean(int input) {
        if((input==0)||(input==1)) {
            return input!=0;
        }else {
            throw new IllegalArgumentException("Входное значение может быть равно только 0 или 1 !");
        }
    }
    private boolean charToBoolean(char input) {
        if((input=='0')||(input=='1')) {
            return input!='0';
        }else {
            throw new IllegalArgumentException("Входное значение может быть равно только 0 или 1 !");
        }
    }

    public boolean parse(String expression, boolean[] values) {
        expression = Util.toRPN(expression);
        expression = replaceVariables(expression, values);
        Stack<Boolean> RPWToBool = new Stack<>();
        int counter =0;
        while(counter<expression.length()){
            if (isBinaryOperator(expression.charAt(counter))){
                RPWToBool.push(Operations.operate(RPWToBool.pop(), RPWToBool.pop(), expression.charAt(counter)));
            }
            else{
                if (isUnaryOperator(expression.charAt(counter))){
                    RPWToBool.push(Operations.operate(RPWToBool.pop(), expression.charAt(counter)));
                }
                else{
                    RPWToBool.push(intToBoolean(Integer.parseInt(String.valueOf(expression.charAt(counter)))));
                }
            }
            counter++;
        }
        return RPWToBool.pop();
    }

    public String replaceVariables(String expression, boolean[] values) {
        for (int i = 0; i < values.length; i++) {
            String str;
            if (values[i]){
                str="1";
            }
            else
                str = "0";
            expression = expression.replace(Character.toString((char) ('A' + i)), str);
        }
        return expression;
    }

    public String replaceVariables(String expression, char[] values) {
        for (int i = 0; i < values.length; i++) {
            String str = String.valueOf(values[i]);
            if (values[i] <= '9' && values[i] >= '0') {
                if (values[i] == '1') {
                    str = "1";
                } else
                    if (values[i] == '0')
                        str = "0";
            }
            expression = expression.replace(Character.toString((char) ('A' + i)), str);
        }
        return expression;
    }


}
