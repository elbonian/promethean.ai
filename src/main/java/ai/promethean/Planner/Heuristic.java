package ai.promethean.Planner;

import ai.promethean.DataModel.*;

/**
 * The type Heuristic.
 */
public class Heuristic {

    /**
     * Returns the f_value of a state.
     *
     * @param currentState the current state
     * @param goalState    the goal state
     * @param g_value      the current state g value
     * @return the current state f value
     */
    public static Double f_value(SystemState currentState, GoalState goalState, Double g_value) {
        return g_value + h_value(currentState, goalState);
    }

    /**
     * Returns the g_value of a state.
     *
     * @param currentState  the current state
     * @param currentTask   the current task
     * @param optimizations the optimizations list
     * @return The current state g_value
     */
    public static Double g_value(SystemState currentState, Task currentTask, StaticOptimizations optimizations) {
        return TaskWeight.calculateTaskWeight(currentTask, optimizations) + currentState.getgValue();
    }

    /**
     * Returns the h_value of a state.
     *
     * @param currentState the current state
     * @param goalState the goal state
     * @return the current state h_value
     */
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
