package ai.promethean.DataModel;

/**
 * Numerical extension of the Property class
 */
public class NumericalProperty extends Property {
    protected Double value;


    /**
     * Instantiates a new Numerical property to be used in property_impacts of a Task
     *
     * @param _name  The name of the property
     * @param _value The value of the property
         * @param _type  The property type, used in property_impacts. Either "assignment" or "delta"
     */
    public NumericalProperty(String _name, Double _value, String _type){
        super(_name,_type);

        setValue(_value);
    }

    /**
     * Instantiates a new Numerical property without a _type argument. Used for SystemState properties
     *
     * @param _name  The name
     * @param _value The value
     */
    public NumericalProperty(String _name, Double _value){
        super(_name);
        setValue(_value);
    }

    /**
     * Set the value of the Property
     *
     * @param _value the value
     */
    public void setValue(Double _value){
        this.value=_value;
    }

    @Override
    public Double getValue(){
        return this.value;
    }

    /* Applies the impacts of this property object onto a given property p
    * Returns: New property with the assignment/delta applied
     */
    public NumericalProperty applyImpact(Property impact){

        if(!impact.name.equals(this.name)){
            throw new IllegalArgumentException("Property names do not match");
        }

        else{
            switch (impact.getType()){
                case("assignment"): return new NumericalProperty(this.name, (Double) impact.getValue());
                case("delta"):
                    Double newVal= (Double)impact.getValue()+this.value;
                    return new NumericalProperty(this.name, newVal);
                default: throw new IllegalArgumentException("Property type is not valid");
            }

        }
    }

    @Override
    public String toString(){

        return "Property Name: "+ super.name+", Type: "+ super.type+ ", Value: " + this.value;

    }

    @Override
    public Boolean equals(Property p) {
        return p instanceof NumericalProperty && p.getName().equals(this.name) && ((NumericalProperty) p).getValue().equals(this.value);
    }
}
