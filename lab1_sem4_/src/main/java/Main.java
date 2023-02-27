import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.Math.*;

public class Main {

    private static boolean hasAddition = false;

    private static boolean hasDebt = false;

    private static String partOfCode = "0";

    public static String intoBinaryCode(int number){
        String result = new String();
        boolean greaterThenZero = number >= 0;

        while(abs(number) >= 1){
            result = (abs(number) % 2) + result;
            number /= 2;
        }

        if(!greaterThenZero){
            String buffer="1";
            result = leadTo16DigitView(result);
            for (int i = 1; i < result.length(); i++){
                buffer = buffer + result.charAt(i);
            }
            result = buffer;
        }

        return leadTo16DigitView(result);
    }

    public static String leadTo16DigitView(String binaryView){
        while (binaryView.length() < 16){
            binaryView = 0 + binaryView;
        }
        return binaryView;
    }

    public static String becomeReversed(String binaryCode){
        String result = "";
        for (int i = 0; i < leadTo16DigitView(binaryCode).length(); i++) {
            if  (leadTo16DigitView(binaryCode).charAt(i) == '0') {
                result = result + "1";
            } else result = result + "0";
        }
        return result;
    }

    public static String intoReverseBinaryCode(int number){
        String result ="";
        if (number < 0) {
           result = becomeReversed(intoBinaryCode(abs(number)));
        }
        else result = leadTo16DigitView(intoBinaryCode(number));

        return result;
    }

    public static int fromBinaryToInt(String binaryCode){
        int result=0;
        for(int i=1; i<binaryCode.length(); i++){
            result+=pow(2, binaryCode.length() - 1 - i) * parseInt(String.valueOf(binaryCode.charAt(i)));
        }

        if(binaryCode.charAt(0)=='0') {
            return result;
        }
        return -1*result;
    }

    public static int fromReverseBinaryToInt(String reverseBinaryCode) {
        int result = 0;
        boolean greaterThenZero = reverseBinaryCode.charAt(0) == '0';
        String changedReverseBinaryCode = "1";
        for (int i = 1; i < reverseBinaryCode.length(); i++) {
            changedReverseBinaryCode += reverseBinaryCode.charAt(i);
        }
        if (greaterThenZero) {
            return fromBinaryToInt(becomeReversed(changedReverseBinaryCode));
        }
        return  -1 *fromBinaryToInt(becomeReversed(changedReverseBinaryCode));
    }

    public static String[] makeTheSameSizeToTop(String numberInBinaryCode1, String numberInBinaryCode2){
        String[] result= new String[2];

        if (numberInBinaryCode1.length() > numberInBinaryCode2.length()){
            result[0] = numberInBinaryCode1;
            result[1] = addZeroToTop(numberInBinaryCode2, numberInBinaryCode1.length()-numberInBinaryCode2.length());
        }
        else {
            result[1] = numberInBinaryCode2;
            result[0] = addZeroToTop(numberInBinaryCode1, numberInBinaryCode2.length()-numberInBinaryCode1.length());
        }

        return result;
    }

    public static String[] makeTheSameSizeToEnd(String numberInBinaryCode1, String numberInBinaryCode2){
        String[] result= new String[2];

        if (numberInBinaryCode1.length() > numberInBinaryCode2.length()){
            result[0] = numberInBinaryCode1;
            result[1] = addZeroToEnd(numberInBinaryCode2, numberInBinaryCode1.length()-numberInBinaryCode2.length());
        }
        else {
            result[1] = numberInBinaryCode2;
            result[0] = addZeroToEnd(numberInBinaryCode1, numberInBinaryCode2.length()-numberInBinaryCode1.length());
        }

        return result;
    }

    public static String addZeroToTop(String number, int zerosToAdd){
        for(var i = 0; i < zerosToAdd; i++){
            number = 0 + number;
        }

        return number;
    }

    public static String addZeroToEnd(String number, int zerosToAdd){
        for(var i = 0; i < zerosToAdd; i++){
            number = number + 0;
        }

        return number;
    }

