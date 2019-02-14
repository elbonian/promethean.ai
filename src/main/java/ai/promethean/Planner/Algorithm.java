package ai.promethean.Planner;

import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.TaskDictionary;

import java.util.ArrayList;

// Algorithm: Abstract class needs only a run function which returns a runtimeGoalState
public interface Algorithm{
    SystemState run(SystemState initialState,
                             SystemState goalState,
                             TaskDictionary tasks,
                             ArrayList<Optimization> optimizations);
}
