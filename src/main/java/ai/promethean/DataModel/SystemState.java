package ai.promethean.DataModel;
import java.util.*;
import java.util.concurrent.atomic.*;

public class SystemState {
    private int UID;
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private int time;
    private PropertyMap properties = new PropertyMap();

    //Members for graph-search in planning
    private SystemState previousState;
    private Task previousTask;
    private double gValue=0.0;

    public SystemState(){
        setUID();
    }

    public SystemState(int time){
        setUID();
        setTime(time);
    }

    public void setTime(int time) {
        this.time = time;
    }

    private void setUID(){

        this.UID=ID_GENERATOR.getAndIncrement();
    }
    public int getUID(){
        return UID;
    }

    public int getTime(){
        return time;
    }

    public void setgValue(double gValue) {
        this.gValue = gValue;
    }

    public double getgValue() {
        return gValue;
    }



    public ArrayList<Property> getProperties() {
        ArrayList<Property> property_list = new ArrayList<>();
        for (String key : properties.getKeys()) {
            property_list.add(properties.getProperty(key));
        }
        return property_list;
    }

    public PropertyMap getPropertyMap() { return properties; }

    public void setPreviousState(SystemState previousState) {
        this.previousState = previousState;
    }

    public SystemState getPreviousState() {

        return previousState;
    }

    public void setPreviousTask(Task previousTask) {
        this.previousTask = previousTask;
    }

    public Task getPreviousTask() {
        return previousTask;
    }

    public void addProperty(String name, Boolean value, boolean isDelta) {
        properties.addProperty(name, value, isDelta);
    }

    public void addProperty(String name, Boolean value) {
        properties.addProperty(name, value);
    }

    public void addProperty(String name, Double value, boolean isDelta) {
        properties.addProperty(name, value,isDelta);
    }

    public void addProperty(String name, Double value) {
        properties.addProperty(name, value);
    }
    public void addProperty(String name, String value, boolean isDelta) {
        properties.addProperty(name, value, isDelta);
    }
    public void addProperty(String name, String value) {
        properties.addProperty(name, value);
    }

    public void addProperty(Property p) {
        properties.addProperty(p);
    }

    public Property getProperty(String name) {
        return properties.getProperty(name);
    }

/*
    //TODO needs more testing
    public Boolean equals(SystemState systemState){
        this.sortProperties();
        systemState.sortProperties();
        return properties.equals(systemState.getProperties());
    }

*/




    @Override
    public String toString() {
        return "System State UID: " + this.UID + "\n Time: " + this.time
                + "\n G-Value: " + gValue
                + "\n Properties: " + properties;
    }
}
