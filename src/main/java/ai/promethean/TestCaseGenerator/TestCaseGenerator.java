package ai.promethean.TestCaseGenerator;
import ai.promethean.DataModel.*;
import java.util.*;

/**
 * A class to generate test cases (hopefully) with a given depth / complexity
 */
public class TestCaseGenerator {
    private SystemState inputState;
    private GoalState goalState;
    private PropertyMap stateProps;
    private ArrayList<Condition> goalReqs;
    private ArrayList<Task> tasks = new ArrayList<>();
    private int numTasks = 25;

    /**
     * Generate a new test case with given starting and goal state. Uses starting state's PropertyMap to create actions
     * @param state The SystemState to begin generating from
     * @param goal The SystemState to plan towards
     */
    public TestCaseGenerator(SystemState state, GoalState goal) {
        this.inputState = state;
        this.goalState = goal;
        this.stateProps = state.getPropertyMap();
        this.goalReqs = goal.getRequirements();
    }

    /**
     * Generate a new test case with given start and goal states, along with a maximum number of tasks to generate
     * @param state The SystemState to begin generating from
     * @param goal The SystemState to plan towards
     * @param numTasks The maximum number of tasks to generate
     */
    public TestCaseGenerator(SystemState state, GoalState goal, int numTasks) {
        this.inputState = state;
        this.goalState = goal;
        this.stateProps = state.getPropertyMap();
        this.goalReqs = goal.getRequirements();
        this.numTasks = numTasks;
    }

    /**
     * Compute the difference in properties between the input and goal states and create a list of those deltas
     */
    private void computePropertyDeltas() {
        for(Condition goalReq: goalReqs) {
            if(stateProps.containsProperty(goalReq.getName())) {
                // Compute the difference in values between init and goal state
                // Somehow add the (name, difference) tuple to some list or something
            }
        }
    }
}