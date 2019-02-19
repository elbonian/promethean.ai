package ai.promethean.DataModel;


public abstract class Property {
    protected String name;
    protected boolean isDelta;
    public Property(){
        setName("");
        setDelta(false);
    }

    public Property(String _name){
        setName(_name);
        setDelta(false);
    }
    public Property(String _name, boolean _delta){
        setName(_name);
        setDelta(_delta);
    }
    public void setName(String _name){
        this.name=_name;
    }

    public String getName(){
        return this.name;
    }

    public boolean getIsDelta() {
        return isDelta;
    }

    public void setDelta(boolean delta) {
        isDelta = delta;
    }

    public abstract String toString();

    public abstract Boolean equals(Property p);

    public abstract Object getValue();

    public abstract Object applyPropertyImpactOnto(Property p);


}
