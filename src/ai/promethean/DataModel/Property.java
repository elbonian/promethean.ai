package ai.promethean.DataModel;

public abstract class Property {
    protected String name;

    public Property(){
        setName("");
    }
    public Property(String _name){
        setName(_name);
    }

    public void setName(String _name){
        this.name=_name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return "Property Name: " + this.name ;
    }
}
