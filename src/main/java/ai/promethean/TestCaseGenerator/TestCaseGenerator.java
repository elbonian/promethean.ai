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
    private ArrayList<PropertyDelta> computePropertyDeltas() {
        ArrayList<PropertyDelta> deltas = new ArrayList<>();
        for(Condition goalReq: goalReqs) {
            String req_name = goalReq.getName();
            if(stateProps.containsProperty(req_name)) {
                Property init_prop = stateProps.getProperty(req_name);
                // Storing this here to figure out the proper way to subtract the init and goal state values
                String req_op = goalReq.getOperator();
                Object req_val = goalReq.getValue();
                if(req_val instanceof Integer) {
                    // I don't think this is a nice way to do this, with the type casting from Object
                    Integer delta = (Integer)init_prop.getValue() - (Integer)req_val;
                    deltas.add(new NumericalDelta(req_name, delta));
                }
                else if(req_val instanceof Boolean) {
                    deltas.add(new BooleanDelta(req_name, (Boolean)req_val));
                }
            }
        }
        return deltas;
    }
}