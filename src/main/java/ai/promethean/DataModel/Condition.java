package ai.promethean.DataModel;

import java.lang.*;

public class Condition {
    private String name;
    private double value;
    //this needs some work - is there any way to pass in operators directly?
    private String operator;



    public Condition(String _name, double _value, String _operator){
        if (_operator.equals("==") || _operator.equals(">") || _operator.equals(">=") || _operator.equals("<") || _operator.equals("<=") || _operator.equals("!=")){
            setName(_name);
            setValue(_value);
            setOperator(_operator);
        }
        else{
            //if not valid operator throw exception
            throw new IllegalArgumentException("Illegal operator argument"+_operator);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getName() {return name; }

    public double getValue() {
        return value;
    }

    public String getOperator() {
        return operator;
    }


    public boolean evaluate(double val1){
        if (this.operator.equals("=="))
        {
            return val1 == this.value;
        }
        else if (operator.equals(">"))
        {
            return val1 > value;
        }
        else if (operator.equals("<"))
        {
            return val1 < value;
        }
        else if (operator.equals("<="))
        {
            return val1 <= value;
        }
        else if (operator.equals(">="))
        {
            return val1 >= value;
        }
        else if (operator.equals("!="))
        {
            return val1 != value;
        }
        else{
            //if not valid operator throw exception
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return "Requirement: " + name +" " +operator+ " " + value;
    }
}
