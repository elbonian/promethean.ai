package ai.promethean.DataModel;

public class StringCondition extends AbstractCondition {
    protected String value;

    public StringCondition(String _name, String _operator, String _value){
        super(_name);
        if(_operator.equals("==")|| _operator.equals("!=")) {
            super.setOperator(_operator);
            setValue(_value);
        }
        else{
            throw new IllegalArgumentException("Illegal operator argument"+_operator);
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Boolean evaluate(Object val1) {
        if(val1 instanceof String){
            if (this.operator.equals("=="))
            {
                return val1.equals(this.value);
            }
            else if (operator.equals("!="))
            {
                return !val1.equals(this.value);
            }
            else return false;

        }else return false;
    }

    @Override
    public String toString(){
        return "Requirement: " + super.name +" " +super.operator+ " " + this.value;
    }
}
