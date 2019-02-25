package ai.promethean.GraphManagement;

import ai.promethean.DataModel.*;
import ai.promethean.Planner.TaskWeight;

public class Heuristic {

    public static Double f_value(SystemState currentState, GoalState goalState, Double g_value) {
        return g_value + h_value(goalState, currentState);
    }

    public static Double g_value(SystemState currentState, Task currentTask, StaticOptimizations optimizations) {
        return TaskWeight.calculateTaskWeight(currentTask, optimizations) + currentState.getgValue();
    }

    private static Double h_value(GoalState goalState, SystemState currentState) {
        Double squaredSum = 0.0;
        for (Condition goalCondition: goalState.getRequirements()) {
            String currentPropertyName = goalCondition.getName();
            Property currentProperty = currentState.getProperty(currentPropertyName);
            if (!goalCondition.evaluate(currentProperty.getValue())) {
                if(currentProperty instanceof NumericalProperty) {
                    squaredSum += (Math.pow(((NumericalProperty) currentProperty).getValue() - ((NumericalCondition) goalCondition).getValue(),2));
                } else {
                    squaredSum += 1.0;
                }
            }
        }
        return Math.sqrt(squaredSum);
    }
}
