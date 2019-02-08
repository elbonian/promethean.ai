package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

public abstract class Heuristic {
    public abstract Integer g_value(SystemState state, Task task);
    public abstract Integer f_value(SystemState state, Task task, Integer g_value);
}
