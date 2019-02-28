package ai.promethean.DataModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import ai.promethean.Planner.OptimizationWeight;

public class Task {
    private int UID;
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private int duration;
    private String name;

    private PropertyMap property_impacts=new PropertyMap();
    private ArrayList<Condition> requirements=new ArrayList<Condition>();

    public Task( int _duration){
        setUID();
        setDuration(_duration);
    }

    public Task(int _duration, String _name){
        setUID();
        setDuration(_duration);
        setName(_name);
    }

    private void  setUID(){
        this.UID=ID_GENERATOR.getAndIncrement();
    }

    public int getUID(){
        return this.UID;
    }

    public void setDuration(int _duration){
        this.duration=_duration;
    }

    public int getDuration(){
        return this.duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyMap getProperty_impacts() {
        return property_impacts;
    }

    public ArrayList<Property> getProperties() {
        ArrayList<Property> property_list = new ArrayList<>();
        for (String key : property_impacts.getKeys()) {
            property_list.add(property_impacts.getProperty(key));
        }
        return property_list;
    }

    public ArrayList<Condition> getRequirements(){
        return requirements;
    }

    public Property getProperty(String name){
        return this.property_impacts.getProperty(name);
    }

    public Condition getRequirement(String name){
        for(Condition c: requirements){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    public void addProperty(Property p){
        property_impacts.addProperty(p);
    }

    public void addProperty(String name, Boolean value, String type){
        property_impacts.addProperty(name, value, type);
    }

    public void addProperty(String name, Double value, String type){
        property_impacts.addProperty(name, value, type);
    }

    public void addProperty(String name, String value, String type){
        property_impacts.addProperty(name, value, type);
    }

    public void addRequirement(Condition c){ requirements.add(c);}

    public void addRequirement(String name, Double value, String operator){
        NumericalCondition c= new NumericalCondition(name, operator,value);
        requirements.add(c);
    }
    public void addRequirement(String name, Boolean value, String operator){
        BooleanCondition c= new BooleanCondition(name, operator,value);
        requirements.add(c);
    }
    public void addRequirement(String name, String value, String operator){
        StringCondition c= new StringCondition(name, operator,value);
        requirements.add(c);
    }

    @Override
    public String toString() {
        return "Task UID: " + this.UID + ", Name: "+ this.name+ "\n Duration: " + this.duration
                + "\n Requirements: " + requirements
                + "\n Properties: " + property_impacts;
    }

}
