package constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static List<List<Boolean>> SUMMATOR_TABLE = new ArrayList<>(){{
        add(Arrays.asList(false, false, false));
        add(Arrays.asList(false, false, true));
        add(Arrays.asList(false, true,  false));
        add(Arrays.asList(false, true,  true));
        add(Arrays.asList(true,  false, false));
        add(Arrays.asList(true,  false, true));
        add(Arrays.asList(true,  true,  false));
        add(Arrays.asList(true,  true,  true));
    }};

    public static List<List<Boolean>> CONVERTER_TABLE = new ArrayList<>(){{
       add(Arrays.asList(false,  false,  false,  false));
       add(Arrays.asList(true,   false,  false,  false));
       add(Arrays.asList(false,  true,   false,  false));
       add(Arrays.asList(true,   true,   false,  false));
       add(Arrays.asList(false,  false,  true,   false));
       add(Arrays.asList(true,   false,  true,   false));
       add(Arrays.asList(false,  true,   true,   false));
       add(Arrays.asList(true,   true,   true,   false));
       add(Arrays.asList(false,  false,  false,  true));
       add(Arrays.asList(true,   false,  false,  true));
       add(Arrays.asList(false,  true,   false,  true));
       add(Arrays.asList(true,   true,   false,  true));
       add(Arrays.asList(false,  false,  true,   true));
       add(Arrays.asList(true,   false,  true,   true));
       add(Arrays.asList(false,  true,   true,   true));
       add(Arrays.asList(true,   true,   true,   true));
    }};

    public static List<List<Boolean>> Y_TABLE = new ArrayList<>(){{
        add(Arrays.asList(true,  false,  false,  true));
        add(Arrays.asList(false, true,   false,  true));
        add(Arrays.asList(true,  true,   false,  true));
        add(Arrays.asList(false, false,  true,   true));
        add(Arrays.asList(true,  false,  true,   true));
        add(Arrays.asList(false, true,   true,   true));
        add(Arrays.asList(true,  true,   true,   true));
        add(Arrays.asList(false, false,  false,  false));
        add(Arrays.asList(true,  false,  false,  false));
        add(Arrays.asList(false, true,   false,  false));
    }};

    public static List<Boolean> RESULT = new ArrayList<>(){{
       add(false);
       add(true);
       add(true);
       add(false);
       add(true);
       add(false);
       add(false);
       add(true);
    }};

    public static List<Boolean> CARRY = new ArrayList<>(){{
       add(false);
       add(false);
       add(false);
       add(true);
       add(false);
       add(true);
       add(true);
       add(true);
    }};
}

