package org.example.array;

import lombok.Data;
import org.example.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class AssociativeArray {
    private int SIZE = 16;
    private List<List<Integer>> array = new ArrayList<>();
    private List<List<Integer>> diagonal = new ArrayList<>();

    public AssociativeArray() {
        for (int i = 0; i<SIZE; i++){
            List<Integer> num = new ArrayList<>();
            for(int j = 0; j<SIZE; j++){
                num.add((int)((Math.random()*10)%2));
            }
            array.add(num);
            diagonal.add(new ArrayList<>(num));
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(var elem : array){
            res.append(elem).append("\n");
        }
        return res.toString();
    }

    public void generateDiagonal(){
        for(int i =0; i < array.size(); i++){
            generateDiagonalColumn(array.get(i), i);
        }
    }

    private void generateDiagonalColumn(List<Integer> num, int row){
        for(int i = row; i<SIZE; i++){
            diagonal.get(i).set(row, num.get(i-row));
        }
        for(int i =0; i< row; i++){
            diagonal.get(i).set(row, num.get(SIZE + i - row));
        }
    }

    public String getAddress(int address){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < diagonal.get(address).size(); i++){
            res.append(diagonal.get(address).get(i));
        }
        return res.toString();
    }

    public List<Integer> slicing(List<Integer> word, int x, int y){
        return word.subList(x, y+1);
    }

    public List<Integer> assigment(List<Integer> num1, List<Integer> num2){
        Collections.reverse(num1);
        Collections.reverse(num2);
        for (int i = 0; i < num2.size(); i++){
            num1.set(i, num2.get(i));
        }
        Collections.reverse(num1);
        return num1;
    }

    public void operations(List<Integer> list){
        for(int i = 0; i <diagonal.size(); i++){
            var slicingNum = slicing(diagonal.get(i), 0, 2);
            if (slicingNum.equals(list)){
                List<Integer> Aj = slicing(diagonal.get(i),3, 6);
                List<Integer> Bj = slicing(diagonal.get(i), 7,10);
                slicingNum = Util.sum(Aj, Bj);
                System.out.println("Vj = V in word " + i);
                diagonal.set(i, assigment(diagonal.get(i), slicingNum));
                System.out.println(i+"th word in matrix: "+getAddress(i));
            }
        }
    }

    public void functions(int address1, int address2, int address3, int number){
        List<Boolean> res = new ArrayList<>();
        var num1 = Util.intToBoolean(diagonal.get(address1));
        var num2 = Util.intToBoolean(diagonal.get(address2));
        switch (number) {
            case 2 -> {
                for (int i = 0; i < num1.size(); i++) {
                    res.add(Boolean.logicalAnd(num1.get(i), !num2.get(i)));
                }
            }
            case 7 -> {
                for (int i = 0; i < num1.size(); i++) {
                    res.add(Boolean.logicalOr(num1.get(i), num2.get(i)));
                }
            }
            case 8 -> {
                for (int i = 0; i < num1.size(); i++) {
                    res.add(!Boolean.logicalOr(num1.get(i), num2.get(i)));
                }
            }
            case 13 -> {
                for (int i = 0; i < num1.size(); i++) {
                    res.add(Boolean.logicalOr(!num1.get(i), num2.get(i)));
                }
            }
        }
        diagonal.set(address3, Util.booleanToInt(res));
        System.out.println("Function "+number);
        System.out.println(address1+"th word in matrix: "+getAddress(address1));
        System.out.println(address2+"th word in matrix: "+getAddress(address2));
        System.out.println("Result: " + Util.toBinary(res));
    }

    public void border(int address1, int address2){
        List<Integer> binary1 = diagonal.get(address1);
        List<Integer> binary2 = diagonal.get(address2);
        List<List<Integer>> interval = new ArrayList<List<Integer>>();
        List<List<Integer>> greater = new ArrayList<>();
        List<List<Integer>> less = new ArrayList<>();

        if (!Util.comparison(binary1, binary2)){
            greater = find(binary1, false);
            less = find(binary2, true);
        }
        else {
            greater = find(binary1, true);
            less = find(binary2, false);
        }

        greater = Util.sort(greater);
        less = Util.sort(less);
        interval = Util.intersection(greater, less);
        Util.print(interval);
    }

    private List<List<Integer>> find(List<Integer> binary, boolean isGreater){
        List<List<Integer>> searchedValues = new ArrayList<>();
        for (List<Integer> integers : array) {
            if ((Util.comparison(binary, integers) && isGreater) || (!Util.comparison(binary, integers) && !isGreater)) {
                searchedValues.add(integers);
            }
        }
        return searchedValues;
    }
}
