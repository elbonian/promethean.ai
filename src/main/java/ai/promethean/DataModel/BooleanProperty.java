package ai.promethean.DataModel;

public class BooleanProperty extends Property {
    protected Boolean value;

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

    @Override
    public String toString(){
        return "Property Name: "+ super.name + ", Property Value: " + this.value;
    }

    @Override
    public Boolean equals(Property p) {
       return p instanceof BooleanProperty && p.getName().equals(this.name) && ((BooleanProperty) p).getValue().equals(this.value);
    }
}
