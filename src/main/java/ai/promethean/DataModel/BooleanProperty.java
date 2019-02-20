package ai.promethean.DataModel;


public class BooleanProperty extends Property {
    protected Boolean value;

    public BooleanProperty(String _name, Boolean _value, boolean _isDelta){
        super(_name, _isDelta);
        setValue(_value);
    }
    public BooleanProperty(String _name, Boolean _value){
        super(_name);
        setValue(_value);
    }

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
    public BooleanProperty applyPropertyImpactOnto(Property p){
        if(!p.name.equals(this.name)){
            throw new IllegalArgumentException("Property names do not match");
        }
        return new BooleanProperty(this.name, this.value);
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
       return p instanceof BooleanProperty && p.getName().equals(this.name) && ((BooleanProperty) p).getValue().equals(this.value);
    }
}
