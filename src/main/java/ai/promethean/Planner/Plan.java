package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;

import java.util.ArrayList;

public class Plan {
    private SystemState initialState;
    private SystemState goalState;
    private ArrayList<PlanBlock> list;

    public Plan(SystemState runtimeGoalState) {
        this.setGoalState(runtimeGoalState);
        // TODO: Create list using state.parent and state.previousTask
    }

    public void setInitialState(SystemState initialState) { this.initialState = initialState; }
    public SystemState getInitialState() { return initialState; }
    public void setGoalState(SystemState goalState) { this.goalState = goalState; }
    public SystemState getGoalState() { return goalState; }

}
