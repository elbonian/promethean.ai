package ai.promethean.DataModel;

public class StringProperty extends Property {
    protected String value;

    public StringProperty(String _name, String _value){
        super(_name);
        setValue(_value);
    }

    public void setValue(String _value){
        this.value=_value;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return "Property Name: "+ super.name + ", Property Value: " + this.value;
    }

    @Override
    public Boolean equals(Property p) {
        return p instanceof StringProperty && p.getName().equals(this.name) && ((StringProperty) p).getValue().equals(this.value);
    }
}
