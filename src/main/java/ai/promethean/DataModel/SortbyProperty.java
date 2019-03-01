package ai.promethean.DataModel;

import java.util.Comparator;

/**
 * The type Sortby property.
 */
public class SortbyProperty implements Comparator<Property> {
    public int compare(Property a, Property b){
        return a.getName().compareTo(b.getName());
    }
}
