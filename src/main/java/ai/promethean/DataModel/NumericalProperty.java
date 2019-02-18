package ai.promethean.DataModel;

public class NumericalProperty extends Property {
    protected Double value;

    public NumericalProperty(String _name, Double _value, boolean _isDelta){
        super(_name,_isDelta);
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
    public NumericalProperty applyPropertyImpactOnto(Property p){

        if(!p.name.equals(this.name)){
            throw new IllegalArgumentException("Property names do not match");
        }
        else if(this.isDelta){
            Double newVal= (Double)p.getValue()+this.value;
            return new NumericalProperty(this.name, newVal);
        }
        else {
            return new NumericalProperty(this.name, this.value);
        }
    }

    @Override
    public String toString(){
        String printOut= "Property Name: "+ super.name;
        if(super.isDelta)
            printOut= printOut + ", Type: Delta";
        else
            printOut= printOut + ", Type: Assignment";
        return printOut + ", Property Value: " + this.value;
    }

    @Override
    public Boolean equals(Property p) {
        return p instanceof NumericalProperty && p.getName().equals(this.name) && ((NumericalProperty) p).getValue().equals(this.value);
    }
}
