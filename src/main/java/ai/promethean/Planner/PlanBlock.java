package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

public class PlanBlock {
    private Task task;
    private SystemState state;

    public PlanBlock(Task task, SystemState state) {
        this.task = task;
        this.state = state;
    }

    public Task getTask() { return task; }
    public SystemState getState() { return state; }

}
