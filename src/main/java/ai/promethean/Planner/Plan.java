package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Plan.
 */
public class Plan {
    private SystemState initialState;
    private SystemState goalState;
    private List<PlanBlock> planBlockList = new ArrayList<>();

    /**
     * Instantiates a new Plan
     *
     * @param runtimeGoalState a runtime goal state
     */
    public Plan(SystemState runtimeGoalState) {
        this.goalState = runtimeGoalState;
        SystemState state = runtimeGoalState;

        while (state.getPreviousTask() != null) {
            planBlockList.add(new PlanBlock(state.getPreviousTask(), state));
            state = state.getPreviousState();
        }

        Collections.reverse(planBlockList);
        this.initialState = state;
    }

    /**
     * Gets initial state.
     *
     * @return the initial state
     */
    public SystemState getInitialState() { return initialState; }

    /**
     * Gets goal state.
     *
     * @return the goal state
     */
    public SystemState getGoalState() { return goalState; }

    /**
     * Gets plan block list.
     *
     * @return the plan block list
     */
    public List<PlanBlock> getPlanBlockList() { return planBlockList; }

}
