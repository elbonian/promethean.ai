package ai.promethean.TestConstants;

import ai.promethean.DataModel.Perturbation;

public class PerturbationTestConstants {

    public static Perturbation getTestPurterbation1 () {
        Perturbation perturbation = new Perturbation();
        perturbation.addProperty(PropertyTestConstants.getNumericProperty1());
        return perturbation;
    }

    public static Perturbation getTestPurterbation2 () {
        Perturbation perturbation = new Perturbation();
        perturbation.addProperty(PropertyTestConstants.getNumericProperty2());
        return perturbation;
    }

    public static Perturbation getTestPurterbation3 () {
        Perturbation perturbation = new Perturbation();
        perturbation.addProperty(PropertyTestConstants.getBooleanProperty1());
        return perturbation;
    }

    public static Perturbation getTestPurterbation4 () {
        Perturbation perturbation = new Perturbation();
        perturbation.addProperty(PropertyTestConstants.getBooleanProperty2());
        return perturbation;
    }

    public static Perturbation getTestPurterbation5 () {
        Perturbation perturbation = new Perturbation();
        perturbation.addProperty(PropertyTestConstants.getStringProperty1());
        return perturbation;
    }

    public static Perturbation getTestPurterbation6 () {
        Perturbation perturbation = new Perturbation();
        perturbation.addProperty(PropertyTestConstants.getStringProperty2());
        return perturbation;
    }
}
