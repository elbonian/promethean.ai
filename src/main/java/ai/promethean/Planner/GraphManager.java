package ai.promethean.Planner;

import ai.promethean.DataModel.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GraphManager {

    private SystemState initState;
    private GoalState goalState;
    private TaskDictionary taskDict;
    private StaticOptimizations optimizations;
    private PriorityQueue<StateTemplate> frontier = new PriorityQueue<StateTemplate>(1, new FrontierComparator());

    public GraphManager(SystemState initState, GoalState goalState, TaskDictionary taskDict, StaticOptimizations optimizations) {
        this.initState = initState;
        this.goalState = goalState;
        this.taskDict = taskDict;
        this.optimizations = optimizations;
    }

    public boolean frontierIsEmpty() {
        return frontier.peek() == null;
    }

    /*
     * returns list of tasks that can be executed at a given SystemState
     */
    private ArrayList<Task> validTasks(SystemState state) {
        ArrayList<Task> validTasks = new ArrayList<>();

        for (String name : taskDict.getKeys()) {
            boolean valid = true;
            Task task = taskDict.getTask(name);

            for (Condition condition : task.getRequirements()) {
                if (!condition.evaluate(state.getProperty(condition.getName()).getValue())) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                validTasks.add(task);
            }
        }

        return validTasks;
    }

    /*
     * returns a SystemState created by applying task's property impacts to previous state
     */
    private SystemState createState(SystemState previousState, Task task, Double g_value) {
        ArrayList<Property> impacts = task.getProperty_impacts();
        PropertyMap prev_properties = previousState.getPropertyMap();

        SystemState nextState = new SystemState(previousState.getTime() + task.getDuration());
        nextState.setgValue(g_value);
        nextState.setPreviousState(previousState);
        nextState.setPreviousTask(task);

        for (Property property : impacts) {
            String propertyName = property.getName();
            Property oldProperty = prev_properties.getProperty(propertyName);
            nextState.getPropertyMap().addProperty(property.applyPropertyImpactOnto(oldProperty));
        }

        for (String propertyName : prev_properties.getKeys()) {
            if (!nextState.getPropertyMap().containsProperty(propertyName)) {
                nextState.getPropertyMap().addProperty(prev_properties.getProperty(propertyName));
            }
        }

        return nextState;
    }

    /*
     * Returns list of state templates given previous state and valid tasks
     */
    private ArrayList<StateTemplate> templateGeneration(SystemState state, ArrayList<Task> tasks) {
        ArrayList<StateTemplate> templates = new ArrayList<>();

        for (Task task : tasks) {
            Double g_value = Heuristic.g_value(state, task, optimizations);
            Double f_value = Heuristic.f_value(state, goalState, g_value);
            templates.add(new StateTemplate(state, task, f_value, g_value));
        }

        return templates;
    }

    public void addNeighborsToFrontier(SystemState state) {
        frontier.addAll(templateGeneration(state, validTasks(state)));
    }

    public SystemState poll() {
        StateTemplate template = frontier.poll();
        return createState(template.getPreviousState(), template.getTask(), template.getG());
    }
}
