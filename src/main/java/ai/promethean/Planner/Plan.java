package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;

import java.util.ArrayList;
import java.util.Collections;

public class Plan {
    private SystemState initialState;
    private SystemState goalState;
    private ArrayList<PlanBlock> planBlockList = new ArrayList<>();

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

    public SystemState getInitialState() { return initialState; }
    public SystemState getGoalState() { return goalState; }
    public ArrayList<PlanBlock> getPlanBlockList() { return planBlockList; }

}
