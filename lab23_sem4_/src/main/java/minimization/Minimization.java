package minimization;

import operations.Operations;
import util.Util;

import java.util.*;

public class Minimization {
     private static boolean find(List<String> originalString, String stringToFind){
        return originalString.contains(stringToFind);
    }

    private static String reverse(String originalString){
        return (originalString.charAt(0)=='!') ? originalString.substring(1) : "!" + originalString;
    }

    private static List<String> divideString(String originalString, boolean isANDSeparator){
        String operator = isANDSeparator ? "&" : "\\|";
        return Arrays.asList(originalString.split(operator));
    }

    private static List<String> divideStringWithBrackets(String originalString, boolean isANDSeparator){
        List<String> res = new ArrayList<>();

        String reverseOperator = isANDSeparator ? "\\|" : "&";

        String[] buff = originalString.split(reverseOperator);


        if (buff.length > 1) {
            for (var b : buff) {
                res.add(b.substring(1, b.length() - 1));
            }
        }
        else
            if (buff.length!=0){
                res.add(originalString);
            }

        return res;
    }

    private static String findSharedPart(String s1, String s2, boolean isANDSeparator){
        char stringOperator = isANDSeparator ? '&' : '|';
        List<String> a = divideString(s1, isANDSeparator);
        List<String> b = divideString(s2, isANDSeparator);
        StringBuilder res = new StringBuilder();
        int counter = 0;
        for (String s : a) {
            for (int j = 0; j < b.size(); j++) {
                if (s.equals(b.get(j))) {
                    counter++;
                    if (res.toString().equals("")) {
                        res.append(s);
                    } else res.append(stringOperator).append(s);
                }
            }
            if (counter == a.size()-1){
                break;
            }
        }
        return res.equals("") || res.indexOf(String.valueOf(stringOperator)) == -1 ? "" : "("+res+")";
    }


    private static String gluing(String originalString, boolean isANDSeparator){
        char reverseOperator = isANDSeparator ? '|' : '&';
        StringBuilder resultString = new StringBuilder();

        List<String> functionalParts = divideStringWithBrackets(originalString, isANDSeparator);

        for (int i = 0; i<functionalParts.size(); i++){
            for (int j = i + 1; j < functionalParts.size(); j++) {
                if (!findSharedPart(functionalParts.get(i), functionalParts.get(j), isANDSeparator).equals("")) {
                    if (resultString.toString().equals("")) {
                        resultString.append(findSharedPart(functionalParts.get(i), functionalParts.get(j), isANDSeparator));
                    } else
                        resultString.append(reverseOperator).append(findSharedPart(functionalParts.get(i), functionalParts.get(j), isANDSeparator));
                }
            }
            functionalParts.remove(i);
            i--;
        }
        return resultString.toString();
    }

    private static int checkExcess(List<String> functionParts, int index, boolean isANDSeparator){
        List<List<String>> constituentsMatrix = new ArrayList<>();
        int res = -1;
        for (String functionPart : functionParts) {
            constituentsMatrix.add(divideString(functionPart, isANDSeparator));
        }
        List<String> tempPart = constituentsMatrix.get(index);
        for(int i =0; i < constituentsMatrix.size(); i++){
            if (unionString(constituentsMatrix.get(i), tempPart).size() == tempPart.size()-1 && checkReversed(constituentsMatrix.get(i), tempPart)){
                res = i;
            }
        }
        return res;
        /*List<List<String>> constituentsMatrix = new ArrayList<>();
        for (String functionPart : functionParts) {
            constituentsMatrix.add(divideString(functionPart, isANDSeparator));
        }
        List<String> tempPart = constituentsMatrix.get(index);
        for(int counter =0; counter <tempPart.size(); counter++) {
            List<String> neighbors = new ArrayList<>();
            for (List<String> matrix : constituentsMatrix) {
                if (find(matrix, tempPart.get(counter))) {
                    for (String s : matrix) {
                        if (!tempPart.get(counter).equals(s)) {
                            neighbors.add(s);
                        }
                    }
                }
            }
            for (String s : tempPart) {
                if (find(neighbors, reverse(s))) {
                    return true;
                }
            }
        }
        return false;
    */
    }

    private static boolean checkReversed(List<String> expression1, List<String> expression2){
         for (var s : expression2){
             if (expression1.contains(reverse(s))){
                 return true;
             }
         }
         return false;
    }

