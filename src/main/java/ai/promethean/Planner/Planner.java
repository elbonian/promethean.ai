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
     * return a plan from initial to goal state
     *
     * @return the plan
     */
    public Plan plan(SystemState initialState, GoalState goalState, TaskDictionary tasks, StaticOptimizations optimizations) {
        SystemState runtimeGoalState = this.algorithm.run(initialState, goalState, tasks, optimizations);
        if (runtimeGoalState != null) {
            return new Plan(runtimeGoalState);
        }
        return null;
    }

}
