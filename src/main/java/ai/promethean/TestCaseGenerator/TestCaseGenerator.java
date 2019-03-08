package ai.promethean.TestCaseGenerator;
import ai.promethean.DataModel.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import java.io.FileWriter;
import java.io.IOException;

// TODO:
//  - Randomly select multiple properties to have Task effect (affect?) at once
//  - Create Tasks to a/effect Properties not just in needed Deltas
//  - Implement requirement generation
//  - **** Figure out writing to JSON files ****
//  - Make sure generated files actually work


/**
 * A class to generate test cases (hopefully) with a given depth / complexity
 */
public class TestCaseGenerator {
    private SystemState inputState;
    private GoalState goalState;
    private PropertyMap stateProps;
    private ArrayList<Condition> goalReqs;
    private ArrayList<Task> tasks = new ArrayList<>();
    private int numTasks = 15;

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
     * @return An ArrayList of PropertyDelta objects
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
                if(req_val instanceof Double) {
                    // I don't think this is a nice way to do this, with the type casting from Object
                    Double delta = (Double)req_val - (Double)init_prop.getValue();
                    // If we are looking at a delta for battery/fuel, we need a special NumericalDelta
                    // If init has fuel 100, and goal is fuel >= 80, we don't necessarily need to delta exactly -20
                    if(req_name.equals("Battery") || req_name.equals("Fuel")) {
                        String operator = "";
                        switch(req_op)
                        {
                            case ">":
                                operator = "<";
                                break;
                            case "<":
                                operator = ">";
                                break;
                            case ">=":
                                operator = "<=";
                                break;
                            case "<=":
                                operator = ">=";
                                break;
                            case "==":
                                operator = "==";
                                break;
                        }
                        deltas.add(new NumericalDelta(req_name, delta, operator));
                    }
                    else {
                        deltas.add(new NumericalDelta(req_name, delta));
                    }
                }
                else if(req_val instanceof Boolean) {
                    deltas.add(new BooleanDelta(req_name, (Boolean)req_val));
                }
            }
        }
        return deltas;
    }

    /**
     * Public method to retrieve ArrayList of the PropertyDeltas for changing from initial to goal states
     * @return The ArrayList of PropertyDelta objects retrieved from computePropertyDeltas()
     */
    public ArrayList<PropertyDelta> getPropertyDeltas() {
        return computePropertyDeltas();
    }

    private ArrayList<Task> createCriticalPathTasks(ArrayList<PropertyDelta> deltas) {
        ArrayList<Task> tasks = new ArrayList<>();
        // Always create a task that charges the battery
        if(stateProps.containsProperty("Battery")) {
            Task charge = new Task(ThreadLocalRandom.current().nextInt(1, 21), "charge");
            charge.addProperty("Battery", 10.0, "delta");
            tasks.add(charge);
        }
        for(PropertyDelta delta: deltas) {
            // Skip making a Task for the battery since we already did
            if(delta.getName().equals("Battery")) {
                continue;
            }
            Object delta_value = delta.getValue();
            String delta_name = delta.getName();
            // Init new task with random duration between 1 and 50
            // Check whether it's a Numerical or Boolean delta to decide whether to delta or assignment the Task
            if(delta.getValue() instanceof Double) {
                Task task = new Task(ThreadLocalRandom.current().nextInt(1, 51), delta_name + "_delta");
                task.addProperty(delta_name, (Double)delta_value, "delta");
                tasks.add(task);
                System.out.println("Adding task!\n" + task.getProperty_impacts() + "\n************\n");
            }
            else if(delta.getValue() instanceof Boolean) {
                Task task = new Task(ThreadLocalRandom.current().nextInt(1, 51), delta_name + "_assignment");
                task.addProperty(delta_name, (Boolean)delta_value, "assignment");
                tasks.add(task);
                System.out.println("Adding task!\n" + task.getProperty_impacts() + "\n************\n");
            }
        }

        return tasks;
    }

    public ArrayList<Task> getCriticalPathTasks() {
        return createCriticalPathTasks(getPropertyDeltas());
    }
}