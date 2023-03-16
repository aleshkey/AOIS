import Parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static String[] makeUnique(String[] arr){
        List<String> res = new ArrayList<>();
        for(var str : arr){
            if (!res.contains(str) && !str.equals("")){
                res.add(str);
            }
        }
        String[] r = new String[res.size()];
        res.toArray(r);
        return r;
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a boolean expression: ");
        String expression = scanner.nextLine();

        String[] variables = makeUnique(expression.split("[&|!() ]+"));
        boolean[] result = new boolean[1 << variables.length];

        for (int i = 0; i < result.length; i++) {
            boolean[] values = parser.getValues(i, variables.length);
            result[i] = parser.parse(expression, values);
        }

        for (String variable : variables) {
            System.out.format("%10s", variable);
        }
        System.out.format("%10s\n","Result");

        List<List<String> > table = new ArrayList<>();
        List<Integer> sknf_indexes = new ArrayList<>();
        List<Integer> sdnf_indexes = new ArrayList<>();
        
        for (int i = 0; i < result.length; i++) {
            boolean[] values = parser.getValues(i, variables.length);
            for (boolean value : values) {

                System.out.format("%10s", value);
            }
            System.out.format("%10s\n", result[i]);
        }
    }
}