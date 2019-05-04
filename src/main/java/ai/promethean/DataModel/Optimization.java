package ai.promethean.DataModel;

/**
 * Optimization class used to define optimizations in the input JSON files
 */
public class Optimization {
    private String name;
    private String type;
    private double weight;

    /**
     * Instantiates a new Optimization.
     *
     * @param _name     The name
     * @param type      Whether the optimization is a minimization or maximization
     * @param _weight The Weight of the optimization, with 0 being the lowest
     */
    public Optimization(String _name, String type, double _weight){
        setName(_name);
        setType(type);
        setWeight(_weight);
    }

    /**
     * Sets Weight
     *
     * @param weight The weight
     */
    public void setWeight(double weight) { this.weight = weight; }

    /**
     * Gets Weight
     *
     * @return The Weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the type of the Optimization
     *
     * @param type The new type of the Optimization
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the type of the Optimization
     *
     * @return The Optimization type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets name of the Optimization
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks whether a given Optimization is the same as this one
     *
     * @param optimization The optimization to compare
     * @return Boolean whether the input Optimization is the same
     */
    public Boolean equals(Optimization optimization){
        return optimization.getName().equals(this.name) && optimization.getType().equals(this.type);
    }

    @Override
    public String toString() {
        if(type == "min")
            return "Optimization Name: " +name + " Type: Minimize Weight: " + weight;
        else
            return "Optimization Name: " +name + " Type: Maximize Weight: " + weight;
    }
}
