package ai.promethean.DataModel;
import java.sql.Time;
import java.util.*;

public class SystemState {
    private int UID;
    private Time timeStamp;
    private PropertyMap properties = new PropertyMap();

    //Members for graph-search in planning
    private SystemState previousState;
    private Task previousTask;
    private Double gValue = 0.0;

    public SystemState(int _UID){
        setUID(_UID);
        timeStamp= new Time(System.currentTimeMillis());
    }

    public SystemState(int _UID, long time){
        setUID(_UID);
        timeStamp= new Time(time);
    }

    public SystemState(int _UID, boolean isGoal, Double gVal){
        setUID(_UID);
        timeStamp= new Time(System.currentTimeMillis());
        if(!isGoal){
            setgValue(0.0);
        }
    }

    public SystemState(int _UID, long time, boolean isGoal, Double gVal){
        setUID(_UID);
        timeStamp= new Time(time);
        if(!isGoal){
            setgValue(0.0);
        }
    }

    public SystemState(int _UID, Double gValue){
        setUID(_UID);
        timeStamp= new Time(System.currentTimeMillis());
        setgValue(gValue);
    }

    public SystemState(int _UID, long time, Double gVal){
        setUID(_UID);
        timeStamp= new Time(time);
        setgValue(gVal);
    }

    public void setUID(int _UID){
        this.UID=_UID;
    }

    public int getUID(){
        return UID;
    }

    public Time getTimeStamp(){
        return timeStamp;
    }

    public void setgValue(Double gValue) {
        this.gValue = gValue;
    }

    public Double getgValue() {
        return gValue;
    }

    public ArrayList<Property> getProperties() {
        ArrayList<Property> property_list = new ArrayList<>();
        for (String key : properties.getKeys()) {
            property_list.add(properties.getProperty(key));
        }
        return property_list;
    }


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

    public PropertyMap getPropertyMap() { return properties; }

    public void addProperty(String name, Boolean value) {
        properties.addProperty(name, value);
    }

    public void addProperty(String name, Double value) {
        properties.addProperty(name, value);
    }

    public void addProperty(String name, String value) {
        properties.addProperty(name, value);
    }

    public void addProperty(Property property) {
        properties.addProperty(property);
    }

    public Property getProperty(String name) {
        return properties.getProperty(name);
    }

    /*
    //TODO needs more testing
    public Boolean equals(SystemState systemState){
        this.sortProperties();
        this.sortResources();
        systemState.sortProperties();
        systemState.sortResources();

        return properties.equals(systemState.getProperties())&& resources.equals(systemState.getResources());
    }
    */

    // public void sortProperties(){ Collections.sort(properties, new SortbyProperty()); }

    @Override
    public String toString() {
        return "System State UID: " + this.UID + "\n Timestamp: " + this.timeStamp
                + "\n G-Value: " + gValue
                + "\n Properties: " + properties;
    }
}