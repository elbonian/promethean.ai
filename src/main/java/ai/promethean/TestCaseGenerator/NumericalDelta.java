package ai.promethean.TestCaseGenerator;

/**
 * The type Numerical delta.
 */
public class NumericalDelta extends PropertyDelta {
    protected Double delta;
    protected String operator;


    /**
     * Instantiates a new NumericalDelta for deltas which change a Double value
     *
     * @param _name  The name of the delta
     * @param _delta The change in value between start and goal states
     */
    public NumericalDelta(String _name, Double _delta) {
        super.setName(_name);
        setValue(_delta);
    }

    /**
     * Special NumericalDelta instance for battery/fuel deltas
     *
     * @param _name The name of the delta
     * @param _data The change in value between start and goal states
     * @param _operator The operator to show how much of a value change the delta should have
     */
    public NumericalDelta(String _name, Double _data, String _operator) {
        super.setName(_name);
        setValue(_data);
        setOperator(_operator);
    }

    public void setValue(Double delta) {
        this.delta = delta;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getValue() {
        return delta;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "Name: " + this.property_name + ", Delta Value: " + this.delta;
    }

}
