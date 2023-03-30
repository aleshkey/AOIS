package operations;

import static java.lang.Math.pow;

public class BinaryOperations {

    public static int fromBinaryToInt(String code){
        int res=0;
        for (int i =0; i<code.length(); i++){
            int charInInt = Integer.parseInt(String.valueOf(code.charAt(i)));
            res += charInInt * pow(2, i);
        }
        return res;
    }

}
