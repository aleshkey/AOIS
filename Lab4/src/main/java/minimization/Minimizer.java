package minimization;

import java.util.*;

public class Minimizer {

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

    private static List<String> divideString(String originalString, boolean isANDSeparator){
        String operator = isANDSeparator ? "&" : "\\|";
        var res = Arrays.asList(originalString.split(operator));
        return res.get(0).equals("") ? new ArrayList<>() : res;
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

    public static void getShortForm(String SNF, boolean isANDSeparator) {
        var constituents = divideString(SNF, !isANDSeparator);
        var implications = divideString(gluing(SNF, isANDSeparator), !isANDSeparator);
        List<List<Integer>> constituentsToImplications = new ArrayList<>();
        if (implications.size() != 0) {
            for (String implication : implications) {
                List<Integer> thisConstituentImplications = new ArrayList<>();
                for (int constituentsIndex = 0; constituentsIndex < constituents.size(); constituentsIndex++) {
                    if (exist(implication.substring(1, implication.length() - 1), constituents.get(constituentsIndex), !isANDSeparator)) {
                        thisConstituentImplications.add(constituentsIndex);
                    }
                }
                constituentsToImplications.add(thisConstituentImplications);
            }
            List<Integer> resId = new ArrayList<>();
            Map<Integer, Boolean> switchedConstituents = createHashMap(constituents);
            for (Map.Entry<Integer, Boolean> entry : switchedConstituents.entrySet()) {
                if (getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).size() == 1) {
                    switchedConstituents = turnOnConstituent(switchedConstituents,
                            constituentsToImplications.get(
                                    getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).get(0))
                    );
                    resId.add(getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).get(0));
                }
            }
            while (!isStrongTable(switchedConstituents)) {
                int bestChoice = chooseBestVariant(switchedConstituents, constituentsToImplications);
                switchedConstituents = turnOnConstituent(switchedConstituents, constituentsToImplications.get(bestChoice));
                resId.add(bestChoice);
            }
            printResult(implications, resId, isANDSeparator);
        }
        else System.out.println("Итог минимизации: "+SNF);
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

    private static List<String> unionString(List<String> expression1, List<String> expression2){
        List<String> res = new ArrayList<>();
        for (var str : expression2){
            if (expression1.contains(str)){
                res.add(str);
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

    private static String reverse(String originalString){
        return (originalString.charAt(0)=='!') ? originalString.substring(1) : "!" + originalString;
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

    private static void printResult(List<String> implications, List<Integer> resId, boolean isANDSeparator) {
        String reverseOperator = isANDSeparator ? "|" : "&";
        StringBuilder res = new StringBuilder();
        for (Integer integer : resId) {
            if (!res.toString().equals("")) {
                res.append(reverseOperator);
            }
            res.append(implications.get(integer));
        }
        System.out.println("Итог минимизации: "+NFtoTDF(res.toString(), isANDSeparator));
    }

}
