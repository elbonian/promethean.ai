package ai.promethean.DataModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import ai.promethean.Planner.OptimizationWeight;

public class Task {
    private int UID;
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private int duration;
    private String name;

    private ArrayList<Property> property_impacts=new ArrayList<Property>();
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

    public ArrayList<Property> getProperty_impacts() {
        return property_impacts;
    }

    public ArrayList<Condition> getRequirements(){
        return requirements;
    }

    public Property getProperty(String name){
        for(Property p: property_impacts){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
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
        property_impacts.add(p);
    }

    public void addProperty(String name, Boolean value, String type){
        property_impacts.add(new BooleanProperty(name, value,type));
    }

    public void addProperty(String name, Double value, String type){
        property_impacts.add(new NumericalProperty(name, value,type));
    }

    public void addProperty(String name, String value, String type){
        property_impacts.add(new StringProperty(name, value,type));
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

    public Double calculateTaskWeight(StaticOptimizations optimizations) {
        Double squaredSum = 0.0;
        int optimizationsLength = optimizations.size();
        if(optimizations.getOptimization("Duration") != null) {
            squaredSum += OptimizationWeight.weightedPropertyValue(
                    optimizations.getOptimization("Duration"),
                    duration+0.0,
                    optimizationsLength);
        } else {
            squaredSum += this.duration;
        }
        for (Property property : this.property_impacts) {
            if (property instanceof NumericalProperty) {
                if (optimizations.getOptimization(property.getName()) != null) {
                    squaredSum += OptimizationWeight.weightedPropertyValue(
                            optimizations.getOptimization(property.getName()),
                            ((NumericalProperty)property).getValue(),
                            optimizationsLength);
                } else {
                    squaredSum += ((NumericalProperty) property).getValue();
                }
            } else {
                squaredSum += 1.0;
            }
        }
        return Math.sqrt(squaredSum);
    }

}
