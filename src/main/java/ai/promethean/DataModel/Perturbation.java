package ai.promethean.DataModel;

import java.sql.Time;
import java.util.*;


public class Perturbation {
    private Time timeStamp;
    private ArrayList<Property> property_impacts = new ArrayList<>();

    public Perturbation(){
        timeStamp= new Time(System.currentTimeMillis());
    }
    public Perturbation(long time){
        timeStamp= new Time(time);
    }

    public Time getTimeStamp() {
        return timeStamp;
    }

    public ArrayList<Property> getPropertyImpacts() {
        return property_impacts;
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

    @Override
    public String toString() {
        return "Pertubation Timestamp: " + this.timeStamp
                + "\n Property Changes: " + property_impacts;
    }
}
