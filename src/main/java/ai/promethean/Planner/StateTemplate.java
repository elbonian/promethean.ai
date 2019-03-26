package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

// Holds minimum information required to store SystemState information in frontier
public class StateTemplate {
    private SystemState previousState;
    private Task task;
    private Double g;
    private Double f;
    private double h;

    public StateTemplate(SystemState prev, Task t, Double g_value, Double f_value, double h_value) {
        this.previousState = prev;
        this.task = t;
        this.g = g_value;
        this.f = f_value;
        this.h = h_value;
    }

    public SystemState getPreviousState() {return this.previousState;}
    public Task getTask() {return this.task;}
    public Double getG() {return this.g;}
    public Double getF() {return this.f;}
    public double getH() {return this.h;}

}
