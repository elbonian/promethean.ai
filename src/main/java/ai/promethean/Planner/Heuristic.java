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
    public static Double h_value(SystemState currentState, GoalState goalState) {
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
