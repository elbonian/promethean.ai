package ai.promethian.TestConstants;

import ai.promethean.DataModel.Property;
import ai.promethean.DataModel.SystemState;

import java.util.ArrayList;
import java.util.List;

public class StateTestConstants {
    public static SystemState getFullState() {
        SystemState returnState = new SystemState(1);
        for (Property property:
                PropertyTestConstants.getAllTestProperties()) {
            returnState.addProperty(property);
        }
        return returnState;
    }

    public static SystemState getNumericState() {
        SystemState returnState = new SystemState(1);
        for (Property property:
                PropertyTestConstants.getAllNumericProperties()) {
            returnState.addProperty(property);
        }
        return returnState;
    }

    public static SystemState getBooleanState() {
        SystemState returnState = new SystemState(1);
        for (Property property:
                PropertyTestConstants.getAllBooleanProperties()) {
            returnState.addProperty(property);
        }
        return returnState;
    }

    public static SystemState getStringState() {
        SystemState returnState = new SystemState(1);
        for (Property property:
                PropertyTestConstants.getAllStringProperties()) {
            returnState.addProperty(property);
        }
        return returnState;
    }

    public static List<SystemState> getAllTestStates() {
        List<SystemState> returnList = new ArrayList<>();
        returnList.add(getFullState());
        returnList.add(getNumericState());
        returnList.add(getBooleanState());
        returnList.add(getStringState());
        return returnList;
    }
}
