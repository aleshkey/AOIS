package minimization;

import operations.Operations;
import util.Util;

import java.util.*;
import java.util.stream.Stream;

public class Minimization {

    private static final int SIZE = 3;

    private static final List<String> topVars = new ArrayList<>(Arrays.asList("!B !C", "!B C", "B C", "B !C"));
    private static final List<String> leftVars = new ArrayList<>(Arrays.asList("!A", "A"));

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

        String[] termArray = originalString.split(reverseOperator);


        if (termArray.length > 1) {
            for (var term : termArray) {
                res.add(term.substring(1, term.length() - 1));
            }
        }
        else
            if (termArray.length!=0){
                res.add(originalString);
            }

        return res;
    }

    private static String findSharedPart(String s1, String s2, boolean isANDSeparator){
        char stringOperator = isANDSeparator ? '&' : '|';
        List<String> firstString = divideString(s1, isANDSeparator);
        List<String> secondString = divideString(s2, isANDSeparator);
        StringBuilder res = new StringBuilder();
        int counter = 0;
        for (String s : firstString) {
            for (String value : secondString) {
                if (s.equals(value)) {
                    counter++;
                    if (res.toString().equals("")) {
                        res.append(s);
                    } else res.append(stringOperator).append(s);
                }
            }
            if (counter == firstString.size()-1){
                break;
            }
        }
        return res.toString().equals("") || res.indexOf(String.valueOf(stringOperator)) == -1 ? "" : "("+res+")";
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
    }

    private static boolean checkReversed(List<String> expression1, List<String> expression2){
        if (expression1.size() == expression2.size()) {
            for (var str : expression2) {
                if (expression1.contains(reverse(str))) {
                    return true;
                }
            }
        }
         return false;
    }

    private static List<String> unionString(List<String> expression1, List<String> expression2){
         List<String> res = new ArrayList<>();
         for (var str : expression2){
             if (expression1.contains(str)){
                res.add(str);
             }
         }
         return res;
    }

    private static String fromListToNF(List<String> list, boolean isANDSeparator){
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
            String unique = "";
            if (checkExcess(functionParts, i, isANDSeparator)!=-1){
                List<String> tempPartList = Arrays.asList(functionParts.get(i).split(stringOperator));
                List<String> checkedPartList = Arrays.asList(functionParts.get(checkExcess(functionParts, i, isANDSeparator)).split(stringOperator));
                String union = fromListToNF(unionString(tempPartList, checkedPartList), isANDSeparator);
                unique = unique + "("+union+")";
                functionParts.remove(checkExcess(functionParts, i, isANDSeparator));
                functionParts.remove(i);
                i--;
            }
            else unique = unique + "("+functionParts.get(i)+")";
            if (res.toString().equals("")){
                res.append(unique);
            }
            else {
                if (res.indexOf(unique) == -1) {
                    res.append(reversedOperator).append(unique);
                }
            }
        }
        return res.toString();
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

    private static String getCarnoVars(String[] variables){
        StringBuilder vars = new StringBuilder();
         for (int i = 0; i < variables.length / 2; i++) {
            vars.append(variables[i]);
        }
        vars.append("\\");
        for (int i = variables.length / 2; i < variables.length; i++) {
            vars.append(variables[i]);
        }
        return vars.toString();
    }

    private static void printTopVars(){
        for (var s : topVars) {
            System.out.format("%20s", s);
        }
    }

    private static void carnoMinimization(String[] variables, boolean[] result) {
        String vars = getCarnoVars(variables);
        System.out.format("%20s", vars);
        printTopVars();
        List<List<Boolean>> parts =createKMap(result);

        for(int i = 0; i< leftVars.size(); i++){
            System.out.println();
            System.out.format("%20s", leftVars.get(i));
            for (var b : parts.get(i)){
                System.out.format("%20s", Operations.fromBoolToInt(b));
            }
        }

        printCarno(parts);

    }

    private static List<List<String>> checkHorizontal(List<List<Boolean>> parts){
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i<parts.size(); i++){
            for (int j = 0; j < parts.get(i).size(); j++){
                for (int counter = SIZE; counter>=0; counter--){
                    if (parts.get(i).get(j) && checkRow(parts.get(i), j, counter) && !res.contains(getArrayOfAtomTerms(j, counter, i))){
                        res.add(getArrayOfAtomTerms(j, counter, i));
                    }
                }
            }
        }
        return res;
    }

    private static List<List<String>> checkVertical(List<List<Boolean>> parts){
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < SIZE+1; i++){
            if (parts.get(0).get(i) && parts.get(1).get(i)){
                for (int counter = SIZE; counter>=0; counter--){
                    if (checkRow(parts.get(0), i, counter) && checkRow(parts.get(1), i, counter)){
                        var top = getArrayOfAtomTerms(i, counter, 0);
                        var down = getArrayOfAtomTerms(i, counter, 1);
                        if (!res.contains(unionString(top, down))){
                            res.add(unionString(top, down));
                        }
                    }
                }
            }
        }
        return res;
    }

    private static List<String> carno(List<List<Boolean>> parts){
        List<String> res = new ArrayList<>();
        var bufH = checkHorizontal(parts);
        var bufV = checkVertical(parts);
        List<List<String>> newList = new ArrayList<>();
        newList.addAll(bufH);
        newList.addAll(bufV);
        var allSubsets = Util.generateAllSubsets(newList);
        res = createNFList(enumeration(allSubsets, parts));
        return res;
    }

    private static List<String> createNFList(List<List<String>> bestVariant) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i<bestVariant.size(); i++){
            res.add("("+fromListToNF(bestVariant.get(i), false)+")");
        }
        return res;
    }

    private static boolean isSwitched(Map<String, Boolean> map){
        for (boolean value : map.values()) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    private static List<List<String>> enumeration(List<List<List<String>>> allSubsets, List<List<Boolean>> parts) {
        List<List<String>> res = new ArrayList<>();
        List<List<List<String>>> allVariants = new ArrayList<>();
        for (var subset : allSubsets){
            Map<String, Boolean> switchedKMap = createKMap(parts);
            switchedKMap = switchKMap(subset, switchedKMap);
            if (isSwitched(switchedKMap)){
                allVariants.add(subset);
            }
        }

        res = chooseBestVariant(allVariants);

        return res;
    }

    private static List<List<String>> chooseBestVariant(List<List<List<String>>> allVariants) {
        int minSize = allVariants.get(0).size();
        List<List<List<String>>> allBestVariants = new ArrayList<>();
        for (var variant : allVariants) {
            if (variant.size() < minSize){
                allBestVariants.clear();
                minSize = variant.size();
            }
            if (variant.size() == minSize){
                allBestVariants.add(variant);
            }
        }
        List<List<String>> res = allBestVariants.get(0);
        for (var bestVariant : allBestVariants){
            if (getMaxSize(bestVariant) < getMaxSize(res)){
                res = bestVariant;
            }
        }
        return res;

    }

    private static int getMaxSize(List<List<String>> listVariants){
        int max = -1;
        for(var variant : listVariants){
            if (max < variant.size()){
                max = variant.size();
            }
        }
        return max;
    }

    private static Map<String, Boolean> switchKMap(List<List<String>> list, Map<String, Boolean> map){
        for (var elem : list){
            for (int i = 0; i<leftVars.size(); i++){
                for (int j =0; j<topVars.size(); j++){
                    List<String> vars = new ArrayList<>(Collections.singleton(leftVars.get(i)));
                    vars.addAll(Arrays.asList(topVars.get(j).split(" ")));
                    if (vars.containsAll(elem)){
                        map.put(leftVars.get(i)+" "+topVars.get(j), true);
                    }
                }
            }
        }
        return map;
    }

    private static Map<String, Boolean> createKMap(List<List<Boolean>> parts){
        Map<String, Boolean> res  = new HashMap<>();
        for (int i =0 ;i <parts.size(); i++){
            for (int j =0; j<parts.get(i).size(); j++){
                if (parts.get(i).get(j)){
                    res.put(leftVars.get(i)+" "+topVars.get(j), false);
                }
            }
        }
        return res;
    }

    private static List<String> getArrayOfAtomTerms(int start, int count, int otherTerm){
        List<List<String>> allTerms = new ArrayList<>();
        if (start+count< topVars.size()) {
            for (int i = start; i < start + count + 1; i++) {
                var buff = new ArrayList<>(Arrays.asList(topVars.get(i).split(" ")));
                buff.add(leftVars.get(otherTerm));
                allTerms.add(buff);
            }
        }
        else {
            for (int i = start; i < topVars.size(); i++) {
                var buff = new ArrayList<>(Arrays.asList(topVars.get(i).split(" ")));
                buff.add(leftVars.get(otherTerm));
                allTerms.add(buff);
            }
            for (int i = 0; i < start+count-topVars.size(); i++) {
                var buff = new ArrayList<>(Arrays.asList(topVars.get(i).split(" ")));
                buff.add(leftVars.get(otherTerm));
                allTerms.add(buff);
            }
        }
        List<String> res = new ArrayList<>(allTerms.get(0));
        for(int i = 1; i< allTerms.size(); i++){
            res = unionString(res, allTerms.get(i));
        }
        return res;
    }


    private static boolean isPower(double a, double b)
    {
        return (int)(Math.log(a)/Math.log(b))==(Math.log(a)/Math.log(b));
    }

    private static boolean checkRow(List<Boolean> list, int start, int count){
         if (isPower(count+1, 2) || count == 0) {
             if (start+count < list.size()) {
                 for (int i = start; i < start + count + 1; i++) {
                     if (!list.get(i)) {
                         return false;
                     }
                 }
             }
             else {
                 for (int i = start; i < list.size(); i++) {
                     if (!list.get(i)) {
                         return false;
                     }
                 }
                 for (int i = 0; i < start+count-list.size()+1; i++){
                     if (!list.get(i)) {
                         return false;
                     }
                 }
             }
             return true;
         }
         else return false;
    }

    private static void printCarno(List<List<Boolean>> parts) {
         List<String> res = carno(parts);
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
        var r = NFtoTDF(result.toString(), isANDSeparator);
        System.out.println("\nИтог минимизации: "+r);
    }

    private static List<String> makeUnique(List<String> list){
        Set<String> set = new HashSet<>(list);
        list.clear();
        list.addAll(set);
        list.removeAll(Arrays.asList(null, "()"));
        return list;
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

        System.out.println("\nТабличный метод");
        carnoMinimization(variables, result);
    }

}
