package seedu.modulight.model.util;

import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.model.GradedComponentBook;
import seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook;

/**
 * Contains utility methods for populating GradedComponentBook with sample data.
 */
public class SampleGcDataUtil {

    public static GradedComponent[] getSampleGradedComponents() {
        return new GradedComponent[]{};
    }

    public static ReadOnlyGradedComponentBook getSampleGcBook() {
        GradedComponentBook sampleAb = new GradedComponentBook();
        for (GradedComponent sampleGradedComponent : getSampleGradedComponents()) {
            sampleAb.addGradedComponent(sampleGradedComponent);
        }
        return sampleAb;
    }
}
