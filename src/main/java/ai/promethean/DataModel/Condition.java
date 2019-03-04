package ai.promethean.DataModel;

/**
 * The base Condition class to be extended for different types
 */
public abstract class Condition {
    protected String name;
    protected String operator;

    /**
     * Instantiates a new Condition with just a name
     *
     * @param _name The name of the condition
     */
    public Condition(String _name){
        setName(_name);
    }

    /**
     * Instantiates a new Condition.
     *
     * @param _name     The name of the condition
     * @param _operator The operator used to compare the value assigned in the specific TypeCondition
     */
    public Condition(String _name,  String _operator){
        if (_operator.equals("==") || _operator.equals(">") || _operator.equals(">=") || _operator.equals("<") || _operator.equals("<=") || _operator.equals("!=")){
            setName(_name);
            setOperator(_operator);
        }
        else{
            //if not valid operator throw exception
            throw new IllegalArgumentException("Illegal operator argument"+_operator);
        }
    }

    /**
     * Set the name of the Condition
     *
     * @param _name The name
     */
    public void setName(String _name){
        this.name=_name;
    }

    /**
     * Gets the name of the Condition
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the operator of the Condition
     *
     * @param operator The operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Gets the operator of the Condition
     *
     * @return The operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Evaluate whether an input value satisfies the operator specified in the TypeCondition which extends this class
     *
     * @param val1 The value to be evaluated
     * @return Boolean whether the input value satisfies the operator
     */
    public abstract boolean evaluate(Object val1);

    /**
     * Gets the value of the Condition
     *
     * @return The value
     */
    public abstract Object getValue();
}
