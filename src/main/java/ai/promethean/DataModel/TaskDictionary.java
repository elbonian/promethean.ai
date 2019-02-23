package ai.promethean.DataModel;
import java.util.*;
public class TaskDictionary {
    private Map<String, Task> TaskDictionary = new HashMap<String, Task>();

    public Map<String, Task> getTaskDictionary() {
        return TaskDictionary;
    }

    public void addTask(Task t){
        TaskDictionary.put(t.getName(), t);
    }

    public void removeTask(Task t){
        TaskDictionary.remove(t.getName());
    }
    public void removeTask(String name){
        TaskDictionary.remove(name);
    }

    public Task findTask(Task t){ return TaskDictionary.get(t.getUID()); }
    public Task findTask(String name){ return TaskDictionary.get(name); }

    public Task getTask(String name) { return TaskDictionary.get(name); }

    public Set<String> getKeys() { return TaskDictionary.keySet(); }

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
