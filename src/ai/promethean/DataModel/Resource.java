package ai.promethean.DataModel;

public class Resource {

    private String name;
    private Double value;


    public Resource(){
        setName("");
        setValue(0.0);
    }
    public Resource(String _name, Double _value){
        setName(_name);
        setValue(_value);
    }

    public void setName(String _name){
        this.name=_name;
    }

    public void setValue(Double _value){
        this.value=_value;
    }

    public String getName(){
        return this.name;
    }

    public Double getValue(){
        return this.value;
    }

    public String toString(){
        return "Resource Name: " + this.name + ", Value: " + this.value+ " ";
    }
}
