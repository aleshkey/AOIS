import constants.Constants;
import minimization.Minimizer;
import utils.Util;

import java.util.Arrays;

public class Main {
    public static void task1() {
        Util.printSummatorTable();
        System.out.println("-----------------------------------------------------P-----------------------------------------------------");
        System.out.println(Util.toNF(Arrays.asList("A", "B", "C"), Constants.SUMMATOR_TABLE, Constants.CARRY, true));
        Minimizer.getShortForm(Util.toNF(Arrays.asList("A", "B", "C"), Constants.SUMMATOR_TABLE, Constants.CARRY, true)
                                   .replaceAll(" ", ""), false);
        System.out.println("-----------------------------------------------------S-----------------------------------------------------");
        System.out.println(Util.toNF(Arrays.asList("A", "B", "C"), Constants.SUMMATOR_TABLE, Constants.RESULT, true));
        Minimizer.getShortForm(Util.toNF(Arrays.asList("A", "B", "C"), Constants.SUMMATOR_TABLE, Constants.RESULT, true)
                                   .replaceAll(" ", ""), false);

    }

    public static void task2(){

    }

    public static void main(String[] args) {
        task1();

    }
}
