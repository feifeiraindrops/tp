package seedu.modulight.commons.util;

import static java.util.Objects.requireNonNull;

import seedu.modulight.model.Model;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.model.GradedComponentBook;
import seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.model.StudentBook;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.studentscore.model.StudentScoreBook;

/**
 * A container for Model specific utility functions
 */
public class ModelUtil {

    public static final String MESSAGE_WEIGHTAGES_MORE_THAN_100 = "Weightages must add up to 100 or less.";
    public static final String MESSAGE_INCORRECT_ENTITY_COUNT = "Size of student list * size of graded component list"
            + "does not equal to size of student score list";
    /**
     * Calculates the sum of weightages of all graded components in the graded component book.
     */
    public static float weightageSum(ReadOnlyGradedComponentBook book) {
        requireNonNull(book);
        float totalWeightage = 0;
        for (GradedComponent gc : book.getGradedComponentList()) {
            float weight = gc.getWeightage().weightage;
            totalWeightage += weight;
        }
        return totalWeightage;
    }
    /**
     * Updates linkages between entities for add commands.
     */
    public static void addCommandUpdateLinks(Student student, GradedComponent gc, StudentScore sc) {
        sc.setGradedComponent(gc);
        sc.setStudent(student);
        student.addScore(sc);
        gc.addScore(sc);
    }

    /**
     * Updates books for add commands.
     */
    public static void addCommandUpdateBooks(Model model, Student student, GradedComponent gc, StudentScore sc) {
        GradedComponentBook gradedComponentBook = model.getGradedComponentBook();
        StudentBook studentBook = model.getStudentBook();
        StudentScoreBook studentScoreBook = model.getStudentScoreBook();
        gradedComponentBook.setGradedComponent(gc, gc);
        studentBook.setStudent(student, student);
        studentScoreBook.addStudentScore(sc);
    }

}