    public static char  addingDigits(char digit1, char digit2){
        char res='\0';
        int sumOfDigits = parseInt(String.valueOf(digit1)) + parseInt(String.valueOf(digit2));

        if (hasAddition){
            sumOfDigits++;
        }

        if (sumOfDigits<2){
            res = (char) (sumOfDigits + '0');
            hasAddition = false;
        }
        else {
            hasAddition=true;
            res = (char) (sumOfDigits - 2 + '0');
        }

        return res;
    }


    private static char minusDigits (char digit1, char digit2){
        char res = '\0';
        int minusOfDigits = parseInt(String.valueOf(digit1)) - parseInt(String.valueOf(digit2));

        if (hasDebt){
            minusOfDigits--;
        }

        if (minusOfDigits >= 0){
            res = (char) (minusOfDigits + '0');
            hasDebt = false;
        }
        else {
            hasDebt = true;
            res = (char) (minusOfDigits + 2 + '0');
        }

        return res;
    }


    public static String minus(String binaryCode1, String binaryCode2){
        String res = "";
        boolean hasDebt = false;
        for (int i = binaryCode1.length()-1; i>=0; i--){
            binaryCode1 = makeTheSameSizeToTop(binaryCode1, binaryCode2)[0];
            binaryCode2 = makeTheSameSizeToTop(binaryCode1, binaryCode2)[1];
            res = minusDigits(binaryCode1.charAt(i), binaryCode2.charAt(i)) + res;
        }

        hasDebt = false;
        return res;
    }

    public static String sumInDirectCode(String numberInBinaryCode1, String numberInBinaryCode2){
        String result = new String();
        numberInBinaryCode1 = leadTo16DigitView(numberInBinaryCode1);
        numberInBinaryCode2 = leadTo16DigitView(numberInBinaryCode2);
        for (var i = numberInBinaryCode1.length()-1; i>=0; i--){
            result = addingDigits(numberInBinaryCode1.charAt(i), numberInBinaryCode2.charAt(i)) + result;
        }

        hasAddition = false;

        return result;
    }

    public static String sumInReverseCode (String numberInBinaryCode1, String numberInBinaryCode2){
        return sumInDirectCode(intoReverseBinaryCode(fromBinaryToInt(numberInBinaryCode1)), intoReverseBinaryCode(fromBinaryToInt(numberInBinaryCode2)));
    }

    public static String sumInReverseCode (int number1, int number2){
        return sumInDirectCode(intoReverseBinaryCode(number1), intoReverseBinaryCode(number2));
    }

    public static String getAdditionalCode(int number){
        String binaryCodeOfNumber="";

        if (number >= 0){
            binaryCodeOfNumber = intoBinaryCode(number);
        }
        else binaryCodeOfNumber = sumInDirectCode(intoReverseBinaryCode(number), "00000001");

        return binaryCodeOfNumber;
    }

    public static String sumInAdditionalCode(int number1, int number2){
        String additionalBinaryCodeOfNumber1 = getAdditionalCode(number1);
        String additionalBinaryCodeOfNumber2 = getAdditionalCode(number2);
        String res = sumInDirectCode(additionalBinaryCodeOfNumber1, additionalBinaryCodeOfNumber2);
        return res;
    }

    public static int fromAdditionalBinaryCodeToInt(String additionalBinaryCode){
        if(additionalBinaryCode.charAt(0) == '1'){
            additionalBinaryCode = 1 + deleteFirstSymbol(sumInDirectCode(becomeReversed(additionalBinaryCode),"1"));

        }
        return fromBinaryToInt(additionalBinaryCode);
    }

    public static String sumOfTheListOfTerm(List<String> terms){
        String res="";

        for(var t : terms){
            res = sumInDirectCode(res, t);
        }

        return res;
    }

    public static String multiplication (int number1, int number2){
        String binaryCode1 = intoBinaryCode(number1);
        String binaryCode2 = intoBinaryCode(number2);
        String res = "";
        List<String> terms =new ArrayList<>();
        for(int i = 0; i < binaryCode2.length(); i++){
            terms.add(String.valueOf((int) (parseInt(binaryCode1)*parseInt(String.valueOf(binaryCode2.charAt(i)))*pow(10, i))));
        }
        res = sumOfTheListOfTerm(terms);
        return res;
    }

