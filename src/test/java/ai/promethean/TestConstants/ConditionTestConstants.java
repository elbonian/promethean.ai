package ai.promethean.TestConstants;

import ai.promethean.DataModel.Condition;
import ai.promethean.DataModel.NumericalCondition;

import java.util.ArrayList;
import java.util.List;

public class ConditionTestConstants {

    public static Condition getGreaterThanTestCondition() {
        return new NumericalCondition("NumProperty1", "<", 123.123);
    }

    public static Condition getGreaterThanEqualToCondition() {
        return new NumericalCondition("NumProperty2", ">=", 123.123);
    }


    public static Condition getLessThanEqualTestCondition() {
        return new NumericalCondition("NumProperty3", "<=", 123.123);
    }

    public static List<Condition> getAllTestConditions() {
        List<Condition> returnList = new ArrayList<>();
        returnList.add(getGreaterThanTestCondition());
        returnList.add(getGreaterThanEqualToCondition());
        returnList.add(getLessThanEqualTestCondition());
        return returnList;
    }
}
