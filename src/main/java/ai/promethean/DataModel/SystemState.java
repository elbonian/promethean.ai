package ai.promethean.DataModel;
import java.sql.Time;
import java.util.*;

public class SystemState {
    private int UID;
    private Time timeStamp;
    private PropertyMap properties = new PropertyMap();

    public SystemState(int _UID){
        setUID(_UID);
        timeStamp= new Time(System.currentTimeMillis());
    }

    public SystemState(int _UID, long time){
        setUID(_UID);
        timeStamp= new Time(time);
    }

    public void setUID(int _UID){
        this.UID=_UID;
    }

    public int getUID(){
        return UID;
    }

    public Time getTimeStamp(){
        return timeStamp;
    }

    public ArrayList<Property> getProperties() {
        ArrayList<Property> property_list = new ArrayList<>();
        for (String key : properties.getKeys()) {
            property_list.add(properties.getProperty(key));
        }
        return property_list;
    }

    public PropertyMap getPropertyMap() { return properties; }

    public void addProperty(String name, Boolean value) {
        properties.addProperty(name, value);
    }

    public void addProperty(String name, Double value) {
        properties.addProperty(name, value);
    }

    public void addProperty(String name, String value) {
        properties.addProperty(name, value);
    }

    public void addProperty(Property property) {
        properties.addProperty(property);
    }

    public Property getProperty(String name) {
        return properties.getProperty(name);
    }

    /*
    //TODO needs more testing
    public Boolean equals(SystemState systemState){
        this.sortProperties();
        this.sortResources();
        systemState.sortProperties();
        systemState.sortResources();

        return properties.equals(systemState.getProperties())&& resources.equals(systemState.getResources());
    }
    */

    // public void sortProperties(){ Collections.sort(properties, new SortbyProperty()); }

    @Override
    public String toString() {
        return "System State UID: " + this.UID + "\n Timestamp: " + this.timeStamp
                + "\n Properties: " + properties;
    }
}
