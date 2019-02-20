package ai.promethean.DataModel;

public class StringProperty extends Property {
    protected String value;

    public StringProperty(String _name, String _value, boolean _isDelta){
        super(_name,_isDelta);
        setValue(_value);
    }
    public StringProperty(String _name, String _value){
        super(_name);
        setValue(_value);
    }


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
    public StringProperty applyPropertyImpactOnto(Property p){
        if(!p.name.equals(this.name)){
            throw new IllegalArgumentException("Property names do not match");
        }
        return new StringProperty(this.name, this.value);
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
        return p instanceof StringProperty && p.getName().equals(this.name) && ((StringProperty) p).getValue().equals(this.value);
    }
}
