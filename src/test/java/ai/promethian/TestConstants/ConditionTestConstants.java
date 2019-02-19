package ai.promethian.TestConstants;

import ai.promethean.DataModel.Condition;

import java.util.ArrayList;

public class ConditionTestConstants {

    public static Condition getEqualsTestCondition() {
        return new Condition("NumProperty1",123.123,"==");
    }

    public static Condition getGreaterThanTestCondition() {
        return new Condition("NumProperty2",123.123,">");
    }

    public static Condition getGreaterThanEqualToCondition() {
        return new Condition("NumProperty2", 123.123, ">=");
    }

    public static Condition getLessThanTestCondition() {
        return new Condition("NumProperty3", 123.123, "<");
    }

    public static Condition getLessThanEqualTestCondition() {
        return new Condition("NumProperty3", 123.123, "<=");
    }

    public static Condition getNotEqualTestCondition() {
        return new Condition("NumProperty1", 123.123, "!=");
    }

    public static ArrayList<Condition> getAllTestConditions() {
        ArrayList<Condition> returnList = new ArrayList<>();
        returnList.add(getEqualsTestCondition());
        returnList.add(getGreaterThanTestCondition());
        returnList.add(getGreaterThanEqualToCondition());
        returnList.add(getLessThanTestCondition());
        returnList.add(getLessThanEqualTestCondition());
        returnList.add(getNotEqualTestCondition());
        return returnList;
    }
}
