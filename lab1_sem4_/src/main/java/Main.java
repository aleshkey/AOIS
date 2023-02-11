import static java.lang.Integer.parseInt;
import static java.lang.Math.*;

public class Main {

    private static boolean hasAddition = false;

    public static String intoBinaryCode(int number){
        String result = new String();
        boolean greaterThenZero = number >= 0;

        while(abs(number) >= 1){
            result = (abs(number) % 2) + result;
            number /= 2;
        }

        if(greaterThenZero){
            result = 0 + result;
        }
        else result = 1 + result;

        return result;
    }

    public static String leadTo8DigitView(String binaryView){
        while (binaryView.length()<8){
            binaryView = 0 + binaryView;
        }
        return binaryView;
    }

    public static String becomeReversed(String binaryCode){
        String result = "";
        for (int i = 0; i < leadTo8DigitView(binaryCode).length(); i++) {
            if (leadTo8DigitView(binaryCode).charAt(i) == '0') {
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
        else result = leadTo8DigitView(intoBinaryCode(number));

        return result;
    }

    public static int fromBinaryToInt(String binaryCode){
        int result=0;

        for(int i=0; i<binaryCode.length(); i++){
            result+=pow(2, binaryCode.length() - 1 - i) * parseInt(String.valueOf(binaryCode.charAt(i)));
        }

        return  result;
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

    public static char addingDigits(char digit1, char digit2){
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

    public static void main(String[] args) {
       /* String str = sumInDirectCode(intoReverseBinaryCode(2), intoReverseBinaryCode(-3));
        System.out.println(str);
        System.out.println(fromReverseBinaryToInt(str));
    */
        String str = sumInAdditionalCode(-2, 3);
        System.out.println(sumInAdditionalCode(-2, 3));
        System.out.println(fromAdditionalBinaryCodeToInt(sumInAdditionalCode(-2, 3)));
    }
}
