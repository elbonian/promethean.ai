package ai.promethean.DataModel;

import java.util.*;


public class Perturbation {
    private int time;
    private String name;
    private PropertyMap property_impacts = new PropertyMap();

    public Perturbation(){
        setTime(0);
    }
    public Perturbation(String _name){
        setTime(0);
        setName(_name);

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyMap getProperties() {
        return property_impacts;
    }


    public Property getProperty(String name){
        return this.property_impacts.getProperty(name);
    }


    public void addProperty(Property p){
        property_impacts.addProperty(p);
    }


    public void addProperty(String name, Boolean value, String type){
        property_impacts.addProperty(name, value,type);
    }

    public void addProperty(String name, Double value, String type){
        property_impacts.addProperty(name, value,type);
    }

    public void addProperty(String name, String value, String type){
        property_impacts.addProperty(name, value,type);
    }

    @Override
    public String toString() {
        return "Perturbation Name: "+this.name+ ", Time: " + this.time
                + "\n Property Changes: " + property_impacts + "\n";
    }
}
