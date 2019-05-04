package ai.promethean.DataModel;

/**
 * Used to keep track of value changes in SystemStates. A TaskInstance will include a previous SystemState and the Task executed in that state to transition in forward in the plan. To be used in the planning algorithm to be able to backtrack in a plan
 */
public class TaskInstance {
    private SystemState prevSystemState;
    private Task task;

    /**
     * Instantiates a new TaskInstance with the given SystemState and Task
     *
     * @param _prevSystemState The previous SystemState
     * @param _task            The Task
     */
    public TaskInstance(SystemState _prevSystemState, Task _task){
        setTask(_task);
        setPreviousSystemState(_prevSystemState);
    }

    /**
     * Sets previous SystemState
     *
     * @param _previousSystemState The previous SystemState
     */
    public void setPreviousSystemState(SystemState _previousSystemState) {
        this.prevSystemState = _previousSystemState;
    }

    /**
     * Set Task
     *
     * @param _task The Task
     */
    public void setTask(Task _task){
        this.task = _task;
    }

    /**
     * Gets previous SystemState
     *
     * @return The previous SystemState
     */
    public SystemState getPrevSystemState() {
        return prevSystemState;
    }

    /**
     * Get the executed Task
     *
     * @return The Task
     */
    public Task getTask(){
        return task;
    }

    public String toString() {
        return "Task Instance: \n Previous State: " + prevSystemState
                + "\n Perturbation: " + task;
    }
}
