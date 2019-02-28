package ai.promethean.Planner;

import ai.promethean.DataModel.*;

public class Heuristic {

    public static Double f_value(SystemState currentState, GoalState goalState, Double g_value) {
        return g_value + h_value(currentState, goalState);
    }

    public static Double g_value(SystemState currentState, Task currentTask, StaticOptimizations optimizations) {
        return TaskWeight.calculateTaskWeight(currentTask, optimizations) + currentState.getgValue();
    }

    private static Double h_value(SystemState currentState, GoalState goalState) {
        Double squaredSum = 0.0;

        for (Condition goalCondition : goalState.getRequirements()) {
            Property currentProperty = currentState.getProperty(goalCondition.getName());
            if (!goalCondition.evaluate(currentProperty.getValue())) {
                if (currentProperty instanceof NumericalProperty) {
                    squaredSum += (Math.pow(((NumericalProperty) currentProperty).getValue() - ((NumericalCondition) goalCondition).getValue(), 2));
                } else {
                    squaredSum += 1.0;
                }
            }
        }

        return Math.sqrt(squaredSum);
    }
}
