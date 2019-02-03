package ai.promethean.GraphManagement;

import java.util.Comparator;

public class FrontierComparator implements Comparator<StateTemplate> {
    public int compare(StateTemplate template1, StateTemplate template2) {
        return template1.getF() - template2.getF();
    }
}
