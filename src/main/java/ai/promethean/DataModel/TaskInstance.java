package ai.promethean.DataModel;

public class TaskInstance {
    private SystemState prevSystemState;
    private Task task;

    public TaskInstance(SystemState _prevSystemState, Task _task){
        setTask(_task);
        setPreviousSystemState(_prevSystemState);
    }

    public void setPreviousSystemState(SystemState _previousSystemState) {
        this.prevSystemState = _previousSystemState;
    }

    public void setTask(Task _task){
        this.task = _task;
    }

    public SystemState getPrevSystemState() {
        return prevSystemState;
    }
    public Task getTask(){
        return task;
    }

    public String toString() {
        return "Task Instance: \n Previous State: " + prevSystemState
                + "\n Perturbation: " + task;
    }
}
