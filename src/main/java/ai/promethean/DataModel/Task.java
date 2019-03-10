package ai.promethean.DataModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Defined a Task which can be executed from a SystemState to transition to a different state
 */
public class Task {
    private int UID;
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private int duration;
    private String name;

    private PropertyMap property_impacts=new PropertyMap();
    private ArrayList<ai.promethean.DataModel.Condition> requirements=new ArrayList<>();

    /**
     * Instantiates a new Task, with a defined duration and no name
     *
     * @param _duration The duration of the Task
     */
    public Task( int _duration){
        setUID();
        setDuration(_duration);
    }

    /**
     * Instantiates a new Task with a given name and duration
     *
     * @param _duration The duration
     * @param _name     The name
     */
    public Task(int _duration, String _name){
        setUID();
        setDuration(_duration);
        setName(_name);
    }

    /**
     * Sets the UID of the Task. Will automatically increment and assign properly
     */
    private void  setUID(){
        this.UID=ID_GENERATOR.getAndIncrement();
    }

    /**
     * Get the UID of the Task
     *
     * @return The UID
     */
    public int getUID(){
        return this.UID;
    }

    /**
     * Sets the duration of the Task
     *
     * @param _duration The duration
     */
    public void setDuration(int _duration){
        this.duration=_duration;
    }

    /**
     * Get the duration of the Task
     *
     * @return The duration
     */
    public int getDuration(){
        return this.duration;
    }

    /**
     * Gets the name of the Task
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Task
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets property impacts.
     *
     * @return A PropertyMap of all properties in the Task
     */
    public PropertyMap getProperty_impacts() {
        return property_impacts;
    }

    /**
     * Gets an ArrayList of the Property objects in the Task
     *
     * @return ArrayList of Property objects
     */
    public ArrayList<Property> getProperties() {
        ArrayList<Property> property_list = new ArrayList<>();
        for (String key : property_impacts.getKeys()) {
            property_list.add(property_impacts.getProperty(key));
        }
        return property_list;
    }

    /**
     * Get requirements for the Task to be able to be executed
     *
     * @return An ArrayList of Condition objects which are required to be satisfied for the Task to be executed
     */
    public ArrayList<Condition> getRequirements(){
        return requirements;
    }

    /**
     * Get a Property from the property_impacts given a name
     *
     * @param name The name of the Property to search for
     * @return The Property object found, or Null if no Property was found with the given name
     */
    public Property getProperty(String name){
        return this.property_impacts.getProperty(name);
    }

    /**
     * Get a Condition object from the requirements given a name
     *
     * @param name The name of the Condition to search for
     * @return The Condition object found, or Null if no Condition was found with the given name
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
     * Add a fully formed Property to the property_impacts Map
     *
     * @param p The valid Property object to ass to property_impacts
     */
    public void addProperty(Property p){
        property_impacts.addProperty(p);
    }

    /**
     * Add a StringProperty to the property_impacts map
     *
     * @param name  The name
     * @param value The value
     * @param type  The type
     */
    public void addProperty(String name, Boolean value, String type){
        property_impacts.addProperty(name, value, type);
    }

    /**
     * Add a NumericalProperty to the property_impacts map
     *
     * @param name  The name
     * @param value The value
     * @param type  The type
     */
    public void addProperty(String name, Double value, String type){
        property_impacts.addProperty(name, value, type);
    }

    /**
     * Add a StringProperty to the property_impacts map
     *
     * @param name  The name
     * @param value The value
     * @param type  The type
     */
    public void addProperty(String name, String value, String type){
        property_impacts.addProperty(name, value, type);
    }

    /**
     * Add a fully formed Condition object to the requirements list
     *
     * @param c The valid Condition object to add to the requirements list
     */
    public void addRequirement(Condition c){ requirements.add(c);}

    /**
     * Add a NumericalCondition object to the requirements list
     *
     * @param name     The name
     * @param value    The value
     * @param operator The operator
     */
    public void addRequirement(String name, Double value, String operator){
        NumericalCondition c= new NumericalCondition(name, operator,value);
        requirements.add(c);
    }

    /**
     * Add a BooleanCondition object to the requirements list
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
     * Add a StringCondition object to the requirements list
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
     * Applies the task's property impacts onto a state and returns the resulting new state
     * @param previousState The original system state
     * @return a new System state with the task property impacts applied to the original state
     */
    public SystemState applyTask(SystemState previousState){
        SystemState newState= new SystemState();
        PropertyMap prevProperties = previousState.getPropertyMap();
        for(String propertyName: prevProperties.getKeys()){
            Property previousProperty = previousState.getProperty(propertyName);
            Property impact = this.getProperty(propertyName);
            if(impact!=null){
                Property newProperty = previousProperty.applyImpact(impact);
                newState.addProperty(newProperty);
            }
            else{
                newState.addProperty(previousProperty);
            }
        }
        return newState;
    }

    @Override
    public String toString() {
        return "Task UID: " + this.UID + ", Name: "+ this.name+ "\n Duration: " + this.duration
                + "\n Requirements: " + requirements
                + "\n Properties: " + property_impacts;
    }

}
