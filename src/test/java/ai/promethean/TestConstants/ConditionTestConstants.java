package ai.promethean.TestConstants;

import ai.promethean.DataModel.Condition;
import ai.promethean.DataModel.NumericalCondition;

import java.util.ArrayList;
import java.util.List;

public class ConditionTestConstants {

    public static Condition getEqualsTestCondition() {
        return new NumericalCondition("NumProperty1","==", 123.123);
    }

    public static Condition getGreaterThanTestCondition() {
        return new NumericalCondition("NumProperty2", "<", 123.123);
    }

    public static Condition getGreaterThanEqualToCondition() {
        return new NumericalCondition("NumProperty2", ">=", 123.123);
    }

    public static Condition getLessThanTestCondition() {
        return new NumericalCondition("NumProperty3", "<", 123.123);
    }

    public static Condition getLessThanEqualTestCondition() {
        return new NumericalCondition("NumProperty3", "<=", 123.123);
    }

    public static Condition getNotEqualTestCondition() {
        return new NumericalCondition("NumProperty1", "!=", 123.123);
    }

    public static List<Condition> getAllTestConditions() {
        List<Condition> returnList = new ArrayList<>();
        returnList.add(getEqualsTestCondition());
        returnList.add(getGreaterThanTestCondition());
        returnList.add(getGreaterThanEqualToCondition());
        returnList.add(getLessThanTestCondition());
        returnList.add(getLessThanEqualTestCondition());
        returnList.add(getNotEqualTestCondition());
        return returnList;
    }
}
