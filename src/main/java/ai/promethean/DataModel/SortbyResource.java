package ai.promethean.DataModel;

import java.util.Comparator;

public class SortbyResource implements Comparator<Resource> {
    public int compare(Resource a, Resource b){
        return a.getName().compareTo(b.getName());
    }
}
