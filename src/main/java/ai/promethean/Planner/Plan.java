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
    private SystemState endState;
    private List<PlanBlock> planBlockList = new ArrayList<>();
    private boolean goalHasBeenReached;

    /**
     * Instantiates a new Plan
     *
     * @param runtimeGoalState a runtime goal state
     */
    public Plan(SystemState runtimeGoalState, boolean goalIsReached) {
        this.endState = runtimeGoalState;
        this.goalHasBeenReached = goalIsReached;
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
    public SystemState getEndState() { return endState; }

    /**
     * Gets plan block list.
     *
     * @return the plan block list
     */
    public List<PlanBlock> getPlanBlockList() { return planBlockList; }

    /**
     * Gets Goal Reached boolean
     *
     * @return whether the goal has been reached or not
     */
    public boolean getGoalHasBeenReached() { return goalHasBeenReached; }

}
