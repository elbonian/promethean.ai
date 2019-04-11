package ai.promethean.DataModel;

import ai.promethean.Logger.Logger;

/**
 * String extension of the Property class
 */
public class StringProperty extends Property {
    protected String value;

    /**
     * Instantiates a new String property.
     *
     * @param _name  The name
     * @param _value The value
     * @param _type  The property type, used in property_impacts. Either "assignment" or "delta"
     */
    public StringProperty(String _name, String _value, String _type){
        super(_name,_type);

        setValue(_value);
    }

    /**
     * Instantiates a new String property without a _type argument. Used for SystemState properties
     *
     * @param _name  the name
     * @param _value the value
     */
    public StringProperty(String _name, String _value){
        super(_name);
        setValue(_value);
    }


    /**
     * Set the value of the Property
     *
     * @param _value The value
     */
    public void setValue(String _value){
        this.value=_value;
    }

    @Override
    public String getValue(){
        return this.value;
    }

    /* Applies the impacts of this property object onto a given property p
    *  Returns: New property with the assignment applied
    */
    public StringProperty applyImpact(Property impact){
        if(!impact.name.equals(this.name)){
            IllegalArgumentException e = new IllegalArgumentException("Property names do not match");
            Logger.logError(e, this.getClass().getSimpleName());
            throw e;
        }
        return new StringProperty(this.name, (String) impact.getValue());
    }

    @Override
    public String toString(){
        return "Property Name: "+ super.name+", Type: "+ super.type+ ", Value: " + this.value;

    }

    @Override
    public Boolean equals(Property p) {
        return p instanceof StringProperty && p.getName().equals(this.name) && ((StringProperty) p).getValue().equals(this.value);
    }
}
