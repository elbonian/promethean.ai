package ai.promethean.GraphManagement;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

public class StateTemplate {
    private SystemState previousState;
    private Task task;
    private Integer f;
    private Integer g;

    public StateTemplate(SystemState prev, Task t, Integer g_value, Integer f_value) {
        this.previousState = prev;
        this.task = t;
        this.g = g_value;
        this.f = f_value;
    }

    public SystemState getPreviousState() {return this.previousState;}
    public Task getTask() {return this.task;}
    public Integer getF() {return this.f;}
    public Integer getG() {return this.g;}

}
