package ai.promethean.Planner;

import ai.promethean.DataModel.*;

/**
 * The type Planner.
 */
public class Planner {

    private Algorithm algorithm;

    /**
     * Instantiates a new Planner.
     *
     * @param algorithm the algorithm to plan with
     */
    public Planner(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * return a plan from initial to goal state or null if none are found
     *
     * @return the plan
     */
    public Plan plan(SystemState initialState, GoalState goalState, TaskDictionary tasks, StaticOptimizations optimizations) {
        SystemState runtimeEndState = this.algorithm.run(initialState, goalState, tasks, optimizations);
        if (runtimeEndState != null) {
            if (goalState.meetsGoal(runtimeEndState)) {
                return new Plan(runtimeEndState, true);
            } else {
                return new Plan(runtimeEndState, false);
            }
        }
        return null;
    }

    /**
     * return a plan from initial to end state or null if none are found
     *
     * @return the plan
     */
    public Plan plan(SystemState initialState, GoalState goalState, TaskDictionary tasks, StaticOptimizations optimizations, int minutesAllowed) {
        SystemState runtimeEndState = this.algorithm.run(initialState, goalState, tasks, optimizations, minutesAllowed);
        if (runtimeEndState != null) {
            if (goalState.meetsGoal(runtimeEndState)) {
                return new Plan(runtimeEndState, true);
            } else {
                return new Plan(runtimeEndState, false);
            }
        }
        return null;
    }

}
