package ai.promethean.DataModel;

public abstract class Condition {
    protected String name;
    protected String operator;

    public Condition(String _name){
        setName(_name);
    }
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

    public void setName(String _name){
        this.name=_name;
    }

    public String getName() {
        return name;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public abstract boolean evaluate(Object val1);
    public abstract Object getValue();
}
