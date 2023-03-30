package util;

import java.util.*;

public class Util {

    public static char[] getStringDifference(String s1, String s2) {
        HashSet<Character> set1 = new HashSet<>();
        HashSet<Character> set2 = new HashSet<>();

        for (char c : s1.toCharArray()) {
            set1.add(c);
        }

        for (char c : s2.toCharArray()) {
            set2.add(c);
        }

        set1.removeAll(set2);

        StringBuilder result = new StringBuilder();
        for (char c : set1) {
            result.append(c);
        }

        char[] res = removeOperators(result.toString()).toCharArray();
        Arrays.sort(res);
        return res;
    }

    private static String removeOperators(String s){
        s = s.replace("!", "");
        s = s.replace("&", "");
        s = s.replace("|", "");
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace(" ", "");
        return s;
    }


    public static List<List<String>> gluingPKNF(String PDNF){
        String[] parts = PDNF.split(" \\| ");
        String res = "";
        List<List<String>> buff = new ArrayList<>();
        for(int i =0; i<parts.length; i++) {
            for (int j =i; j<parts.length; j++) {
                if (i!=j){
                    buff.add(glue2PartsPDNF(parts[i], parts[j]));
                }
            }
        }
        return makeUnique(buff);
    }

    private static List<String> glue2PartsPDNF(String f1, String f2){
        f1 = f1.substring(1, f1.length() - 1);
        f2 = f2.substring(1, f2.length() - 1);
        String[] f1Parts = f1.split(" & ");
        String[] f2Parts = f2.split(" & ");
        List<String> gluingParts = new ArrayList<>();
        String res = "";
        for(int i =0; i<f1Parts.length; i++){
            if (exist(f1Parts[i], f2Parts)){
                gluingParts.add(f1Parts[i]);
            }
        }
        if (gluingParts.size() == f1Parts.length-1){
            res = "("+fromListToKNF(gluingParts)+")";
            return gluingParts;
        }
        return null;
    }

    private static List<List<String>> makeUnique(List<List<String>> list){
        Set<List<String>> set = new HashSet<>(list);
        list.clear();
        list.addAll(set);
        list.removeAll(Collections.singleton(null));
        return list;
    }

    public static String[] makeUnique(String[] arr){
        List<String> uniqueVars = new ArrayList<>();
        for(var str : arr){
            if (!uniqueVars.contains(str) && !str.equals("")){
                uniqueVars.add(str);
            }
        }
        String[] res = new String[uniqueVars.size()];
        uniqueVars.toArray(res);
        return res;
    }

    public static String fromListToKNF(List<String> list) {
        String res ="";
        for (int i =0; i<list.size();i++){
            if (!res.equals("")){
                res  = res + " | ";
            }
            res = res + list.get(i);
        }
        return res;
    }


    private static boolean exist(String str, String[] arr){
        for(int i =0; i< arr.length; i++){
            if (arr[i].equals(str)){
                return true;
            }
        }
        return false;
    }

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
