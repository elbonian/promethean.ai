package ai.promethean.DataModel;


public abstract class Property {
    protected String name;
    protected boolean isDelta;
    public Property(){
        setName("");
        setType(false);
    }

    public Property(String _name){
        setName(_name);
        setType(false);
    }
    public Property(String _name, boolean _delta){
        setName(_name);
        setType(_delta);
    }
    public void setName(String _name){
        this.name=_name;
    }

    public String getName(){
        return this.name;
    }

    public boolean getType() {
        return isDelta;
    }

    public void setType(boolean delta) {
        isDelta = delta;
    }

    public abstract String toString();

    public abstract Boolean equals(Property p);

    public abstract Object getValue();

    public abstract Object applyPropertyImpactOnto(Property p);


}
