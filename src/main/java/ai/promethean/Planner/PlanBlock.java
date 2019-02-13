package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

public class PlanBlock {
    private Task task;
    private SystemState state;

    public PlanBlock(Task task, SystemState state) {
        this.setTask(task);
        this.setState(state);
    }

    public void setTask(Task task) { this.task = task; }
    public Task getTask() { return task; }
    public void setState(SystemState state) { this.state = state; }
    public SystemState getState() { return state; }

}
