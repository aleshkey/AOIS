package org.example;

import org.example.constants.Constants;
import org.example.minimization.Minimizer;
import org.example.util.Util;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        for(int i = 0; i< Constants.H_Values.get(0).size(); i++) {
            var sdnf = Util.toNF(Arrays.asList("A", "B", "C", "D", "V"), Util.unionTable(), Util.getHValues(i), false);
            System.out.println("SDNF h"+(i+1)+": "+sdnf);
            Minimizer.getShortForm(sdnf.replace(" ", ""), true);
        }
    }
}