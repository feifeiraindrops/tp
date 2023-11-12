package seedu.modulight.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's Grade in the address book.
 * Guarantees: is valid as declared in {@link #isValidGrade(String)}
 */
public class StudentGrade {
    public static final String MESSAGE_CONSTRAINTS =
            "Student Grade must only contains at most 2 characters.";
    public static final String VALIDATION_REGEX = ".{0,2}";
    public final String grade;

    /**
     * Constructs a {@code studentGrade}.
     *
     * @param grade A valid student grade.
     */
    public StudentGrade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_CONSTRAINTS);
        this.grade = grade;
    }

    /**
     * Returns true if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return grade;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentGrade)) {
            return false;
        }

        StudentGrade otherGrade = (StudentGrade) other;
        return grade.equals(otherGrade.grade);
    }

    @Override
    public int hashCode() {
        return grade.hashCode();
    }

}
