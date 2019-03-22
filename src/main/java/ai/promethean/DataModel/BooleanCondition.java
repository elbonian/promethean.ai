package ai.promethean.DataModel;

/**
 * Boolean extension of the Condition class
 */
public class BooleanCondition extends Condition {

    protected Boolean value;

    /**
     * Instantiates a new Boolean condition.
     *
     * @param _name     The name of the condition
     * @param _operator The operator used to compare the value
     * @param _value    The value of the condition
     */
    public BooleanCondition(String _name, String _operator, Boolean _value){
        super(_name);
        if(Operators.ValidBooleanOperator(_operator)) {
            super.setOperator(_operator);
            setValue(_value);
        }
        else{
            throw new IllegalArgumentException("Illegal operator argument"+_operator);
        }
    }

    /**
     * Sets value of the Condition
     *
     * @param value The value to be set
     */
    public void setValue(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public boolean evaluate(Object val1) {
        if(val1 instanceof Boolean){
            switch (this.operator){
                case("=="):
                    return val1.equals(this.value);
                case("!="):
                    return !val1.equals(this.value);
                default:
                    return false;
            }
        }else return false;
    }

    @Override
    public String toString(){
        return "Requirement: " + super.name +" " +super.operator+ " " + this.value;
    }
}
