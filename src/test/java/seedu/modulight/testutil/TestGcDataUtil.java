package seedu.modulight.testutil;

import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.MaxMarks;
import seedu.modulight.model.gradedcomponent.Weightage;
import seedu.modulight.model.gradedcomponent.model.GradedComponentBook;
import seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook;

/**
 * Contains utility methods for populating GradedComponentBook with sample data.
 */
public class TestGcDataUtil {

    public static GradedComponent[] getTestGradedComponents() {
        return new GradedComponent[]{new GradedComponent(new GcName("Midterm"), new MaxMarks(50), new Weightage(30)),
            new GradedComponent(new GcName("Final"), new MaxMarks(100), new Weightage(40))};
    }

    public static GradedComponent[] getTestGradedComponentsAfterAdding() {
        return new GradedComponent[]{new GradedComponent(new GcName("Midterm"), new MaxMarks(50), new Weightage(30)),
            new GradedComponent(new GcName("Final"), new MaxMarks(100), new Weightage(40)),
            new GradedComponent(new GcName("Practical Exam"), new MaxMarks(30), new Weightage(30))};
    }

    public static GradedComponent[] getTestGradedComponentsAfterDeleting() {
        return new GradedComponent[]{new GradedComponent(new GcName("Midterm"), new MaxMarks(50), new Weightage(30))};
    }

    public static ReadOnlyGradedComponentBook getTestGcBook(String selectedGc) {
        GradedComponentBook sampleAb = new GradedComponentBook();
        GradedComponent[] gcToBeAdded;
        switch (selectedGc) {
        case "add":
            gcToBeAdded = getTestGradedComponentsAfterAdding();
            break;
        case "delete":
            gcToBeAdded = getTestGradedComponentsAfterDeleting();
            break;
        case "create":
            gcToBeAdded = getTestGradedComponents();
            break;
        default:
            gcToBeAdded = new GradedComponent[]{};
        }
        for (GradedComponent testGradedComponent : gcToBeAdded) {
            sampleAb.addGradedComponent(testGradedComponent);
        }
        return sampleAb;
    }
}
