package ai.promethean.TestCaseGenerator;

public class NumericalDelta extends PropertyDelta {
    protected Integer delta;

    public NumericalDelta(String _name, Integer _delta) {
        super.setName(_name);
        setValue(_delta);
    }

    public void setValue(Integer delta) {
        this.delta = delta;
    }
}
