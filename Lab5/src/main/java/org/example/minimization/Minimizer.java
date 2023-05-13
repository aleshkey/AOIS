package org.example.minimization;

import org.example.constants.Constants;
import org.example.util.Util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Minimizer {
    private static List<List<Integer>> pairs;

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
            res.add(originalString.substring(1, originalString.length()-1));
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
            for (int j = i; j < functionalParts.size(); j++) {
                var b = checkExcess(functionalParts, j, isANDSeparator);
                if (b.size() != 0) {
                    for (int index = 0; index< b.size(); index++) {
                        if (resultString.toString().indexOf(findSharedPart(functionalParts.get(j), functionalParts.get(b.get(index)), isANDSeparator)) == -1) {
                            if (resultString.toString().equals("")) {
                                resultString.append(findSharedPart(functionalParts.get(j), functionalParts.get(b.get(index)), isANDSeparator));
                            } else
                                resultString.append(reverseOperator).append(findSharedPart(functionalParts.get(b.get(index)), functionalParts.get(j), isANDSeparator));
                        }
                    }
                } else if (!resultString.toString().contains(functionalParts.get(j))) {
                    if (resultString.toString().equals("")) {
                        resultString.append("(").append(functionalParts.get(j)).append(")");
                    } else
                        resultString.append(reverseOperator).append("(").append(functionalParts.get(j)).append(")");
                }
            }
            functionalParts.remove(i);
            i--;
        }
        return resultString.toString();
    }



    private static List<Integer> checkExcess(List<String> functionParts, int index, boolean isANDSeparator){
        List<List<String>> constituentsMatrix = new ArrayList<>();
        var res = new ArrayList<Integer>();
        for (String functionPart : functionParts) {
            constituentsMatrix.add(divideString(functionPart, isANDSeparator));
        }
        List<String> tempPart = constituentsMatrix.get(index);
        for(int i =index; i < constituentsMatrix.size(); i++){
            if (unionString(constituentsMatrix.get(i), tempPart).size() == tempPart.size()-1 && checkReversed(constituentsMatrix.get(i), tempPart)){
                res.add(i);
            }
        }
        return res;
    }

    private static boolean checkReversed(List<String> expression1, List<String> expression2){
        var isMinus = false;
        List<String> allStrs = new ArrayList<>();
        if (expression1.size() == expression2.size()) {
            for (var str : expression2) {
                if (expression1.contains(reverse(str))) {
                    allStrs.add(str);
                }
            }
            if(sameSign(allStrs)){
                return true;
            }
        }
        return false;
    }

    public static boolean sameSign(List<String> list){
        if (list.size() == 0)
            return false;
        var isMinus = list.get(0).charAt(0) == '!';
        for (var elem : list){
            if ((isMinus && elem.charAt(0) !='!') || (!isMinus && elem.charAt(0) == '!')){
                return false;
            }
        }
        return true;
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

    public static String NFtoTDF(String originalString, boolean isANDSeparator){
        List<String> functionParts = divideStringWithBrackets(originalString, isANDSeparator);
        StringBuilder res = new StringBuilder();
        String stringOperator = isANDSeparator ? "&" : "\\|";
        String reversedOperator = !isANDSeparator ? "&" : "|";
        for (int i = 0; i< functionParts.size(); i++){
            String unique = "";
            if (checkExcess(functionParts, i, isANDSeparator).size() != 0){
                List<String> tempPartList = Arrays.asList(functionParts.get(i).split(stringOperator));
                List<String> checkedPartList = Arrays.asList(functionParts.get(checkExcess(functionParts, i, isANDSeparator).get(0)).split(stringOperator));
                String union = fromListToNF(unionString(tempPartList, checkedPartList), !isANDSeparator);
                unique = unique + "("+union+")";
                functionParts.remove(functionParts.get(checkExcess(functionParts, i, isANDSeparator).get(0)));
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

    public static String getShortForm(String SNF, boolean isANDSeparator){
        var constituents = divideString(SNF, !isANDSeparator);
        var implications = Util.makeUnique(divideString(gluing(SNF, isANDSeparator), !isANDSeparator));
        List<List<Integer>> constituentsToImplications = new ArrayList<>();
        for (String implication : implications) {
            List<Integer> thisConstituentImplications = new ArrayList<>();
            for (int constituentsIndex = 0; constituentsIndex < constituents.size(); constituentsIndex++) {
                if (exist(implication.substring(1, implication.length() - 1), constituents.get(constituentsIndex), !isANDSeparator)) {
                    thisConstituentImplications.add(constituentsIndex);
                }
            }
            constituentsToImplications.add(thisConstituentImplications);
        }
        List<List<Integer>> allVariants = generateAllSubsets(IntStream.range(0, implications.size()).boxed().collect(Collectors.toList()));
        List<List<Integer>> bestVariants = new ArrayList<>();
        for (var variant : allVariants){
            Map <Integer, Boolean> switchedConstituents = createHashMap(constituents);
            for (var i : variant) {
                var b = getImplicationsOfConstituents(i, constituentsToImplications);
                switchedConstituents = turnOnConstituent(switchedConstituents, constituentsToImplications.get(i));
            }
            if (isStrongTable(switchedConstituents)){
                bestVariants.add(variant);
            }
        }
        var res = chooseBestVariantForTable(bestVariants, implications, isANDSeparator);
        return NFtoTDF(res, isANDSeparator);
    }

    public static List<List <Integer>> generateAllSubsets(List<Integer> list) {
        pairs= new ArrayList<>();
        generateAllSubsets(list, new ArrayList<>(), 0);
        pairs.removeAll(Collections.singleton(new ArrayList<>()));
        return pairs;
    }

    private static void generateAllSubsets(List<Integer> list, List<Integer> current, int index) {
        if (index == list.size()) {
            pairs.add(current);
            return;
        }
        generateAllSubsets(list, current, index + 1);
        List<Integer> updated = new ArrayList<>(current);
        updated.add(list.get(index));
        generateAllSubsets(list, updated, index + 1);
    }

    private static String chooseBestVariantForTable(List<List<Integer>> variants,List<String> implications, boolean isANDSeparator) {
        int min = Constants.MAX_SIZE;
        String stringOperator = isANDSeparator ? "&" : "\\|";
        String reverseOperator = !isANDSeparator ? "&" : "\\|";
        String res = "";
        var allMinSize = new ArrayList<String>();
        for (var variant : variants){
            var b = fromListToFormula(variant, implications, !isANDSeparator);
            var sep = NFtoTDF(b, isANDSeparator);
            var x = sep.split(reverseOperator).length;
            if (sep.split(reverseOperator).length <= min) {
                if (min == sep.split(reverseOperator).length)
                    allMinSize.add(sep);
                else {
                    min = sep.split(reverseOperator).length;
                    allMinSize.clear();
                    allMinSize.add(sep);
                }
            }
        }
        res = minimum(allMinSize);
        return res;
    }

    private static String minimum(ArrayList<String> allMinSize) {
        var min = Constants.MAX_SIZE;
        var res = "";
        for(var elem : allMinSize){
            if(elem.length() < min){
                res = elem;
            }
        }
        return res;
    }

    private static String fromListToFormula(List<Integer> list, List<String> implications, boolean isANDSeparator){
        String stringOperator = isANDSeparator ? "&" : "|";
        StringBuilder res = new StringBuilder();
        for (int i =0; i< implications.size();i++){
            if (list.contains(i)){
                if (res.toString().equals("")){
                    res.append(implications.get(i));
                }
                else {
                    res.append(stringOperator).append(implications.get(i));
                }
            }
        }
        return res.toString();
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

}

