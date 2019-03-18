package ai.promethean.DataModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Operators {

    static List<String> allOperators = new ArrayList<>(Arrays.asList("==", ">", ">=", "<", "<=", "!="));
    static List<String> booleanOperators = new ArrayList<>(Arrays.asList("==", "!="));

    public static boolean ValidOperator(String operator) {
        for (String s: allOperators) {
            if (s.equals(operator)) {
                return true;
            }
        }
        return false;
    }

    public static boolean ValidBooleanOperator(String operator) {
        for (String s: booleanOperators) {
            if (s.equals(operator)) {
                return true;
            }
        }
        return false;
    }
}
