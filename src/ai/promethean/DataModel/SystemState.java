package ai.promethean.DataModel;
import java.sql.Time;
import java.util.ArrayList;

public class SystemState {
    private int UID;
    private Time timeStamp;
    private ArrayList<Resource> resources;
    private ArrayList<Property> properties;

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

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void addResource(Resource r){
        resources.add(r);
    }

    public void addResource(String name, Double value){
        resources.add(new Resource(name,value));
    }

    public void addProperty(Property p){
        properties.add(p);
    }

    public void addProperty(String name, Boolean value){
        properties.add(new BooleanProperty(name, value));
    }

    public void addProperty(String name, Double value){
        properties.add(new NumericalProperty(name, value));
    }

    public void addProperty(String name, String value){
        properties.add(new StringProperty(name, value));
    }
}
