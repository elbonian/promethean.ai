package ai.promethean.DataModel;


public abstract class Property {
    protected String name;
    protected String type;
    public Property(){
        setName("");
        setType("assignment");
    }

    public Property(String _name){
        setName(_name);
        setType("assignment");
    }
    public Property(String _name, String _type){
        setName(_name);
        setType(_type);
    }
    public void setName(String _name){
        this.name=_name;
    }

    public String getName(){
        return this.name;
    }

    public String getType() {
        return type;
    }

    public void setType(String _type) {
        type=_type;
    }

    public abstract String toString();

    public abstract Boolean equals(Property p);

    public abstract Object getValue();

    public abstract Object applyPropertyImpactOnto(Property p);


}
