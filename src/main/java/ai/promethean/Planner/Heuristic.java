package ai.promethean.Planner;

import ai.promethean.DataModel.*;

/**
 * The type Heuristic.
 */
public class Heuristic {

    /**
     * Returns the h_value of a state.
     *
     * @param currentState the current state
     * @param goalState the goal state
     * @return the current state h_value
     */
    public static Double h_value(SystemState currentState, GoalState goalState, Task task) {
        Double squaredSum = 0.0;

        for (Condition goalCondition : goalState.getRequirements()) {
            Property currentProperty = currentState.getProperty(goalCondition.getName());
            Property taskImpact = task.getProperty(goalCondition.getName());
            Object nextStatePropertyValue;
            if (taskImpact != null) {
                nextStatePropertyValue = currentProperty.applyImpact(taskImpact).getValue();
            } else {
                nextStatePropertyValue = currentProperty.getValue();
            }
            if (!goalCondition.evaluate( nextStatePropertyValue )) {
                if (nextStatePropertyValue instanceof Double) {
                    squaredSum += (Math.pow(((Double) nextStatePropertyValue) - ((NumericalCondition) goalCondition).getValue(), 2));
                } else {
                    squaredSum += 1.0;
                }
            }
        }

        return Math.sqrt(squaredSum);
    }
}
