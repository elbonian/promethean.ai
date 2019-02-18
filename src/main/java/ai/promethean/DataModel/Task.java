package ai.promethean.DataModel;
import java.util.*;
import ai.promethean.DataModel.Condition;

public class Task {
    private int UID;
    private int duration;

    private ArrayList<Property> property_impacts=new ArrayList<Property>();
    private ArrayList<Condition> requirements=new ArrayList<Condition>();

    public Task(int _UID, int _duration){
        setUID(_UID);
        setDuration(_duration);
    }

    public void  setUID(int _UID){
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
}
