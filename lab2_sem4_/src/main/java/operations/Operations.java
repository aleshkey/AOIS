package operations;

public class Operations {

    public static boolean operate (Boolean var1, Boolean var2, char operator){
        return chooseOperation(var1, var2, operator);
    }

    private static boolean chooseOperation(Boolean var1, Boolean var2, char operator){
        boolean res =false;
        switch (operator){
            case '|': {
                res = Boolean.logicalOr(var1, var2);
                break;
            }
            case '&':{
                res = Boolean.logicalAnd(var1, var2);
                break;
            }
        }
        return res;
    }

    public static boolean operate (Boolean var1, char operator){
        if (operator=='!'){
            return !var1;
        }
        return false;
    }
}
