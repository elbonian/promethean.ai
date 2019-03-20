package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

/**
 * The type Plan block.
 */
public class PlanBlock {
    private Task task;
    private SystemState state;

    /**
     * Instantiates a new Plan block.
     *
     * @param task  a task
     * @param state a state
     */
    public PlanBlock(Task task, SystemState state) {
        this.task = task;
        this.state = state;
    }

    /**
     * Gets task.
     *
     * @return the task
     */
    public Task getTask() { return task; }

    /**
     * Gets state.
     *
     * @return the state
     */
    public SystemState getState() { return state; }

}
