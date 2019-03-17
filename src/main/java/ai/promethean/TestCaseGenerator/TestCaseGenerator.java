package ai.promethean.TestCaseGenerator;
import ai.promethean.DataModel.*;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

// TODO:
//  - Implement requirement generation

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
    private String filename = null;

    /**
     * Generate a new test case with given starting and goal state. Uses starting state's PropertyMap to create actions
     *
     * @param state The SystemState to begin generating from
     * @param goal  The SystemState to plan towards
     */
    public TestCaseGenerator(SystemState state, GoalState goal) {
        this.inputState = state;
        this.goalState = goal;
        this.stateProps = state.getPropertyMap();
        this.goalReqs = goal.getRequirements();
    }

    /**
     * Generate a new test case with given start and goal states, along with a maximum number of tasks to generate
     *
     * @param state    The SystemState to begin generating from
     * @param goal     The SystemState to plan towards
     * @param numTasks The maximum number of tasks to generate
     */
    public TestCaseGenerator(SystemState state, GoalState goal, int numTasks) {
        this.inputState = state;
        this.goalState = goal;
        this.stateProps = state.getPropertyMap();
        this.goalReqs = goal.getRequirements();
        this.numTasks = numTasks;
    }

    public TestCaseGenerator(SystemState state, GoalState goal, int numTasks, String filename) {
        this.inputState = state;
        this.goalState = goal;
        this.stateProps = state.getPropertyMap();
        this.goalReqs = goal.getRequirements();
        this.numTasks = numTasks;
        this.filename = filename;
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
                    if(req_name.toLowerCase().equals("battery") || req_name.toLowerCase().equals("fuel")) {
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
     *
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
            }
            else if(delta.getValue() instanceof Boolean) {
                Task task = new Task(ThreadLocalRandom.current().nextInt(1, 51), delta_name + "_assignment");
                task.addProperty(delta_name, (Boolean)delta_value, "assignment");
                tasks.add(task);
            }
        }

        return tasks;
    }

    /**
     * Public method to retrieve the critical path tasks
     *
     * @return ArrayList of Tasks which are intended to be the main set of tasks selected in the test case
     */
    public ArrayList<Task> getCriticalPathTasks() {
        return createCriticalPathTasks(getPropertyDeltas());
    }

    private ArrayList<Task> createRemainingTasks(ArrayList<Task> criticalPath) {
        ArrayList<Task> tasksList = new ArrayList<>(criticalPath);
        // Create new tasks until there are the specified number of tasks
        for(int i = criticalPath.size(); i <= this.numTasks-1; i++) {
            // init new Task with random duration in range [1, 50]
            Task new_task = new Task(randomIntInRange(1, 50));
            // I can't find a way to select random elements from the props ArrayList without dups w/o deleting them from list when done?
            ArrayList<Property> temp_props = stateProps.getProperties();
            // How many properties to include in randomly generated tasks? 1 - 5
            int num_props = randomIntInRange(1, temp_props.size() % 5);
            // Randomly grab properties from the input state properties
            for(int j = 0; j < num_props; j++) {
                int randomIndex = randomIntInRange(temp_props.size());
                Property affected_prop = temp_props.get(randomIndex);
                if(affected_prop instanceof BooleanProperty) {
                    // Update to opposite of the current BooleanProperty value
                    Boolean update = !(Boolean)affected_prop.getValue();
                    new_task.addProperty(affected_prop.getName(), update, "assignment");
                    new_task.addRequirement(affected_prop.getName(), ((BooleanProperty)affected_prop).getValue(), "==");
                }
                else if (affected_prop instanceof NumericalProperty) {
                    // Flip a coin for each NumericalProperty to see if it'll be positive or negative
                    Double delta;
                    // Special case when the value is already at zero, we can only go up from here!
                    if((Double)affected_prop.getValue() == 0) {
                        delta = randomDoubleInRange(50.0);
                    } else {
                        delta = randomDoubleInRange(((NumericalProperty) affected_prop).getValue());
                        if(randomIntInRange(100) % 2 == 0) {
                            delta *= -1;
                        }
                    }
                    new_task.addProperty(affected_prop.getName(), delta, "delta");
                }
                // Remove the property we just affected so we don't affect the same property multiple times
                // Is this needed? My head hurts.
                temp_props.remove(randomIndex);
            }
            new_task.addRequirement("Battery", randomDoubleInRange(15.0, 40.0), ">=");
            tasksList.add(new_task);
        }
        return tasksList;
    }

    /**
     * Generate optimizations for the test case
     * @param numOps The number of optimizations to generate, after adding the battery optimization
     * @return ArrayList of Optimization objects
     */
    public ArrayList<Optimization> generateOptimizations(int numOps) {
        ArrayList<Optimization> ops = new ArrayList<>();
        Optimization battery_op = new Optimization("Battery", "max", 0);
        ops.add(battery_op);

        // Duplicate input_state property list to randomly select from w/o repeats
        ArrayList<Property> temp_props = stateProps.getProperties();
        for(int i = 0; i < numOps; i++) {
            int randomIndex = randomIntInRange(temp_props.size());
            Property random_prop = temp_props.get(randomIndex);
            // Flip coin to decide whether the op is max or min
            String op_type;
            if(randomIntInRange(100) % 2 == 1) {
                op_type = "min";
            }
            else {
                op_type = "max";
            }
            Optimization new_op = new Optimization(random_prop.getName(), op_type, i+1);
            ops.add(new_op);
            // Remove the affected property so we don't optimize it twice
            temp_props.remove(randomIndex);
        }
        return ops;
    }

    /**
     * Generate perturbations for the test case
     * @param numPerts The number of perturbations to generate
     * @return ArrayList of Perturbation objects
     */
    private ArrayList<Perturbation> generatePerturbations(int numPerts) {
        ArrayList<Perturbation> perts = new ArrayList<>();
        // Duplicate input_state property list to randomly select from w/o repeats
        ArrayList<Property> temp_props = stateProps.getProperties();
        for(int i=0; i<numPerts; i++) {
            // Get random property to affect in Perturbation
            int randomIndex = randomIntInRange(temp_props.size());
            Property random_prop = temp_props.get(randomIndex);
            String affected_prop_name = random_prop.getName();

            // Create perturbation object
            String pert_name = affected_prop_name + "_pert";
            int duration = randomIntInRange(500);
            Perturbation new_pert = new Perturbation(pert_name, duration);

            if(random_prop instanceof BooleanProperty) {
                Boolean impact = !(Boolean)random_prop.getValue();
                // Add generated property_impact to new perturbation
                new_pert.addProperty(affected_prop_name, impact, "assignment");
            }
            else if(random_prop instanceof NumericalProperty) {
                // Get random impact value, then flip coin for positive/negative
                Double impact = randomDoubleInRange(100.0);
                if(randomIntInRange(100) % 2 == 0) {
                    impact *= -1;
                }
                // Add generated property_impact to new perturbation
                new_pert.addProperty(affected_prop_name, impact, "delta");
            }
            // Add the new perturbation to the list of perts
            perts.add(new_pert);
            // Remove the used temp_prop so we don't pert the same thing twice
            temp_props.remove(randomIndex);
        }
        return perts;
    }

    /**
     * Generates a full test case, so each individual method doesn't have to be called on its own
     *
     * @return An ArrayList of Task objects, which are all the tasks in the test case
     */
    public ArrayList<Task> generateTestCase() {
        ArrayList<PropertyDelta> deltas = computePropertyDeltas();
        ArrayList<Task> critical_path = createCriticalPathTasks(deltas);
        ArrayList<Task> full_plan = createRemainingTasks(critical_path);
        return full_plan;
    }

    /**
     * Convert an input ArrayList of Tasks, along with the input SystemState and GoalState, into a valid JSON test file
     *
     * @param input_tasks The generated ArrayList of Tasks for the test case
     */
    @SuppressWarnings("unchecked")
    public void testCaseToJSON(ArrayList<Task> input_tasks, ArrayList<Optimization> ops, int num_perts) throws IOException {
        // obj is the top level object
        JSONObject obj = new JSONObject();
        // Main JSON fields
        JSONObject initial_state = new JSONObject();
        JSONObject goal_state = new JSONObject();
        JSONArray optimizations = new JSONArray();
        JSONArray perturbations = new JSONArray();
        JSONArray tasks = new JSONArray();
        // Sub-fields for properties/requirements for init state and goal state
        JSONArray init_state_props = new JSONArray();
        JSONArray goal_state_reqs = new JSONArray();

        // ***** Generate initial_state properties *****
        for(Property input_prop: stateProps.getProperties()) {
            // Create JSONObject for each property, then add to initial_state
            JSONObject this_prop = new JSONObject();
            this_prop.put("name", input_prop.getName());
            this_prop.put("value", input_prop.getValue());
            init_state_props.put(this_prop);
        }
        initial_state.put("properties", init_state_props);

        // ***** Generate goal_state requirements Sne*****
        for(Condition goal_req: goalReqs) {
            // Create JSONObject for each requirement, then add to goal_state
            JSONObject this_req = new JSONObject();
            this_req.put("name", goal_req.getName());
            this_req.put("value", goal_req.getValue());
            this_req.put("operator", goal_req.getOperator());
            goal_state_reqs.put(this_req);
        }
        goal_state.put("requirements", goal_state_reqs);

        // ***** Generate tasks *****
        for(Task task: input_tasks) {
            // Create JSONObject for this task, populate, and then add to the tasks JSONArray
            JSONObject this_task = new JSONObject();
            this_task.put("name", task.getName() + "_task");
            this_task.put("duration", task.getDuration());
            JSONArray requirements = new JSONArray();
            JSONArray property_impacts = new JSONArray();
            for(Condition req: task.getRequirements()) {
                JSONObject new_req = new JSONObject();
                new_req.put("name", req.getName());
                new_req.put("value", req.getValue());
                new_req.put("operator", req.getOperator());
                requirements.put(new_req);
            }
            for(Property prop: task.getProperty_impacts().getProperties()) {
                JSONObject new_prop = new JSONObject();
                new_prop.put("name", prop.getName());
                new_prop.put("type", prop.getType());
                new_prop.put("value", prop.getValue());
                property_impacts.put(new_prop);
            }
            this_task.put("requirements", requirements);
            this_task.put("property_impacts", property_impacts);
            tasks.put(this_task);
        }

        // ***** Generate optimizations *****
        for(Optimization input_op: ops) {
            JSONObject this_op = new JSONObject();
            this_op.put("name", input_op.getName());
            this_op.put("priority", input_op.getPriority());
            this_op.put("type", input_op.getType());
            optimizations.put(this_op);
        }

        // ***** Generate perturbations *****
        ArrayList<Perturbation> perts = generatePerturbations(num_perts);
        System.out.println(perts);
        for(Perturbation pert: perts) {
            JSONObject this_pert = new JSONObject();
            this_pert.put("name", pert.getName());
            this_pert.put("time", pert.getTime());
            JSONArray property_impacts = new JSONArray();
            ArrayList<Property> prop_impacts = pert.getProperties().getProperties();
            for(Property prop: prop_impacts) {
                JSONObject this_impact = new JSONObject();
                this_impact.put("name", prop.getName());
                this_impact.put("type", prop.getType());
                this_impact.put("value", prop.getValue());
                property_impacts.put(this_impact);
            }
             this_pert.put("property_impacts", property_impacts);
            perturbations.put(this_pert);
        }

        obj.put("initial_state", initial_state);
        obj.put("goal_state", goal_state);
        obj.put("tasks", tasks);
        obj.put("optimizations", optimizations);
        obj.put("perturbations", perturbations);

        String filepath;
        if(filename == null) {
            filepath = "JSON_input/TestCases/generated_test.json";
        }
        else {
            filepath = "JSON_input/TestCases/" + filename + ".json";
        }
        try(FileWriter file = new FileWriter(filepath)) {
            file.write(obj.toString(4));
            System.out.println("Successfully Copied JSON Object to File...");
        }
    }


    private Integer randomIntInRange(Integer i1, Integer i2) {
        return ThreadLocalRandom.current().nextInt(i1, i2);
    }

    private Integer randomIntInRange(Integer i1) {
        return ThreadLocalRandom.current().nextInt(i1);
    }

    private Double randomDoubleInRange(Double d1) {
        Double random = ThreadLocalRandom.current().nextDouble(d1);
        random = Math.floor(random * 10) / 10;
        return random;
    }

    private Double randomDoubleInRange(Double d1, Double d2) {
        Double random = ThreadLocalRandom.current().nextDouble(d1, d2);
        random = Math.floor(random * 10) / 10;
        return random;
    }
}