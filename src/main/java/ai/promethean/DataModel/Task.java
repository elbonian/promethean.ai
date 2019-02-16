package ai.promethean.DataModel;

import java.util.*;

import ai.promethean.DataModel.Condition;
import ai.promethean.Planner.OptimizationWeightMap;


public class Task {
    private int UID;
    private int duration;

    private ArrayList<Property> property_impacts= new ArrayList<>();
    private ArrayList<ai.promethean.DataModel.Condition> requirements=new ArrayList<>();

    public Task(int _UID, int _duration){
        setUID(_UID);
        setDuration(_duration);
    }

    public void setUID(int _UID){
        this.UID=_UID;
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

    public void addProperty(String name, Boolean value){
        property_impacts.add(new BooleanProperty(name, value));
    }

    public void addProperty(String name, Double value){
        property_impacts.add(new NumericalProperty(name, value));
    }

    public void addProperty(String name, String value){
        property_impacts.add(new StringProperty(name, value));
    }

    public void addRequirement(Condition c){ requirements.add(c);}

    public Double calculateTaskWeight(OptimizationWeightMap map) {
        Double squaredSum = this.duration * map.getOptimizationWeightMap().get("Duration");
        // Translate the
        ArrayList<String> propertyNames = this.propertyMap.getKeys();
        for (String propertyName: propertyNames) {
            Property property = this.propertyMap.getProperty(propertyName);
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
