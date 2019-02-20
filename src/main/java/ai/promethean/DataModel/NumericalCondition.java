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
