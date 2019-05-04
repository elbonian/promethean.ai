package ai.promethean.DataModel;

/**
 * Numerical extension of the Condition class
 */
public class NumericalCondition extends Condition {
    protected Double value;

    /**
     * Instantiates a new Numerical condition.
     *
     * @param _name     The name of the condition
     * @param _operator The operator used to compare the value
     * @param _value    The value of the condition
     */
    public NumericalCondition(String _name, String _operator, Double _value) {
        super(_name, _operator);
        setValue(_value);
    }

    /**
     * Sets the value of the Condition
     *
     * @param value The value to be set
     */
    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean evaluate(Object val1){
        if(val1 instanceof Double) {
            switch (this.operator){
                case("=="): return val1.equals(this.value);
                case("!="): return !val1.equals(this.value);
                case(">"): return (Double)val1 > value;
                case("<"): return (Double)val1 < value;
                case("<="): return (Double)val1 <= value;
                case(">="): return (Double)val1 >= value;
                default: return false;
            }
        }else return false;

    }

    @Override
    public String toString(){
        return "Requirement: " + super.name +" " +super.operator+ " " + this.value;
    }
}
