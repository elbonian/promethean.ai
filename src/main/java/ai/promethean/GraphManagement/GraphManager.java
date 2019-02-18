package ai.promethean.GraphManagement;

import ai.promethean.DataModel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class GraphManager {

    private PriorityQueue<StateTemplate> frontier;
    private static SystemState initState;
    private static SystemState goalState;
    private static TaskDictionary taskDict;

    public GraphManager() {

    }
    private static ArrayList<Task> validTasks(SystemState state, TaskDictionary taskDictionary){
        // For keeping track of valid tasks
        ArrayList<Task> valid_tasks = new ArrayList<>();
        for (int i = 0; i < taskDictionary.size(); i++) {
            boolean possible_task = false;
            Task current_task = taskDictionary.getTask(i);
            ArrayList<Condition> requirements = current_task.getRequirements();
            // I love IntelliJ
            for (Condition condition : requirements) {
                String name = condition.getName();
                double value = condition.getValue();
                Object state_value = state.getProperty(name);
                // ISSUE: condition.evaluate only does Doubles, extending props will break this
                // Extend after merging other changes to handle different inputs
                // Basically remove the (Double) cast right there, for now it won't compile without it
                if (condition.evaluate((Double) state_value)) {
                    possible_task = true;
                } else {
                    possible_task = false;
                }
            }
            // If all conditions are satisfied, then add this task to the valid_tasks array
            if (possible_task) {
                valid_tasks.add(current_task);
            }
        }
        return valid_tasks;
    }

    private static ArrayList<StateTemplate> templateGeneration(SystemState state, ArrayList<Task> tasks){
        // TODO: Create templates for every task
        return null;
    }

    public static void addNeighbors(SystemState state) {
        ArrayList<Task> tasks = validTasks(state, taskDict);
        ArrayList<StateTemplate> templates = templateGeneration(state, tasks);
        // TODO: Enqueue to frontier
    }

    private static ArrayList<Property> mergeProperties(ArrayList<Property> previousProperties,
                                                ArrayList<Property> nextProperties) {
        HashMap<String, Property> affectedProperties = new HashMap<>();

        for (Property property : previousProperties) {
            String name = property.getName();
            affectedProperties.put(name, property);
        }

        for (Property property: nextProperties) {
            String propertyName = property.getName();

            if (affectedProperties.containsKey(propertyName)) {
                if (property instanceof NumericalProperty) {
                    NumericalProperty oldProperty = (NumericalProperty) affectedProperties.get(propertyName);

                    Double value = oldProperty.getValue() + ((NumericalProperty) property).getValue();
                    Property updatedProperty = new NumericalProperty(propertyName, value);
                    affectedProperties.replace(propertyName, updatedProperty);
                } else {
                    affectedProperties.replace(propertyName, property);
                }
            } else {
                affectedProperties.put(propertyName, property);
            }
        }

        ArrayList<Property> finalProperties = new ArrayList<>();

        for (String propertyName: affectedProperties.keySet()) {
            finalProperties.add(affectedProperties.get(propertyName));
        }

        return finalProperties;
    }

    private static SystemState createState(SystemState previousState, Task task) {
        ArrayList<Property> properties = previousState.getProperties();
        ArrayList<Property> taskProperties = task.getProperty_impacts();

        ArrayList<Property> mergedProperties = GraphManager.mergeProperties(properties, taskProperties);

        // TODO: Change UID generation
        long previousTime = previousState.getTimeStamp().getTime();
        long nextTime = previousTime + task.getDuration();

        SystemState nextState = new SystemState(previousState.getUID() + 1, nextTime);

        for (Property property: mergedProperties) {
            nextState.addProperty(property);
        }

        return nextState;
    }

    public static SystemState poll() {
        // TODO: Dequeue best template from frontier or Null if frontier is empty
        // TODO: return createState(template.previousState, template.task)
        return null;
    }
}
