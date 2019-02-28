package ai.promethean.DataModel;

public class Optimization {
    private String name;
    private String type;
    private int priority;

    public Optimization(String _name, String type, int _priority){
        setName(_name);
        setType(type);
        setPriority(_priority);
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean equals(Optimization optimization){
        return optimization.getName().equals(this.name) && optimization.getType().equals(this.type);
    }

    @Override
    public String toString() {
        if(type == "min")
            return "Optimization Name: " +name + " Type: Minimize Priority: " + priority;
        else
            return "Optimization Name: " +name + " Type: Maximize Priority: " + priority;
    }
}