    private static List<String> unionString(List<String> expression1, List<String> expression2){
         List<String> res = new ArrayList<>();
         for (var s : expression2){
             if (expression1.contains(s)){
                res.add(s);
             }
         }
         return res;
    }

    private static String fromListToNF(List<String> list, boolean isANDSeparator){
         String stringOperator = isANDSeparator ? "&" : "|";
         String reversedOperator = !isANDSeparator ? "&" : "|";
         StringBuilder res = new StringBuilder();
         for (var el : list){
             if (res.toString().equals(""))
                res.append(el);
             else {
                 res.append(reversedOperator).append(el);
             }
         }
         return res.toString();
    }

    private static String NFtoTDF(String originalString, boolean isANDSeparator){
        List<String> functionParts = divideStringWithBrackets(originalString, isANDSeparator);
        StringBuilder res = new StringBuilder();
        String stringOperator = isANDSeparator ? "&" : "\\|";
        String reversedOperator = !isANDSeparator ? "&" : "|";
        for (int i = 0; i< functionParts.size(); i++){
            String buff = "";
            if (checkExcess(functionParts, i, isANDSeparator)!=-1){
                List<String> tempPartList = Arrays.asList(functionParts.get(i).split(stringOperator));
                List<String> checkedPartList = Arrays.asList(functionParts.get(checkExcess(functionParts, i, isANDSeparator)).split(stringOperator));
                String union = fromListToNF(unionString(tempPartList, checkedPartList), isANDSeparator);
                buff = buff + "("+union+")";
                functionParts.remove(checkExcess(functionParts, i, isANDSeparator));
                functionParts.remove(i);
                i--;
            }
            else buff = buff + "("+functionParts.get(i)+")";
            if (res.toString().equals("")){
                res.append(buff);
            }
            else {
                if (res.indexOf(buff) == -1) {
                    res.append(reversedOperator).append(buff);
                }
            }
        }
        return res.toString();
        /*List<String> functionParts = divideStringWithBrackets(originalString, isANDSeparator);
        for (int i = 0; i< functionParts.size(); i++){
            if (checkExcess(functionParts, i, isANDSeparator)){
                var check = originalString.indexOf(functionParts.get(i));
                String buffer = originalString.substring(originalString.indexOf(functionParts.get(i))+functionParts.get(i).length()+2);
                originalString = originalString.substring(0, originalString.indexOf(functionParts.get(i))-1)+originalString.substring(originalString.indexOf(functionParts.get(i))+functionParts.get(i).length()+2);
                functionParts.remove(i);
                i--;
            }
        }
        return originalString;*/
    }

    private static boolean exist (String stringWhat, String stringIn, boolean isANDSeparator){
         String reverseOperator = isANDSeparator ? "\\|" : "&";
         List<String> atomicFormulasInImplications = Arrays.asList(stringWhat.split(reverseOperator));
         List<String> atomicFormulasInConstituents = Arrays.asList(stringIn.substring(1, stringIn.length()-1).split(reverseOperator));
         for(var formulaInImplications : atomicFormulasInImplications){
            if (!atomicFormulasInConstituents.contains(formulaInImplications)){
                return false;
            }
         }
         return true;
    }

