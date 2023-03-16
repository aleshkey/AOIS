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

    private boolean isBinaryOperator(char operator){
        return  operator == '|' || operator == '&';
    }

    private boolean isUnaryOperator(char operator){
        return  operator == '!';
    }

    private boolean intToBoolean(int input) {
        if((input==0)||(input==1)) {
            return input!=0;
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

    private String replaceVariables(String expression, boolean[] values) {
        for (int i = 0; i < values.length; i++) {
            String str;
            if (values[i]){
                str="1";
            }
            else str = "0";
            expression = expression.replace(Character.toString((char) ('A' + i)), str);
        }
        return expression;
    }


}