    public static boolean isTheSameSignInRightCode(String binaryCode1, String binaryCode2){
        if (binaryCode1.charAt(0) == binaryCode2.charAt(0)){
            return true;
        }
        return false;
    }

    public static String deleteFirstSymbol(String str){
        String res ="";
        for (int i =1; i< str.length(); i++){
            res = res + str.charAt(i);
        }
        return res;
    }

    public static String multiplication (String binaryCode1, String binaryCode2) {
        String res = "";
        List<String> terms = new ArrayList<>();

        if (isTheSameSignInRightCode(binaryCode1, binaryCode2)){
            res = "0";
        }
        else{
            res = "1";
        }

        binaryCode1 = deleteFirstSymbol(binaryCode1);
        binaryCode2 = deleteFirstSymbol(binaryCode2);

        for (int i = 0; i < binaryCode2.length(); i++) {
            terms.add(leadTo16DigitView(String.valueOf((int) (parseInt(binaryCode1) * parseInt(String.valueOf(binaryCode2.charAt(binaryCode2.length() - 1 - i))) * pow(10, i)))));
        }

        res = res + deleteFirstSymbol(sumOfTheListOfTerm(terms));
        return res;
    }

    public static boolean canDivide(String dividend, String divider){
        if (parseInt(dividend) / parseInt(divider) >= 1) {
            return true;
        }
        return false;
    }

    public static String divideIntegerPart(String binaryCode1, String binaryCode2){                     //нужно уменьшить кол-во строк
        String res = "";
        int position = 0;

        while (position < binaryCode1.length() ) {
            if (parseInt(partOfCode) < parseInt(binaryCode2)) {
                partOfCode = partOfCode + binaryCode1.charAt(position);
                position++;
                res = res + '0';
            } else {
                String buffer = binaryCode1;
                binaryCode1 = String.valueOf(parseInt(minus(partOfCode, binaryCode2)));
                partOfCode = binaryCode1 + buffer.charAt(position);
                res = res + '1';
                for (int i = position; i < buffer.length(); i++) {
                    binaryCode1 = binaryCode1 + buffer.charAt(i);
                }
                position = partOfCode.length();
            }
        }

        if (parseInt(partOfCode) >= parseInt(binaryCode2)){
            res = res + 1;
            partOfCode = String.valueOf(parseInt(minus(partOfCode, binaryCode2)))+0;
        }
        else {
            res = res + 0;
            partOfCode = partOfCode + 0;
        }
        return res;
    }

    public static String divideFractionalPart(String binaryCode2){
        String res = "";
        partOfCode = String.valueOf(parseInt(partOfCode));
        binaryCode2 = String.valueOf(parseInt(binaryCode2));
        for(int i = 0; i < 5; i++){
            if (parseInt(partOfCode) >= parseInt(binaryCode2)){
                res = res + 1;
                partOfCode = String.valueOf(parseInt(minus(partOfCode, binaryCode2)))+0;
            }
            else {
                res = res + 0;
                partOfCode = partOfCode + 0;
            }
        }
        return res;
    }



    public static String dividing(String binaryCode1, String binaryCode2){
        int buffer = parseInt(divideIntegerPart(binaryCode1, binaryCode2));
        var buf = divideFractionalPart(binaryCode2);
        partOfCode="0";
        return String.valueOf(buffer+"." + buf);
    }

    public static String changeSize(int size, String binaryCode){
        for (int i = binaryCode.length(); i<size; i++){
            binaryCode = 0 + binaryCode;
        }
        return binaryCode;
    }

    public static String floatIntoBinary(float number){
        String integer = String.valueOf(number).split("\\.")[0];
        float fraction = number - parseInt(integer);
        integer = String.valueOf(parseInt(intoBinaryCode(parseInt(String.valueOf(number).split("\\.")[0]))));
        String result = integer+".";
        int counter = -1;

        while(fraction > 0){
            if (fraction - pow(2, counter) >= 0){
                result = result + 1;
                fraction -= pow(2, counter);
            }
            else {
                result = result + 0;
            }
            counter--;
        }

        return result;

    }

