package org.example;

import org.example.constants.Constants;
import org.example.minimization.Minimizer;
import org.example.util.Util;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Util.printTable();
        for(int i = 0; i< Constants.H_Values.get(0).size(); i++) {
            var sdnf = Util.toNF(Arrays.asList("A", "B", "C", "D", "V"), Util.unionTable(), Util.getValues(Constants.H_Values, i), false);
            System.out.println("SDNF H"+(i+1)+": "+sdnf);
            sdnf = Minimizer.NFtoTDF(sdnf.replaceAll(" ", ""), true);
            System.out.println(Minimizer.getShortForm(sdnf.replaceAll(" ", ""), true));
        }
    }
}