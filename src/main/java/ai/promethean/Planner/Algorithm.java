package ai.promethean.Planner;

import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.StaticOptimizations;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.TaskDictionary;


/**
 * The interface Algorithm.
 */
public interface Algorithm {
    /**
     * Run system state.
     *
     * @return the system state
     */
    SystemState run(SystemState initState, GoalState goalState, TaskDictionary tasks, StaticOptimizations optimizations, int stopTime, boolean activateCLF);
}