    public static String floatSum(float number1, float number2){
        String integerOfNumber1 = floatIntoBinary(number1).split("\\.")[0];
        String floatOfNumber1 = floatIntoBinary(number1).split("\\.")[1];

        String integerOfNumber2 = floatIntoBinary(number2).split("\\.")[0];
        String floatOfNumber2 = floatIntoBinary(number2).split("\\.")[1];

        integerOfNumber1 = makeTheSameSizeToTop(integerOfNumber1, integerOfNumber2)[0];
        integerOfNumber2 = makeTheSameSizeToTop(integerOfNumber1, integerOfNumber2)[1];

        floatOfNumber1 = makeTheSameSizeToEnd(floatOfNumber1, floatOfNumber2)[0];
        floatOfNumber2 = makeTheSameSizeToEnd(floatOfNumber1, floatOfNumber2)[1];

        int pointPosition = floatOfNumber1.length();
        String binaryCode1 = integerOfNumber1 + floatOfNumber1;
        String binaryCode2 = integerOfNumber2 + floatOfNumber2;

        String res = sumInDirectCode(binaryCode1, binaryCode2);
        res = res.substring(0, res.length()-pointPosition) + "." + res.substring(res.length()-pointPosition, res.length());
        return res;
    }

    public static void printMenu(){
        System.out.println("1 - sum\n" +
                           "2 - multiplication\n" +
                           "3 - dividing\n" +
                           "4 - sum of float");
        Scanner sc = new Scanner(System.in);
        int kod = sc.nextInt();
        System.out.println("enter two integer numbers");
        float num1 = sc.nextFloat(), num2 = sc.nextFloat();
        chooseOperation(kod, num1, num2);
    }

    private static void chooseOperation(int kod, float num1, float num2) {
        switch (kod){
            case 1:{
                sum((int)num1, (int)num2);
                break;
            }
            case 2:{
                System.out.println(intoBinaryCode((int)num1)+"\n*\n"+intoBinaryCode((int)num2)+"\n-----------------------------------");
                System.out.println(multiplication(intoBinaryCode((int)num1), intoBinaryCode((int)num2)));
                break;
            }
            case 3:{
                System.out.println(intoBinaryCode((int)num1)+"\n/\n"+intoBinaryCode((int)num2)+"\n-----------------------------------");
                System.out.println(dividing(intoBinaryCode((int)num1), intoBinaryCode((int)num2)));
                break;
            }
            case 4: {
                String res =floatSum((float) num1, (float) num2);
                System.out.println(res);
                System.out.println("in the decimal system: ");
                System.out.println(binaryToFloat(res));
                break;
            }
        }
    }

    private static float binaryFloatToFloat(String floatBinaryCode){
        float res = 0;
        for (int i = 0; i < floatBinaryCode.length(); i++){
            res = res + parseInt(String.valueOf(floatBinaryCode.charAt(i)))*(float)pow(2, -(i+1));
        }
        return res;
    }

    private static float binaryToFloat(String binaryCode) {
        String integerPart = binaryCode.split("\\.")[0];
        String floatPart = binaryCode.split("\\.")[1];
        float res = (float)fromBinaryToInt(leadTo16DigitView(integerPart)) + binaryFloatToFloat(floatPart);
        return res;
    }

    private static void sum(int num1, int num2) {
        System.out.println(getAdditionalCode((int)num1)+"\n+\n"+getAdditionalCode((int)num2)+"\n-----------------------------------");
        String res = sumInAdditionalCode((int)num1, (int)num2);
        System.out.println(res);
        System.out.println("in the decimal system: ");
        System.out.println(fromAdditionalBinaryCodeToInt(res));
    }


    public static void main(String[] args) {
        floatSum(1.5f, 1.125f);
        while (true) {
            printMenu();
        }
    }
}