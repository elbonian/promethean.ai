package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

/**
 * The type State template.
 * The minimum required information to generate a new state
 */
// Holds minimum information required to store SystemState information in frontier
public class StateTemplate {
    private SystemState previousState;
    private Task task;
    private Double g;
    private Double f;
    private double h;

    /**
     * Instantiates a new State template.
     *
     * @param prev    the previous state
     * @param t       the task that could be executed
     * @param g_value the g value of the potential next state
     * @param f_value the f value of the potential next state
     */

    public StateTemplate(SystemState prev, Task t, Double g_value, Double f_value, double h_value) {
        this.previousState = prev;
        this.task = t;
        this.g = g_value;
        this.f = f_value;
        this.h = h_value;
    }

    /**
     * Gets previous state.
     *
     * @return the previous state
     */
    public SystemState getPreviousState() {return this.previousState;}

    /**
     * Gets task.
     *
     * @return the task
     */
    public Task getTask() {return this.task;}

    /**
     * Gets g.
     *
     * @return the g
     */
    public Double getG() {return this.g;}

    /**
     * Gets f.
     *
     * @return the f
     */
    public Double getF() {return this.f;}
    /**
     * Gets gh.
     *
     * @return the h     */
    public double getH() {return this.h;}

}
