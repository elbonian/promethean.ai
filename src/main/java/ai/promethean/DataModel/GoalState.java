package ai.promethean.DataModel;

import java.util.*;

public class GoalState {
    private ArrayList<Condition> requirements=new ArrayList<Condition>();

    public ArrayList<Condition> getRequirements() {
        return requirements;
    }

    public Condition getRequirement(String name){
        for(Condition c: requirements){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

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
    public void addRequirement(Condition c){
        requirements.add(c);
    }

    public boolean meetsGoal(SystemState s){
        for(Condition c: requirements){
            if(s.getProperty(c.getName())==null) return false;
            else if(!c.evaluate(s.getProperty(c.getName()).getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Goal State: "
                + "\n Requirements: " + requirements;
    }
}
