import Parser.Parser;
import operations.Operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Parser parser = new Parser();
    private static final List<List<Boolean> > table = new ArrayList<>();
    private static final List<Integer> sknf_indexes = new ArrayList<>();
    private static final List<Integer> sdnf_indexes = new ArrayList<>();

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

    private static void printTable(String[] variables, boolean[] result){
        for (String variable : variables) {
            System.out.format("%10s", variable);
        }
        System.out.format("%10s\n","Result");

        for (int i = 0; i < result.length; i++) {
            boolean[] values = parser.getValues(i, variables.length);
            List<Boolean> buffer = new ArrayList<>();
            for (boolean value : values) {
                buffer.add(value);
                System.out.format("%10s", value);
            }
            table.add(buffer);
            System.out.format("%10s\n", result[i]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a boolean expression: ");
        String expression = scanner.nextLine();

        String[] variables = makeUnique(expression.split("[&|!() ]+"));
        boolean[] result = new boolean[1 << variables.length];

        for (int i = 0; i < result.length; i++) {
            boolean[] values = parser.getValues(i, variables.length);
            result[i] = parser.parse(expression, values);
        }

        printTable(variables, result);
        System.out.println("\n");
        System.out.println(Operations.SDNF(variables, table, result));
        System.out.println("\n");
        System.out.println(Operations.SKNF(variables, table, result));
        System.out.println("\n");
        System.out.println(Operations.toIndexForm(table, result));
        System.out.println("\n");
        System.out.println(Operations.toNumberFormSDNF(Operations.SDNF(variables, table, result)));
        System.out.println("\n");
        System.out.println(Operations.toNumberFormSKNF(Operations.SKNF(variables, table, result)));
    }
}
