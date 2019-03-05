package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;

// Algorithm: Interface needs only a run function which returns a runtimeGoalState
public interface Algorithm {
    SystemState run();
}
