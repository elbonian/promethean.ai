package ai.promethean.TestCaseGenerator;

public class BooleanDelta extends PropertyDelta {
    protected Boolean delta;

    public BooleanDelta(String _name, Boolean _delta) {
        super.setName(_name);
        setValue(_delta);
    }

    public void setValue(Boolean delta) {
        this.delta = delta;
    }

    public Boolean getValue() {
        return delta;
    }

    @Override
    public String toString() {
        return "Name: " + this.property_name + ", Delta Value: " + this.delta;
    }
}
