package ai.promethean.DataModel;

public abstract class Property {
    protected String name;

    public Property(String _name){
        setName(_name);
    }

    public void setName(String _name){
        this.name=_name;
    }

    public String getName(){
        return this.name;
    }

    public abstract String toString();

    public abstract Boolean equals(Property p);
}
