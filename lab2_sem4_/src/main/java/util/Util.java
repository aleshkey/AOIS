package util;

import java.util.Stack;

public class Util {

    public static String toRPN(String expression) {
        StringBuilder rpn = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == ' ') {
                continue;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    rpn.append(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop(); // удаляем открывающую скобку
                }
            } else if (isOperator(c)) { // оператор - добавляем в стек
                while (!stack.isEmpty() && priority(stack.peek()) >= priority(c)) {
                    rpn.append(stack.pop());
                }
                stack.push(c);
            } else { // операнд - добавляем в ОПЗ
                rpn.append(c);
            }
        }

        while (!stack.isEmpty()) { // извлекаем оставшиеся операторы из стека и добавляем в ОПЗ
            rpn.append(stack.pop());
        }

        return rpn.toString();
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
