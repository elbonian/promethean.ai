package ai.promethean.DataModel;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

public class Condition {
    private int val1;
    private int val2;
    //this needs some work - is there any way to pass in operators directly?
    private String operator;


    public Condition(int _val1, int _val2, String _operator){
        setVal1(_val1);
        setVal2(_val2);
        setOperator(_operator);
    }

    public void setVal1(int val1) {
        this.val1 = val1;
    }

    public void setVal2(int val2) {
        this.val2 = val2;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getVal1() {
        return val1;
    }

    public int getVal2() {
        return val2;
    }

    public String getOperator() {
        return operator;
    }
    public boolean evaluate(int val1, int val2, String Operator){
        if (operator.equals("=="))
        {
            return val1 == val2;
        }
        else if (operator.equals(">"))
        {
            return val1 > val2;
        }
        else if (operator.equals("<"))
        {
            return val1 < val2;
        }
        else if (operator.equals("<="))
        {
            return val1 <= val2;
        }
        else if (operator.equals(">="))
        {
            return val1 >= val2;
        }
        else if (operator.equals("!="))
        {
            return val1 != val2;
        }
        else{
            //need a valid operator
            return false;
        }
    }
}
