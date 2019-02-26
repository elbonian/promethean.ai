package ai.promethean.DataModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PropertyMap {
    private Map<String, Property> property_map = new HashMap<>();

    public PropertyMap() {}
    public PropertyMap(PropertyMap propertyMap) {
        for (Property p: propertyMap.getProperties() ) {
            this.addProperty(p);
        }
    }

    /* Used to add a new property to the PropertyMap. If a property already has the name specified, the value will be updated
     * @param   name    The name of the property being added
     * @param   value   The boolean value associated with the propertyZ
     */

    public void addProperty(String name, Boolean value, String type) {
        if(property_map.containsKey(name)) {
            throw new IllegalArgumentException("Properties should never be mutated.");
        } else {
            property_map.put(name, new BooleanProperty(name, value, type));
        }
    }

    /* Add a Property with a Double value
     */

    public void addProperty(String name, Double value, String type) {
        if(property_map.containsKey(name)) {
            throw new IllegalArgumentException("Properties should never be mutated.");
        } else {
            property_map.put(name, new NumericalProperty(name, value, type));
        }
    }

    /* Add a Property with a String value
     */

    public void addProperty(String name, String value, String type) {
        if(property_map.containsKey(name)) {
            throw new IllegalArgumentException("Properties should never be mutated.");
        } else {
            property_map.put(name, new StringProperty(name, value, type));
        }
    }

    public void addProperty(Property p) {
        if(property_map.containsKey(p.getName())) {
            throw new IllegalArgumentException("Properties should never be mutated.");
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

    public ArrayList<Property> getProperties() {
        ArrayList<Property> properties =  new ArrayList<>();
        for (String key : this.getKeys()) {
            properties.add(getProperty(key));
        }
        return properties;
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
            printOut=printOut + "\n Name: "+ p.getName()+ " Type: " +p.getType()+ " Value: " + p.getValue();
        }
        return printOut;
    }
}
