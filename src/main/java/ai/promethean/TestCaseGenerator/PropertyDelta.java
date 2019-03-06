package ai.promethean.TestCaseGenerator;

public abstract class PropertyDelta {
    protected String property_name;

    public PropertyDelta() {}

    public PropertyDelta(String _name) {
        setName(_name);
    }

    public void setName(String _name) {
        this.property_name = _name;
    }

}
