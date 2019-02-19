package ai.promethean.DataModel;
import java.util.*;
public class TaskDictionary {
    private Map<Integer, Task> TaskDictionary = new HashMap<Integer, Task>();

    public Map<Integer, Task> getTaskDictionary() {
        return TaskDictionary;
    }

    public void addTask(Task t){
        TaskDictionary.put(t.getUID(), t);
    }

    public void removeTask(Task t){
        TaskDictionary.remove(t.getUID());
    }

    public Task findTask(Task t){
        return TaskDictionary.get(t.getUID());
    }

    public Task getTask(Integer i) { return TaskDictionary.get(i); }

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
