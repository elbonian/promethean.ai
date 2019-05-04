package ai.promethean.DataModel;


import ai.promethean.Logger.Logger;

/**
 * Boolean extension of the Property class
 */
public class BooleanProperty extends Property {

    protected Boolean value;


    /**
     * Instantiates a new Boolean property to be used in property_impacts of a Task
     *
     * @param _name  The name of the property
     * @param _value The value of the property
     * @param _type  The property type, used in property_impacts. either "assignment" or "delta"
     */
    public BooleanProperty(String _name, Boolean _value, String _type){
        super(_name, _type);

        setValue(_value);
    }

    /**
     * Instantiates a new Boolean property without a _type argument. Used for SystemState properties
     *
     * @param _name  The name of the property
     * @param _value The value of the property
     */
    public BooleanProperty(String _name, Boolean _value){
        super(_name);
        setValue(_value);
    }

    /**
     * Set the value of the Property
     *
     * @param _value the value
     */
    public void setValue(Boolean _value){
        this.value=_value;
    }

    @Override
    public Boolean getValue(){
        return this.value;
    }

    /* Applies the impacts of this property object onto a given property p
   *  Returns: New property with the assignment applied
   */
    public BooleanProperty applyImpact(Property impact){
        if(!impact.name.equals(this.name)){
            IllegalArgumentException e = new IllegalArgumentException("Property names do not match");
            Logger.logError(e, this.getClass().getSimpleName());
            throw e;
        }
        return new BooleanProperty(this.name, (boolean) impact.getValue());
    }

    @Override
    public String toString(){
        return "Property Name: "+ super.name+", Type: "+ super.type+ ", Value: " + this.value;

    }

    @Override
    public Boolean equals(Property p) {
       return p instanceof BooleanProperty && p.getName().equals(this.name) && ((BooleanProperty) p).getValue().equals(this.value);
    }
}
