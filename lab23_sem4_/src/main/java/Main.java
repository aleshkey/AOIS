import Parser.Parser;
import minimization.Minimization;
import operations.Operations;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Parser parser = new Parser();
    private static final List<List<Boolean> > table = new ArrayList<>();

    //(A | B) & (A | !C)

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
        System.out.println("\n");
        System.out.println(Operations.SDNF(variables, table, result));
        System.out.println("\n");
        System.out.println(Operations.SKNF(variables, table, result));
        System.out.println("\n");
        System.out.println(Operations.toIndexForm(result));
        System.out.println("\n");
        System.out.println(Operations.toNumberFormSDNF(result));
        System.out.println("\n");
        System.out.println(Operations.toNumberFormSKNF(result));

        System.out.println("\n");
        Minimization.minimize(variables, table, result);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a boolean expression: ");
        String expression = scanner.nextLine();
        String[] variables = Util.makeUnique(expression.split("[&|!()> ]+"));
        boolean[] result = new boolean[1 << variables.length];
        for (int index = 0; index < result.length; index++) {
            boolean[] values = parser.getValues(index, variables.length);
            result[index] = parser.parse(expression, values);
        }
        printTable(variables, result);
    }
}
