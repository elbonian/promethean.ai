package ai.promethean.Planner;

import ai.promethean.DataModel.*;
import ai.promethean.Logger.Logger;


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
    public Plan plan(SystemState initialState,
                     GoalState goalState,
                     TaskDictionary tasks,
                     StaticOptimizations optimizations,
                     double stopTime,
                     boolean activateCLF) {
        Logger.writeLog("Running planning algorithm.", "Planner");
        SystemState runtimeEndState = this.algorithm.run(initialState, goalState, tasks, optimizations, stopTime, activateCLF);
        if (runtimeEndState != null) {
            Plan plan;
            if (goalState.meetsGoal(runtimeEndState)) {
                plan = new Plan(runtimeEndState, true);
            } else {
                Logger.writeLog("Time Limit Exceeded. Goal state was not found. Creating Plan for states explored until termination.", "Planner");
                plan = new Plan(runtimeEndState, false);
            }
            Logger.writeLog("Initial State: \n" + plan.getInitialState(), "Planner");
            Logger.writeLog("Runtime Goal State:: \n" + plan.getEndState(), "Planner");
            Logger.writeLog("Plan: \n" + plan.getPlanBlockList(), "Planner");
            return plan;
        }
        Logger.writeLog("There is no possible plan from initial state to goal state.", "Planner");
        return null;
    }
}
