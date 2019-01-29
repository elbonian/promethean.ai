package ai.promethean.DataModel;

public class Optimization {
    private String name;
    private Boolean isMin;

    public Optimization(String _name, Boolean _isMin){
        setName(_name);
        setIsMin(_isMin);
    }

    public void setIsMin(Boolean min) {
        isMin = min;
    }

    public Boolean getIsMin() {
        return isMin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean equals(Optimization optimization){
        return optimization.getName().equals(this.name) && optimization.getIsMin().equals(this.isMin);
    }

    @Override
    public String toString() {
        if(isMin)
            return "Optimization Name: " +name + " Type: Minimize";
        else
            return "Optimization Name: " +name + " Type: Maximize";
    }
}
