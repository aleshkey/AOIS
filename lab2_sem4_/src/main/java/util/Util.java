package util;

import java.util.LinkedList;

public class Util {

    public static String toRPN(String expression) {
        StringBuilder rpn = new StringBuilder();
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c != ' ') {
                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        rpn.append(stack.pop());
                    }
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else if (isOperator(c)) {
                    while (!stack.isEmpty() && priority(stack.peek()) >= priority(c)) {
                        rpn.append(stack.pop());
                    }
                    stack.push(c);
                } else {
                    rpn.append(c);
                }
            }
        }
        rpn.append(getStrFromStack(stack));
        return rpn.toString();
    }

    private static String getStrFromStack(LinkedList<Character> stack){
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        return res.toString();
    }

    public static boolean isOperator(char c) {
        return c == '&' || c == '|' || c == '!';
    }

    public static int priority(char c) {
        switch (c) {
            case '!':
                return 3;
            case '&':
                return 2;
            case '|':
                return 1;
            default:
                return 0;
        }
    }
}
