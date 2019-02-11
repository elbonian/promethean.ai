package ai.promethean.DataModel;

public class NumericalCondition extends Condition {
    protected Double value;

    public NumericalCondition(String _name, String _operator, Double _value) {
        super(_name, _operator);
        setValue(_value);
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean evaluate(Object val1){
        if(val1 instanceof Double) {
            if (operator.equals("==")) {
                return val1.equals(this.value);
            } else if (operator.equals(">")) {
                return (Double)val1 > value;
            } else if (operator.equals("<")) {
                return (Double)val1 < value;
            } else if (operator.equals("<=")) {
                return (Double)val1 <= value;
            } else if (operator.equals(">=")) {
                return (Double)val1 >= value;
            } else if (operator.equals("!=")) {
                return !val1.equals(value);
            }
            else return false;
        }else return false;

    }

    @Override
    public String toString(){
        return "Requirement: " + super.name +" " +super.operator+ " " + this.value;
    }
}
