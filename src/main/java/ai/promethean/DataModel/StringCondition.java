package ai.promethean.DataModel;

import ai.promethean.Logger.Logger;

/**
 * String extension of the Condition class
 */
public class StringCondition extends Condition {
    protected String value;

    /**
     * Instantiates a new String condition.
     *
     * @param _name     The name
     * @param _operator The operator used to compare the value
     * @param _value    The value
     */
    public StringCondition(String _name, String _operator, String _value){
        super(_name);
        if(Operators.ValidBooleanOperator(_operator)) {
            super.setOperator(_operator);
            setValue(_value);
        }
        else{
            IllegalArgumentException e = new IllegalArgumentException("Illegal operator argument"+_operator);
            Logger.logError(e, this.getClass().getSimpleName());
            throw e;
        }
    }

    /**
     * Sets the value of the Condition
     *
     * @param value The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean evaluate(Object val1) {
        if(val1 instanceof String){
            switch (this.operator){
                case("=="): return val1.equals(this.value);
                case("!="): return !val1.equals(this.value);
                default: return false;
            }
        }else return false;
    }

    @Override
    public String toString(){
        return "Requirement: " + super.name +" " +super.operator+ " " + this.value;
    }
}
