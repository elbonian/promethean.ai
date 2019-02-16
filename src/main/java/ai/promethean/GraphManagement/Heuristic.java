package ai.promethean.GraphManagement;

import ai.promethean.DataModel.*;
import ai.promethean.Planner.OptimizationWeightMap;

public class Heuristic {

    public static Double f_value(SystemState currentState, SystemState goalState, Double g_value) {
        return g_value + h_value(goalState, currentState);
    }

    public static Double g_value(SystemState currentState, Task currentTask, OptimizationWeightMap map) {
        return currentTask.calculateTaskWeight(map) + currentState.getgValue();
    }

    public static Double h_value(SystemState goalState, SystemState currentState) {
        Double squaredSum = 0.0;
        for (Property currentProperty : currentState.getProperties()) {
            Property goalProperty = goalState.getProperty(currentProperty.getName());
            if (currentProperty instanceof NumericalProperty && goalProperty != null) {
                squaredSum += (Math.pow(((NumericalProperty) currentProperty).getValue() - ((NumericalProperty) goalProperty).getValue(),2));
            } else if (goalProperty != null) {
                squaredSum += (1.0 - (currentProperty.equals(goalProperty) ? 1.0 : 0.0));
            }
        }
        return Math.sqrt(squaredSum);
    }
}
