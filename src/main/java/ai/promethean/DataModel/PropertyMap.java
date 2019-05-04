package ai.promethean.DataModel;
import ai.promethean.Logger.Logger;

import java.util.*;

/**
 * A Map of (name, Property object) to easily retrieve a Property with a given name
 */
public class PropertyMap {
    private Map<String, Property> property_map = new HashMap<>();
    private String className = this.getClass().getSimpleName();

    /**
     * Instantiates a new Property map.
     */
    public PropertyMap() {}
    public PropertyMap(PropertyMap propertyMap) {
        for (Property p: propertyMap.getProperties() ) {
            this.addProperty(p);
        }
    }

    /**
     * Add a BooleanProperty to the PropertyMap. If a property already has the name specified, an error is thrown
     *
     * @param name  The name
     * @param value The value
     */

    public void addProperty(String name, Boolean value, String type) {
        if(property_map.containsKey(name)) {
            IllegalArgumentException e = new IllegalArgumentException("Properties should never be mutated.");
            Logger.logError(e, className);
            throw e;
        } else {
            property_map.put(name, new BooleanProperty(name, value, type));
        }
    }

    /**
     * Add a NumericalProperty to the PropertyMap. If a property already has the name specified, an error is thrown
     *
     * @param name  The name
     * @param value The value
     */

    public void addProperty(String name, Double value, String type) {
        if(property_map.containsKey(name)) {
            IllegalArgumentException e = new IllegalArgumentException("Properties should never be mutated.");
            Logger.logError(e, className);
            throw e;
        } else {
            property_map.put(name, new NumericalProperty(name, value, type));
        }
    }

    /**
     * Add a StringProperty to the PropertyMap. If a property already has the name specified, an error is thrown
     *
     * @param name  The name
     * @param value The value
     */
    public void addProperty(String name, String value, String type) {
        if(property_map.containsKey(name)) {
            IllegalArgumentException e = new IllegalArgumentException("Properties should never be mutated.");
            Logger.logError(e, className);
            throw e;
        } else {
            property_map.put(name, new StringProperty(name, value, type));
        }
    }

    /**
     * Add a fully defined property to the PropertyMap. If a property already has the name specified, the value will be updated
     *
     * @param p The property to add
     */
    public void addProperty(Property p) {
        if(property_map.containsKey(p.getName())) {
            IllegalArgumentException e = new IllegalArgumentException("Properties should never be mutated.");
            Logger.logError(e, className);
            throw e;
        } else {
            property_map.put(p.getName(), p);
        }
    }

    /**
     * Gets the whole Map object and returns it, for use of comparing two PropertyMaps
     *
     * @return The property_map
     */
    private Map<String, Property> getPropertyMap() {
        return property_map;
    }

    /**
     * Gets value of the property with specified name. If not property exists, returns Null
     *
     * @param name The name of the property to get the value of
     * @return Property object with the given name, Null otherwise
     */
    public Property getProperty(String name) {
        return property_map.get(name);
    }

    /**
     * Checks whether the PropertyMap contains an entry with the given name
     *
     * @param name The name of property to check in the map
     * @return Boolean whether the PropertyMap contains an entry with that key name
     */
    public Boolean containsProperty(String name) {
        return property_map.containsKey(name);
    }

    /**
     * Gets a Java Set with all the string names of keys in the PropertyMap
     *
     * @return A Sat of Strings of the key names
     */
    public Set<String> getKeys() {
        return property_map.keySet();
    }

    /**
     * Retrieves an List of all Property objects in the PropertyMap
     *
     * @return List of Property objects
     */
    public List<Property> getProperties() {
        List<Property> properties =  new ArrayList<>();
        for (String key : this.getKeys()) {
            properties.add(getProperty(key));
        }
        return properties;
    }

    /**
     * Used to compare two PropertyMaps. Returns true if they are the same map, false otherwise. This includes the Property mapped to the name
     *
     * @param p the PropertyMap to compare to this one
     * @return Boolean whether the input PropertyMap is identical to this one
     */
    @Override
    public boolean equals(Object p){
        if(p instanceof PropertyMap) {
            Map<String, Property> propertyMap1= ((PropertyMap)p).getPropertyMap();
            if(propertyMap1.size()!=property_map.size()){
                return false;
            }
            for(String key: property_map.keySet()){
                if(!property_map.get(key).equals(propertyMap1.get(key))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        String printOut="";
        for(Property p : property_map.values()){
            printOut=printOut + "\n Name: "+ p.getName()+ " Type: " +p.getType()+ " Value: " + p.getValue();
        }
        return printOut;
    }
}
