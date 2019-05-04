package ai.promethean.DataModel;
import java.util.*;

/**
 * A Map of (UID, Task object) pairs to quickly access Tasks
 */
public class TaskDictionary {
    private Map<String, Task> TaskDictionary = new HashMap<String, Task>();

    /**
     * Gets the entire task dictionary
     *
     * @return The task dictionary
     */
    public Map<String, Task> getTaskDictionary() {
        return TaskDictionary;
    }

    /**
     * Add a fully formed Task to the Map
     *
     * @param t The Task object to add to the TaskDictionary
     */
    public void addTask(Task t){
        TaskDictionary.put(t.getName(), t);
    }

    /**
     * Remove a given task from the TaskDictionary
     *
     * @param t The Task to remove
     */
    public void removeTask(Task t){
        TaskDictionary.remove(t.getName());
    }
    public void removeTask(String name){
        TaskDictionary.remove(name);
    }

    /**
     * Find a Task in the dictionary given the Task itself
     *
     * @param t The Task to retrieve the UID of
     * @return The Task which was found, Null otherwise
     */
    public Task findTask(Task t){ return TaskDictionary.get(t.getUID()); }

    /**
     * Find a Task in the dictionary given a Task name
     *
     * @param name The name of the Task to search for
     * @return The Task which was found, Null otherwise
     */
    public Task findTask(String name){ return TaskDictionary.get(name); }

    /**
     * Gets a Task from the dictionary given a Task name
     *
     * @param name The name of the Task to search for
     * @return The Task found with the given name, Null otherwise
     */
    public Task getTask(String name) { return TaskDictionary.get(name); }

    /**
     * Gets a Set of Strings of all the keys in the TaskDictionary
     *
     * @return Set of Strings of Task names in the dictionary
     */
    public Set<String> getKeys() { return TaskDictionary.keySet(); }

    /**
     * Gets the number of elements in the TaskDictionary
     *
     * @return Integer of the number of Tasks
     */
    public int size() { return TaskDictionary.size(); }

    @Override
    public String toString() {
        String printOut= "Task Dictionary: \n";
        for(Task t: TaskDictionary.values()){
            printOut=printOut+ t + "\n";
        }
        return printOut;
    }
}
