package ai.promethean.DataModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import ai.promethean.DataModel.Condition;
import ai.promethean.Planner.OptimizationWeightMap;

public class Task {
    private int UID;
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private int duration;

    private ArrayList<Property> property_impacts=new ArrayList<Property>();
    private ArrayList<Condition> requirements=new ArrayList<Condition>();

    public Task( int _duration){
        setUID();
        setDuration(_duration);
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

    public void addProperty(String name, Boolean value, boolean isDelta){
        property_impacts.add(new BooleanProperty(name, value,isDelta));
    }

    public void addProperty(String name, Double value, boolean isDelta){
        property_impacts.add(new NumericalProperty(name, value,isDelta));
    }

    public void addProperty(String name, String value, boolean isDelta){
        property_impacts.add(new StringProperty(name, value,isDelta));
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
        return "Task UID: " + this.UID + "\n Duration: " + this.duration
                + "\n Requirements: " + requirements
                + "\n Properties: " + property_impacts;
    }

    public Double calculateTaskWeight(OptimizationWeightMap map) {
        Double squaredSum = this.duration * map.getOptimizationWeightMap().get("Duration");
        // Translate the
        for (Property property : this.property_impacts) {
            if (property instanceof NumericalProperty) {
                if (map.getOptimizationWeightMap().get(property.getName()) != null) {
                    squaredSum += (Math.pow(((NumericalProperty) property).getValue(),2)) * map.getOptimizationWeightMap().get(property.getName());
                } else {
                    squaredSum += (Math.pow(((NumericalProperty) property).getValue(), 2));
                }
            } else {
                squaredSum += 1.0;
            }
        }
        return Math.sqrt(squaredSum);
    }

}
