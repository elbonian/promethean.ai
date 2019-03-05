package ai.promethean.DataModel;

import java.util.*;

/**
 * Class defining the GoalState of the planning system
 */
public class GoalState {
    private ArrayList<Condition> requirements=new ArrayList<Condition>();

    /**
     * Gets the ArrayList of Condition objects which are the requirements to be in the goal state
     *
     * @return the requirements
     */
    public ArrayList<Condition> getRequirements() {
        return requirements;
    }

    /**
     * Retrieve a requirement given the name
     *
     * @param name The name of the Condition to find
     * @return A Condition object with the given name, or Null if a Condition with that name is not found
     */
    public Condition getRequirement(String name){
        for(Condition c: requirements){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    /**
     * Add a NumericalCondition requirement to the GoalState, given the String name and operator and Double value
     *
     * @param name     The name of the requirement
     * @param value    The value
     * @param operator The operator
     */
    public void addRequirement(String name, Double value, String operator){
        NumericalCondition c= new NumericalCondition(name, operator,value);
        requirements.add(c);
    }

    /**
     * Add a BooleanCondition requirement to the GoalState, given the String name and operator and Boolean value
     *
     * @param name     The name
     * @param value    The value
     * @param operator The operator
     */
    public void addRequirement(String name, Boolean value, String operator){
        BooleanCondition c= new BooleanCondition(name, operator,value);
        requirements.add(c);
    }

    /**
     * Add a StringCondition requirement to the GoalState, given the String name, value, and operator
     *
     * @param name     The name
     * @param value    The value
     * @param operator The operator
     */
    public void addRequirement(String name, String value, String operator){
        StringCondition c= new StringCondition(name, operator,value);
        requirements.add(c);
    }

    /**
     * Add any Type Condition requirement to the GoalState, given a fully constructed Condition object
     *
     * @param c The fully constructed Condition object
     */
    public void addRequirement(Condition c){
        requirements.add(c);
    }

    /**
     * Checks to see if an input SystemState matches the current GoalState
     *
     * @param s The SystemState to compare to this GoalState
     * @return A Boolean whether the given SystemState matches this GoalState's conditions
     */
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
