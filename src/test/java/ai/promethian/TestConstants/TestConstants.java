package ai.promethian.TestConstants;

import ai.promethean.DataModel.*;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.ArrayList;

public class TestConstants {
    public static NumericalProperty getNumericProperty1() {
        return new NumericalProperty("NumProperty1",1.2343);
    }

    public static NumericalProperty getNumericProperty2() {
        return new NumericalProperty("NumProperty2", 3.14159);
    }

    public static NumericalProperty getNumericProperty3() {
        return new NumericalProperty("NumProperty3",1.2343);
    }

    public static ArrayList<NumericalProperty> getAllNumericProperties() {
        ArrayList<NumericalProperty> returnList = new ArrayList<>();
        returnList.add(getNumericProperty1());
        returnList.add(getNumericProperty2());
        returnList.add(getNumericProperty3());
        return returnList;
    }

    public static BooleanProperty getBooleanProperty1() {
        return new BooleanProperty("BoolProperty1", true);
    }

    public static BooleanProperty getBooleanProperty2() {
        return new BooleanProperty("BoolProperty2", false);
    }

    public static BooleanProperty getBooleanProperty3() {
        return new BooleanProperty("BoolProperty3", true);
    }

    public static ArrayList<BooleanProperty> getAllBooleanProperties() {
        ArrayList<BooleanProperty> returnList = new ArrayList<>();
        returnList.add(getBooleanProperty1());
        returnList.add(getBooleanProperty2());
        returnList.add(getBooleanProperty3());
        return returnList;
    }

    public static StringProperty getStringProperty1() {
        return new StringProperty("StringProperty1", "Some String I Guess");
    }

    public static StringProperty getStringProperty2() {
        return new StringProperty("StringProperty2", "Another String?");
    }

    public static StringProperty getStringProperty3() {
        return new StringProperty("StringProperty3", "Some String I Guess");
    }

    public static ArrayList<StringProperty> getAllStringProperties () {
        ArrayList<StringProperty> returnList = new ArrayList<>();
        returnList.add(getStringProperty1());
        returnList.add(getStringProperty2());
        returnList.add(getStringProperty3());
        return returnList;
    }

    public static ArrayList<Property> getAllTestProperties () {
        ArrayList<Property> returnList = new ArrayList<>();
        returnList.addAll(getAllNumericProperties());
        returnList.addAll(getAllBooleanProperties());
        returnList.addAll(getAllStringProperties());
        return returnList;
    }

    public static SystemState getFullState() {
        SystemState returnState = new SystemState(1);
        for (Property property: getAllTestProperties()) {
            returnState.addProperty(property);
        }
        return returnState;
    }

    public static SystemState getNumericState() {
        SystemState returnState = new SystemState(1);
        for (Property property: getAllNumericProperties()) {
            returnState.addProperty(property);
        }
        return returnState;
    }

    public static SystemState getBooleanState() {
        SystemState returnState = new SystemState(1);
        for (Property property: getAllBooleanProperties()) {
            returnState.addProperty(property);
        }
        return returnState;
    }

    public static SystemState getStringState() {
        SystemState returnState = new SystemState(1);
        for (Property property: getAllStringProperties()) {
            returnState.addProperty(property);
        }
        return returnState;
    }

    public static ArrayList<SystemState> getAllTestStates() {
        ArrayList<SystemState> returnList = new ArrayList<>();
        returnList.add(getFullState());
        returnList.add(getNumericState());
        returnList.add(getBooleanState());
        returnList.add(getStringState());
        return returnList;
    }
}
