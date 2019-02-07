package ai.promethean.DataModel;
import java.util.*;
import ai.promethean.DataModel.Condition;

public class Task {
    private int UID;
    private int duration;

    private ArrayList<Resource> resource_impacts=new ArrayList<Resource>();
    private ArrayList<Property> property_impacts=new ArrayList<Property>();
    private ArrayList<Condition> requirements=new ArrayList<Condition>();

    public Task(int _UID, int _duration){
        setUID(_UID);
        setDuation(_duration);
    }

    public void  setUID(int _UID){
        this.UID=_UID;
    }

    public int getUID(){
        return this.UID;
    }

    public void setDuation(int _duration){
        this.duration=_duration;
    }

    public int getDuration(){
        return this.duration;
    }

    public ArrayList<Resource> getResource_impacts() {
        return resource_impacts;
    }

    public ArrayList<Property> getProperty_impacts() {
        return property_impacts;
    }
    public ArrayList<Condition> getRequirements(){
        return requirements;
    }

    public void addResource(Resource r){
        resource_impacts.add(r);
    }

    public void addResource(String name, Double value){resource_impacts.add(new Resource(name,value));
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

    public void addRequirement(Condition c){ requirements.add(c);}

    public void addRequirement(String name, Double value, String operator){
        Condition c= new Condition(name, value, operator);
        requirements.add(c);
    }

    @Override
    public String toString() {
        return "Task UID: " + this.UID + "\n Duration: " + this.duration
                + "\n Requirements: " + requirements
                + "\n Properties: " + property_impacts
                + "\n Resources: " + resource_impacts;
    }
}
