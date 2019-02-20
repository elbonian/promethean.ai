package ai.promethean.DataModel;

public class BooleanCondition extends Condition {
    protected Boolean value;

    public BooleanCondition(String _name, String _operator, Boolean _value){
        super(_name);
        if(_operator.equals("==")|| _operator.equals("!=")) {
            super.setOperator(_operator);
            setValue(_value);
        }
        else{
            throw new IllegalArgumentException("Illegal operator argument"+_operator);
        }
    }

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
