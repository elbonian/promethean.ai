package ai.promethean.Planner;

import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.Perturbation;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.TaskDictionary;

import java.util.ArrayList;

public class Planner {

    private SystemState initialState;
    private SystemState goalState;
    private TaskDictionary tasks;
    private ArrayList<Optimization> optimizations;
    private ArrayList<Perturbation> perturbations;
    private Algorithm algorithm;

    public Planner(SystemState initialState,
                   SystemState goalState,
                   TaskDictionary tasks,
                   ArrayList<Optimization> optimizations,
                   ArrayList<Perturbation> perturbations,
                   Algorithm algorithm) {

        this.initialState = initialState;
        this.goalState = goalState;
        this.tasks = tasks;
        this.optimizations = optimizations;
        this.perturbations = perturbations;
        this.algorithm = algorithm;
    }

    // Uses Planner algorithm.run() to get a runtimeGoalState and returns a Plan object
    public void plan() {
        SystemState runtimeGoalState = this.algorithm.run(this.initialState, this.goalState, this.tasks, this.optimizations);
        // TODO: return this....
        // return this.CreatePlan(runtimeGoalState);
    }

    // Given a runtimeGoalState, backtracks to create Plan object
    private void createPlan(SystemState runtimeGoalState) {
        // TODO: Create Plan
    }



}
