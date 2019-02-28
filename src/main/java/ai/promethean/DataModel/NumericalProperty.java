package ai.promethean.DataModel;

public class NumericalProperty extends Property {
    protected Double value;


    public NumericalProperty(String _name, Double _value, String _type){
        super(_name,_type);

        setValue(_value);
    }
    public NumericalProperty(String _name, Double _value){
        super(_name);
        setValue(_value);
    }
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