    private static void printTable(String SNF, boolean isANDSeparator){
        System.out.format("%20s", "----------");
        var constituents = divideString(SNF, !isANDSeparator);
        for (var str : constituents){
            System.out.format("%20s", str);
        }

        var implications = divideString(gluing(SNF, isANDSeparator), !isANDSeparator);

        List<List<Integer>> constituentsToImplications = new ArrayList<>();

        for (String implication : implications) {
            System.out.format("\n%20s", implication);
            List<Integer> thisConstituentImplications = new ArrayList<>();
            for (int constituentsIndex = 0; constituentsIndex < constituents.size(); constituentsIndex++) {
                if (exist(implication.substring(1, implication.length() - 1), constituents.get(constituentsIndex), !isANDSeparator)) {
                    System.out.format("%20s", "X");
                    thisConstituentImplications.add(constituentsIndex);
                } else System.out.format("%20s", "--");
            }
            constituentsToImplications.add(thisConstituentImplications);
        }

        List<Integer> resId = new ArrayList<>();

        Map <Integer, Boolean> switchedConstituents = createHashMap(constituents);

        for (Map.Entry<Integer, Boolean> entry : switchedConstituents.entrySet()) {
            if (getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).size() == 1){
                switchedConstituents = turnOnConstituent(switchedConstituents , constituentsToImplications.get(getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).get(0)));
                resId.add(getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).get(0));
            }
        }


        while (!isStrongTable(switchedConstituents)){
            int bestChoice = chooseBestVariant(switchedConstituents, constituentsToImplications);
            switchedConstituents = turnOnConstituent(switchedConstituents , constituentsToImplications.get(bestChoice));
            resId.add(bestChoice);
        }

        printResult(implications, resId, isANDSeparator);
    }

    private static int chooseBestVariant(Map<Integer, Boolean> switchedConstituents, List<List<Integer>> constituentsToImplications) {

         Map<Integer, Integer> implicationsToNumberUnswitchedConstituents = new HashMap<>();
         List<Integer> unswitched = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : switchedConstituents.entrySet()) {
            if (!entry.getValue()) {
                unswitched.add(entry.getKey());
            }
        }

         for (int index = 0; index<constituentsToImplications.size(); index++){
             implicationsToNumberUnswitchedConstituents.put(index, intersection(unswitched, constituentsToImplications.get(index)).size());
         }

         return findMax(implicationsToNumberUnswitchedConstituents);
    }

    private static int findMax(Map<Integer, Integer> implicationsToNumberUnswitchedConstituents) {
        int max = -1;
        int index = -1;
        for (Map.Entry<Integer, Integer> entry : implicationsToNumberUnswitchedConstituents.entrySet()) {
            if (entry.getValue() >= max){
                max = entry.getValue();
                index = entry.getKey();
            }
        }
        return index;
    }

    private static List<Integer> intersection(List<Integer> list1, List<Integer> list2){
        List<Integer> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);
        return intersection;
    }

    private static Map<Integer, Boolean> turnOnConstituent(Map<Integer, Boolean> map, List<Integer> ids){
         for (var id : ids){
             map.put(id, true);
         }
         return map;
    }

    private static Map<Integer, Boolean> createHashMap(List<String> constituents) {
         Map<Integer, Boolean> res = new HashMap<>();
         for(int i = 0; i < constituents.size(); i++){
             res.put(i, false);
         }
         return res;
    }


    private static List<Integer> getImplicationsOfConstituents(int constituent, List<List<Integer>> constituentsToImplications){
         List<Integer> res = new ArrayList<>();
         for(int i =0;i<constituentsToImplications.size(); i++){
             if (constituentsToImplications.get(i).contains(constituent)){
                 res.add(i);
             }
         }
         return res;
    }



    private static boolean isStrongTable(Map<Integer, Boolean> switchedConstituents){
         for (boolean value : switchedConstituents.values()) {
            if (!value) {
                return false;
            }
        }
         return true;
    }

    private static void printResult(List<String> implications, List<Integer> resId, boolean isANDSeparator) {
         String reverseOperator = isANDSeparator ? "|" : "&";
         StringBuilder res = new StringBuilder();
        for (Integer integer : resId) {
            if (!res.toString().equals("")) {
                res.append(reverseOperator);
            }
            res.append(implications.get(integer));
        }
        System.out.println("\n"+res+"\nИтог минимизации: "+NFtoTDF(res.toString(), isANDSeparator));
    }

    private static void carnoMinimization(String[] variables, List<List<Boolean>> table, boolean[] result) {
        List<String> res = new ArrayList<>();
        String vars = "";
        for (int i = 0; i < variables.length / 2; i++) {
            vars = vars + variables[i];
        }
        vars = vars + "\\";
        for (int i = variables.length / 2; i < variables.length; i++) {
            vars = vars + variables[i];
        }

        System.out.format("%10s", "\n" + vars);
        System.out.format("%10s", "00");
        System.out.format("%10s", "01");
        System.out.format("%10s", "11");
        System.out.format("%10s", "10");

        List<List<Boolean>> parts =createKMap(result);

        for(int i = 0; i< 2; i++){
            System.out.format("%10s", "\n"+i);
            for (var b : parts.get(i)){
                System.out.format("%10s", Operations.fromBoolToInt(b));

            }
        }

        printCarno(parts, variables);

    }

    private static List<String> carno(List<List<Boolean>> parts, String[] variables, boolean isANDSeparator){
        List<String> res = new ArrayList<>();
        if (!isANDSeparator)
            parts = reverseList(parts);
        for(int i=0; i< parts.size(); i++){
            for (int j =0; j< parts.get(i).size(); j++){
                if (j!=parts.get(i).size()-1 && parts.get(i).get(j) && parts.get(i).get(j+1)){
                    res.add(chooseFormula(variables ,i, true, isANDSeparator));
                }
                if (i!=parts.size()-1 && parts.get(i).get(j) && parts.get(i+1).get(j)){
                    res.add(chooseFormula(variables, j, false,isANDSeparator));
                }
            }
        }
        res = makeUnique(res);
        return res;
    }

    private static List<List<Boolean>> reverseList(List<List<Boolean>> parts) {
         List<List<Boolean>> res = new ArrayList<>();
         for (var list : parts){
             List<Boolean> buff = new ArrayList<>();
             for (var bool : list){
                 buff.add(!bool);
             }
             res.add(buff);
         }
         return res;
    }

    private static void printCarno(List<List<Boolean>> parts, String[] variables) {
         List<String> res = carno(parts, variables, true);
         printCarnoRes(res, true);
    }

    private static void printCarnoRes(List<String> res, boolean isANDSeparator) {
        StringBuilder result = new StringBuilder();
        String reverseOperator = isANDSeparator ? "|" : "&";
        for (var s : res){
            if (result.toString().equals("")){
                result = new StringBuilder(s);
            }
            else {
                result.append(reverseOperator).append(s);
            }
        }
        System.out.println("\nИтог минимизации: "+result);
    }

    private static List<String> makeUnique(List<String> list){
        Set<String> set = new HashSet<>(list);
        list.clear();
        list.addAll(set);
        list.removeAll(Collections.singleton(null));
        return list;
    }

    private static String chooseFormula(String[] variables, int index, boolean isHorizontal, boolean isANDSeparator) {
         String stringOperator = isANDSeparator ? "&" : "|";
        if (isHorizontal){
            return index == 1 ? variables[0] : "!"+variables[0];
        }
        else{
            switch (index){
                case 0:{
                    return "(!"+variables[1]+stringOperator+"!"+variables[2]+")";
                }
                case 1:{
                    return "(!"+variables[1]+stringOperator+variables[2]+")";
                }
                case 2:{
                    return "("+variables[1]+stringOperator+variables[2]+")";
                }
                default:{
                    return "("+variables[1]+stringOperator+"!"+variables[2]+")";
                }
            }
        }
     }

    private static List<List<Boolean>> createKMap(boolean[] result) {
        List<List<Boolean>> res = new ArrayList<>();
        int counter =0;
        for(int i =1; i<3; i++){
            List<Boolean> buff = new ArrayList<>();
            while (counter < result.length/2*i){
                if (result.length/2*i - counter == 2){
                    buff.add(result[counter+1]);
                }
                else
                    if (result.length/2*i - counter == 1){
                        buff.add(result[counter-1]);
                    }
                    else
                        buff.add(result[counter]);
                counter++;
            }
            res.add(buff);
        }
        return res;
    }


    public static void minimize(String[] variables, List<List<Boolean>> table, boolean[] result) {
        String DNF;
        String defaultSDNF = Operations.SDNF(variables, table, result).replaceAll(" ", "");
        String defaultSCNF = Operations.SKNF(variables, table, result).replaceAll(" ", "");
        String TDNF;
        String CNF;
        String TCNF;
        DNF = gluing(defaultSDNF, true);
        TDNF = NFtoTDF(DNF, true);
        CNF = gluing(defaultSCNF, false);
        TCNF = NFtoTDF(CNF, false);

        System.out.println("Расчётный метод:");
        System.out.println("Исходная функция в СДНФ: " + defaultSDNF);

        System.out.println( "ДНФ: " + DNF);
        System.out.println("ТДНФ: " +TDNF );
        System.out.println("Исходная функция в СКНФ: " + defaultSCNF);
        System.out.println("КНФ: " +CNF);
        System.out.println("ТКНФ: " + TCNF);

        System.out.println("\n\nРасчетно-табличный метод\nСДНФ: "+defaultSDNF+"\n");
        printTable(defaultSDNF, true);

        System.out.println("\n\nРасчетно-табличный метод\nСКНФ: "+defaultSCNF+"\n");
        printTable(defaultSCNF, false);

        carnoMinimization(variables, table, result);
    }

}
