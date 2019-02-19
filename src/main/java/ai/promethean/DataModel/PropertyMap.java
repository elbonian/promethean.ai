package ai.promethean.DataModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PropertyMap {
    private Map<String, Property> property_map = new HashMap<>();

    public PropertyMap() {}

    /* Used to add a new property to the PropertyMap. If a property already has the name specified, the value will be updated
     * @param   name    The name of the property being added
     * @param   value   The boolean value associated with the propertyZ
     */
    public void addProperty(String name, Boolean value, boolean isDelta) {
        Property new_prop = new BooleanProperty(name, value,isDelta);
        if(property_map.containsKey(name)) {
            property_map.replace(name, new_prop);
        } else {
            property_map.put(name, new_prop);
        }
    }

    public void addProperty(String name, Boolean value) {
        Property new_prop = new BooleanProperty(name, value);
        if(property_map.containsKey(name)) {
            property_map.replace(name, new_prop);
        } else {
            property_map.put(name, new_prop);
        }
    }

    /* Add a Property with a Double value
     */
    public void addProperty(String name, Double value, boolean isDelta) {
        Property new_prop = new NumericalProperty(name, value, isDelta);
        if(property_map.containsKey(name)) {
            property_map.replace(name, new_prop);
        } else {
            property_map.put(name, new_prop);
        }
    }

    public void addProperty(String name, Double value) {
        Property new_prop = new NumericalProperty(name, value);
        if(property_map.containsKey(name)) {
            property_map.replace(name, new_prop);
        } else {
            property_map.put(name, new_prop);
        }
    }

    /* Add a Property with a String value
     */
    public void addProperty(String name, String value, boolean isDelta) {
        Property new_prop = new StringProperty(name, value, isDelta);
        if(property_map.containsKey(name)) {
            property_map.replace(name, new_prop);
        } else {
            property_map.put(name, new_prop);
        }
    }

    public void addProperty(String name, String value) {
        Property new_prop = new StringProperty(name, value);
        if(property_map.containsKey(name)) {
            property_map.replace(name, new_prop);
        } else {
            property_map.put(name, new_prop);
        }
    }

    public void addProperty(Property p) {

        if(property_map.containsKey(p.getName())) {
            property_map.replace(p.getName(), p);
        } else {
            property_map.put(p.getName(), p);
        }
    }

    /* Returns the private property_map to be used to compare two PropertyMaps
     */
    private Map<String, Property> getPropertyMap() {
        return property_map;
    }

    /* Returns the value of the property with specified name. If not property exists, returns null
     * @param   name    The name of the property to get the value of
     * @return Property object with the given name, null otherwise
     */
    public Property getProperty(String name) {
        return property_map.get(name);
    }

    /* Returns true/false whether the property map contains an entry with argument name
     * @param   name    The name of property to check in the map
     * @return Boolean whether the PropertyMap contains an entry with that key name
     */
    public Boolean containsProperty(String name) {
        return property_map.containsKey(name);
    }

    /* Returns a Java Set with all the string names of keys in the property map
     */
    public Set<String> getKeys() {
        return property_map.keySet();
    }

    /* Used to compare two PropertyMaps. Returns true if they are the same map, false otherwise. This includes the Property mapped to the name
     * @param   p   The PropertyMap to compare to this one
     * @return Boolean whether input PropertyMap is identical to this one
     */
    public Boolean equals(PropertyMap p) {
        return property_map.equals(p.getPropertyMap());
    }

    @Override
    public String toString() {
        String printOut="";
        for(Property p : property_map.values()){
            printOut=printOut + "\n Name: "+ p.getName();
            if(p.getIsDelta()) printOut=printOut+" Type: Delta";
            else printOut=printOut+" Type: Assignment";
            printOut=printOut+ " Value: " + p.getValue();
        }
        return printOut;
    }
}
