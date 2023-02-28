import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Main {

    private static boolean hasAddition = false;

    private static boolean hasDebt = false;

    private static String partOfCode = "0";

    private static final int EPS = 5;

    private static final int NUMBER_OF_BYTES = 16;

    public static String intoBinaryCode(int number) {
        StringBuilder result = new StringBuilder();
        boolean greaterThenZero = number >= 0;

        while (abs(number) >= 1) {
            result.insert(0, (abs(number) % 2));
            number /= 2;
        }

        if (!greaterThenZero) {
            StringBuilder buffer = new StringBuilder("1");
            result = new StringBuilder(leadTo16DigitView(result.toString()));
            for (int i = 1; i < result.length(); i++) {
                buffer.append(result.charAt(i));
            }
            result = new StringBuilder(buffer.toString());
        }

        return leadTo16DigitView(result.toString());
    }

    public static String leadTo16DigitView(String binaryView) {
        StringBuilder binaryViewBuilder = new StringBuilder(binaryView);
        while (binaryViewBuilder.length() < NUMBER_OF_BYTES) {
            binaryViewBuilder.insert(0, 0);
        }
        binaryView = binaryViewBuilder.toString();
        return binaryView;
    }

    public static String becomeReversed(String binaryCode) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < leadTo16DigitView(binaryCode).length(); i++) {
            if (leadTo16DigitView(binaryCode).charAt(i) == '0') {
                result.append("1");
            } else result.append("0");
        }
        return result.toString();
    }

    public static String intoReverseBinaryCode(int number) {
        String result = "";
        if (number < 0) {
            result = becomeReversed(intoBinaryCode(abs(number)));
        } else result = leadTo16DigitView(intoBinaryCode(number));

        return result;
    }

    public static int fromBinaryToInt(String binaryCode) {
        int result = 0;
        for (int i = 1; i < binaryCode.length(); i++) {
            result += pow(2, binaryCode.length() - 1 - i) * parseInt(String.valueOf(binaryCode.charAt(i)));
        }

        if (binaryCode.charAt(0) == '0') {
            return result;
        }
        return -1 * result;
    }

    public static String[] makeTheSameSizeToTop(String numberInBinaryCode1, String numberInBinaryCode2) {
        String[] result = new String[2];

        if (numberInBinaryCode1.length() > numberInBinaryCode2.length()) {
            result[0] = numberInBinaryCode1;
            result[1] = addZeroToTop(numberInBinaryCode2, numberInBinaryCode1.length() - numberInBinaryCode2.length());
        } else {
            result[1] = numberInBinaryCode2;
            result[0] = addZeroToTop(numberInBinaryCode1, numberInBinaryCode2.length() - numberInBinaryCode1.length());
        }

        return result;
    }

    public static String[] makeTheSameSizeToEnd(String numberInBinaryCode1, String numberInBinaryCode2) {
        String[] result = new String[2];

        if (numberInBinaryCode1.length() > numberInBinaryCode2.length()) {
            result[0] = numberInBinaryCode1;
            result[1] = addZeroToEnd(numberInBinaryCode2, numberInBinaryCode1.length() - numberInBinaryCode2.length());
        } else {
            result[1] = numberInBinaryCode2;
            result[0] = addZeroToEnd(numberInBinaryCode1, numberInBinaryCode2.length() - numberInBinaryCode1.length());
        }

        return result;
    }

    public static String addZeroToTop(String number, int zerosToAdd) {
        StringBuilder numberBuilder = new StringBuilder(number);
        for (var i = 0; i < zerosToAdd; i++) {
            numberBuilder.insert(0, 0);
        }
        number = numberBuilder.toString();

        return number;
    }

    public static String addZeroToEnd(String number, int zerosToAdd) {
        number = number + "0".repeat(Math.max(0, zerosToAdd));

        return number;
    }

    public static char addingDigits(char digit1, char digit2) {
        char res;
        int sumOfDigits = parseInt(String.valueOf(digit1)) + parseInt(String.valueOf(digit2));

        if (hasAddition) {
            sumOfDigits++;
        }

        if (sumOfDigits < 2) {
            res = (char) (sumOfDigits + '0');
            hasAddition = false;
        } else {
            hasAddition = true;
            res = (char) (sumOfDigits - 2 + '0');
        }

        return res;
    }


    private static char minusDigits(char digit1, char digit2) {
        char res;
        int minusOfDigits = parseInt(String.valueOf(digit1)) - parseInt(String.valueOf(digit2));

        if (hasDebt) {
            minusOfDigits--;
        }

        if (minusOfDigits >= 0) {
            res = (char) (minusOfDigits + '0');
            hasDebt = false;
        } else {
            hasDebt = true;
            res = (char) (minusOfDigits + 2 + '0');
        }

        return res;
    }


    public static String minus(String binaryCode1, String binaryCode2) {
        StringBuilder res = new StringBuilder();
        binaryCode1 = makeTheSameSizeToTop(binaryCode1, binaryCode2)[0];
        binaryCode2 = makeTheSameSizeToTop(binaryCode1, binaryCode2)[1];
        for (int i = binaryCode1.length() - 1; i >= 0; i--) {
            res.insert(0, minusDigits(binaryCode1.charAt(i), binaryCode2.charAt(i)));
        }

        hasDebt = false;
        return res.toString();
    }

    public static String sumInDirectCode(String numberInBinaryCode1, String numberInBinaryCode2) {
        StringBuilder result = new StringBuilder();
        numberInBinaryCode1 = leadTo16DigitView(numberInBinaryCode1);
        numberInBinaryCode2 = leadTo16DigitView(numberInBinaryCode2);
        for (int i = numberInBinaryCode1.length() - 1; i >= 0; i--) {
            result.insert(0, addingDigits(numberInBinaryCode1.charAt(i), numberInBinaryCode2.charAt(i)));
        }

        hasAddition = false;

        return result.toString();
    }

    public static String getAdditionalCode(int number) {
        String binaryCodeOfNumber = "";

        if (number >= 0) {
            binaryCodeOfNumber = intoBinaryCode(number);
        } else binaryCodeOfNumber = sumInDirectCode(intoReverseBinaryCode(number), "00000001");

        return binaryCodeOfNumber;
    }

    public static String sumInAdditionalCode(int number1, int number2) {
        String additionalBinaryCodeOfNumber1 = getAdditionalCode(number1);
        String additionalBinaryCodeOfNumber2 = getAdditionalCode(number2);
        return sumInDirectCode(additionalBinaryCodeOfNumber1, additionalBinaryCodeOfNumber2);
    }

    public static int fromAdditionalBinaryCodeToInt(String additionalBinaryCode) {
        if (additionalBinaryCode.charAt(0) == '1') {
            additionalBinaryCode = 1 + deleteFirstSymbol(sumInDirectCode(becomeReversed(additionalBinaryCode), "1"));

        }
        return fromBinaryToInt(additionalBinaryCode);
    }

    public static String sumOfTheListOfTerm(List<String> terms) {
        String res = "";

        for (var t : terms) {
            res = sumInDirectCode(res, t);
        }

        return res;
    }

    public static boolean isTheSameSignInRightCode(String binaryCode1, String binaryCode2) {
        return binaryCode1.charAt(0) == binaryCode2.charAt(0);
    }

    public static String deleteFirstSymbol(String str) {
        StringBuilder res = new StringBuilder();
        for (int i = 1; i < str.length(); i++) {
            res.append(str.charAt(i));
        }
        return res.toString();
    }

    public static String multiplication(String binaryCode1, String binaryCode2) {
        String res = "";
        List<String> terms = new ArrayList<>();

        if (isTheSameSignInRightCode(binaryCode1, binaryCode2)) {
            res = "0";
        } else {
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


    public static String divideIntegerPart(String binaryCode1, String binaryCode2) {                     //нужно уменьшить кол-во строк
        StringBuilder res = new StringBuilder();
        int position = 0;

        while (position < binaryCode1.length()) {
            if (parseInt(partOfCode) < parseInt(binaryCode2)) {
                partOfCode = partOfCode + binaryCode1.charAt(position);
                position++;
                res.append('0');
            } else {
                String buffer = binaryCode1;
                binaryCode1 = String.valueOf(parseInt(minus(partOfCode, binaryCode2)));
                partOfCode = binaryCode1 + buffer.charAt(position);
                res.append('1');
                StringBuilder binaryCode1Builder = new StringBuilder(binaryCode1);
                for (int i = position; i < buffer.length(); i++) {
                    binaryCode1Builder.append(buffer.charAt(i));
                }
                binaryCode1 = binaryCode1Builder.toString();
                position = partOfCode.length();
            }
        }

        hasDebt = false;

        if (parseInt(partOfCode) >= parseInt(binaryCode2)) {
            res.append(1);
            partOfCode = String.valueOf(parseInt(minus(partOfCode, binaryCode2))) + 0;
        } else {
            res.append(0);
            partOfCode = partOfCode + 0;
        }
        return res.toString();
    }

    public static String divideFractionalPart(String binaryCode2) {
        StringBuilder res = new StringBuilder();
        partOfCode = String.valueOf(parseInt(partOfCode));
        binaryCode2 = String.valueOf(parseInt(binaryCode2));
        for (int i = 0; i < EPS; i++) {
            if (parseInt(partOfCode) >= parseInt(binaryCode2)) {
                res.append(1);
                partOfCode = String.valueOf(parseInt(minus(partOfCode, binaryCode2))) + 0;
            } else {
                res.append(0);
                partOfCode = partOfCode + 0;
            }
        }
        return res.toString();
    }


    public static String dividing(String binaryCode1, String binaryCode2) {
        int buffer = parseInt(divideIntegerPart(binaryCode1, binaryCode2));
        var buf = divideFractionalPart(binaryCode2);
        partOfCode = "0";
        return buffer + "." + buf;
    }

    public static String floatIntoBinary(float number) {
        String integer = String.valueOf(number).split("\\.")[0];
        float fraction = number - parseInt(integer);
        integer = String.valueOf(parseInt(intoBinaryCode(parseInt(String.valueOf(number).split("\\.")[0]))));
        StringBuilder result = new StringBuilder(integer + ".");
        int counter = -1;

        while (fraction > 0) {
            if (fraction - pow(2, counter) >= 0) {
                result.append(1);
                fraction -= pow(2, counter);
            } else {
                result.append(0);
            }
            counter--;
        }

        return result.toString();

    }

    public static String floatSum(float number1, float number2) {
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
        res = res.substring(0, res.length() - pointPosition) + "." + res.substring(res.length() - pointPosition);
        return res;
    }

    public static void printMenu() {
        System.out.println("1 - sum\n" +
                "2 - multiplication\n" +
                "3 - dividing\n" +
                "4 - sum of float\n");
        Scanner sc = new Scanner(System.in);
        int kod = sc.nextInt();
        System.out.println("enter two integer numbers");
        float num1 = sc.nextFloat(), num2 = sc.nextFloat();
        chooseOperation(kod, num1, num2);
    }

    private static void chooseOperation(int kod, float num1, float num2) {
        switch (kod) {
            case 1: {
                sum((int) num1, (int) num2);
                break;
            }
            case 2: {
                System.out.println(intoBinaryCode((int) num1) + "\n*\n" + intoBinaryCode((int) num2) + "\n-----------------------------------");
                System.out.println(multiplication(intoBinaryCode((int) num1), intoBinaryCode((int) num2)));
                break;
            }
            case 3: {
                System.out.println(intoBinaryCode((int) num1) + "\n/\n" + intoBinaryCode((int) num2) + "\n-----------------------------------");
                System.out.println(dividing(intoBinaryCode((int) num1), intoBinaryCode((int) num2)));
                break;
            }
            case 4: {
                String res = floatSum(num1, num2);
                System.out.println(res);
                System.out.println("in the decimal system: ");
                System.out.println(binaryToFloat(res));
                break;
            }
        }
    }

    private static float binaryFloatToFloat(String floatBinaryCode) {
        float res = 0;
        for (int i = 0; i < floatBinaryCode.length(); i++) {
            res = res + parseInt(String.valueOf(floatBinaryCode.charAt(i))) * (float) pow(2, -(i + 1));
        }
        return res;
    }

    private static float binaryToFloat(String binaryCode) {
        String integerPart = binaryCode.split("\\.")[0];
        String floatPart = binaryCode.split("\\.")[1];
        return (float) fromBinaryToInt(leadTo16DigitView(integerPart)) + binaryFloatToFloat(floatPart);
    }

    private static void sum(int num1, int num2) {
        System.out.println(getAdditionalCode(num1) + "\n+\n" + getAdditionalCode(num2) + "\n-----------------------------------");
        String res = sumInAdditionalCode(num1, num2);
        System.out.println(res);
        System.out.println("in the decimal system: ");
        System.out.println(fromAdditionalBinaryCodeToInt(res));
    }


    public static void main(String[] args) {
        while (true) {
            printMenu();
        }
    }
}