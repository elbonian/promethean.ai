package ai.promethean.DataModel;

import java.util.*;


public class Perturbation {
    private int time;
    private String name;
    private ArrayList<Property> property_impacts= new ArrayList<Property>();

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


    public void addProperty(String name, Boolean value, String type){
        property_impacts.add(new BooleanProperty(name, value,type));
    }

    public void addProperty(String name, Double value, String type){
        property_impacts.add(new NumericalProperty(name, value,type));
    }

    public void addProperty(String name, String value, String type){
        property_impacts.add(new StringProperty(name, value,type));
    }

    public void sortProperties(){
        Collections.sort(property_impacts, new SortbyProperty());
    }

    @Override
    public String toString() {
        return "Perturbation Name: "+this.name+ ", Time: " + this.time
                + "\n Property Changes: " + property_impacts + "\n";
    }
}
