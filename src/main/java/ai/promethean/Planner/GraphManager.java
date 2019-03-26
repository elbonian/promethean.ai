package ai.promethean.Planner;

import ai.promethean.DataModel.*;

import java.util.ArrayList;
import java.util.Iterator;
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
    public ArrayList<Task> validTasks(SystemState state) {
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
    public SystemState createState(SystemState previousState, Task task, Double g_value, double h_value) {
        PropertyMap prev_properties = previousState.getPropertyMap();

        SystemState nextState = new SystemState(previousState.getTime() + task.getDuration(), h_value);
        nextState.setgValue(g_value);
        nextState.setPreviousState(previousState);
        nextState.setPreviousTask(task);

        for (String propertyName : prev_properties.getKeys()) {
            Property previousProperty = previousState.getProperty(propertyName);
            Property impact = task.getProperty(propertyName);
            if (impact != null) {
                Property newProperty = previousProperty.applyImpact(impact);
                nextState.addProperty(newProperty);
            } else {
                nextState.addProperty(previousProperty);
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
            Double g_value = TaskWeight.calculateTaskWeight(task, optimizations) + state.getgValue();
            double h_value = Heuristic.h_value(state,goalState);
            double f_value = g_value + h_value;
            templates.add(new StateTemplate(state, task, f_value, g_value, h_value));
        }

        return templates;
    }

    public void addNeighborsToFrontier(SystemState state) {
        frontier.addAll(templateGeneration(state, validTasks(state)));
    }

    public boolean checkCLF(SystemState currentState) {
        Iterator<StateTemplate> template = frontier.iterator();
        if(template.next().getH() < currentState.gethValue()) {
            return true;
        }
        while(template.hasNext()) {
            if(template.next().getH() < currentState.gethValue()) {
                return true;
            }
        }
        return false;
    }

    public SystemState poll() {
        StateTemplate template = frontier.poll();
        return createState(template.getPreviousState(), template.getTask(), template.getG(), template.getH());
    }
}
