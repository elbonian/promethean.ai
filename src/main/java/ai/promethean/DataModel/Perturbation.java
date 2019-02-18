package ai.promethean.DataModel;

import java.util.*;


public class Perturbation {
    private int time;
    private ArrayList<Property> property_impacts= new ArrayList<Property>();

    public Perturbation(){
        setTime(0);
    }
    public Perturbation(int time){
        setTime(time);
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<Property> getProperties() {
        return property_impacts;
    }


    public Property getProperty(String name){
        for(Property p: property_impacts){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }


    public void addProperty(Property p){
        property_impacts.add(p);
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
    public void addProperty(String name, Boolean value, boolean isDelta){
        property_impacts.add(new BooleanProperty(name, value,isDelta));
    }

    public void addProperty(String name, Double value, boolean isDelta){
        property_impacts.add(new NumericalProperty(name, value,isDelta));
    }

    public void addProperty(String name, String value, boolean isDelta){
        property_impacts.add(new StringProperty(name, value,isDelta));
    }

    public void sortProperties(){
        Collections.sort(property_impacts, new SortbyProperty());
    }


    @Override
    public String toString() {
        return "Pertubation Time: " + this.time
                + "\n Property Changes: " + property_impacts;
    }
}
