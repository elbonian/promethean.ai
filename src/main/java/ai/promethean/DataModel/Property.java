package ai.promethean.DataModel;


/**
 * The base Property class to be extended for different types
 */
public abstract class Property {
    protected String name;
    protected String type;

    /**
     * Instantiates a new Property.
     */
    public Property(){
        setName("");
        setType("assignment");
    }

    /**
     * Instantiates a new Property.
     *
     * @param _name the name
     */
    public Property(String _name){
        setName(_name);
        setType(null);
    }

    /**
     * Instantiates a new Property.
     *
     * @param _name the name
     * @param _type the type
     */
    public Property(String _name, String _type){
        setName(_name);
        setType(_type);

    }

    /**
     * Set name.
     *
     * @param _name the name
     */
    public void setName(String _name){
        this.name=_name;
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param _type the type
     */
    public void setType(String _type) {
        type=_type;
    }

    public abstract String toString();

    /**
     * Equals boolean.
     *
     * @param p the p
     * @return the boolean
     */
    public abstract Boolean equals(Property p);

    /**
     * Gets value.
     *
     * @return the value
     */
    public abstract Object getValue();

    /**
     * Apply property impact onto property.
     *
     * @param p the p
     * @return the property
     */
    public abstract Property applyImpact(Property p);

}
