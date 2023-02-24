import java.util.ArrayList;
import java.util.List;

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

    public static String[] makeTheSameSize(String numberInBinaryCode1, String numberInBinaryCode2){
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

    public static String addZeroToTop(String number, int zerosToAdd){
        for(var i = 0; i < zerosToAdd; i++){
            number = 0 + number;
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
        binaryCode1 = leadTo16DigitView(binaryCode1);
        binaryCode2 = leadTo16DigitView(binaryCode2);

        for (int i = binaryCode1.length()-1; i>=0; i--){
            res = minusDigits(binaryCode1.charAt(i), binaryCode2.charAt(i)) + res;
        }


        return res;
    }

    public static String sumInDirectCode(String numberInBinaryCode1, String numberInBinaryCode2){
        String result = new String();
        String[] numbers = makeTheSameSize(numberInBinaryCode1, numberInBinaryCode2);

        for (var i = numbers[0].length()-1; i>=0; i--){
            result = addingDigits(numbers[0].charAt(i), numbers[1].charAt(i)) + result;
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
        return parseInt(divideIntegerPart(binaryCode1, binaryCode2))+"." + divideFractionalPart(binaryCode2);
    }




    public static void main(String[] args) {
       /* String str = sumInDirectCode(intoReverseBinaryCode(2), intoReverseBinaryCode(-3));
        System.out.println(str);
        System.out.println(fromReverseBinaryToInt(str));

        String str = sumInAdditionalCode(-2, 3);
        System.out.println(sumInAdditionalCode(-2, 3));
        System.out.println(fromAdditionalBinaryCodeToInt(sumInAdditionalCode(-2, 3)));
    */
        System.out.println(parseInt(minus("1110", "110")));
        System.out.println(dividing("1111", "110"));
        /*System.out.println(intoBinaryCode(-10));
        System.out.println(intoBinaryCode(22));
        System.out.println(multiplication(intoBinaryCode(-10), intoBinaryCode(22)));
        //System.out.println(String.valueOf(15/4*pow(2,4)));*/
    }
}