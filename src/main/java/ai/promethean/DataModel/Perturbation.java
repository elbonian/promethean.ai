package ai.promethean.DataModel;

import java.sql.Time;
import java.util.*;


public class Perturbation {
    private Time timeStamp;
    private ArrayList<Resource> resources= new ArrayList<Resource>();
    private ArrayList<Property> properties= new ArrayList<Property>();

    public Perturbation(){
        timeStamp= new Time(System.currentTimeMillis());
    }
    public Perturbation(long time){
        timeStamp= new Time(time);
    }

    public Time getTimeStamp() {
        return timeStamp;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public Resource getResource(String name){
        for(Resource r: resources){
            if(r.getName().equals(name)){
                return r;
            }
        }
        return null;
    }

    public Property getProperty(String name){
        for(Property p: properties){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public void addResource(Resource r){
        resources.add(r);
    }

    public void addResource(String name, Double value){
        resources.add(new Resource(name,value));
    }

    public void addProperty(Property p){
        properties.add(p);
    }

    public void addProperty(String name, Boolean value){
        properties.add(new BooleanProperty(name, value));
    }

    public void addProperty(String name, Double value){
        properties.add(new NumericalProperty(name, value));
    }

    public void addProperty(String name, String value){
        properties.add(new StringProperty(name, value));
    }
    public void sortProperties(){
        Collections.sort(properties, new SortbyProperty());
    }

    public void sortResources(){
        Collections.sort(resources, new SortbyResource());
    }

    @Override
    public String toString() {
        return "Pertubation Timestamp: " + this.timeStamp
                + "\n Property Changes: " + properties
                + "\n Resource Changes: " + resources;
    }
}
