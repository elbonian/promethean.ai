package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;

// Algorithm: Abstract class needs only a run function which returns a runtimeGoalState
public interface Algorithm{
    SystemState run();
}
