package seedu.modulight.model.gradedcomponent;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.commons.util.AppUtil.checkArgument;

/**
 * Represents a graded component's maximum marks in the address book.
 * Guarantees: immutable;
 */
public class MaxMarks {


    public static final String MESSAGE_CONSTRAINTS =
            "Maximum mark argument should be >= 0 and <= 10000";

    public final Float maxMarks;

    /**
     * Constructs a {@code MaxMarks}.
     *
     * @param maxMarks Maximum marks for a graded component.
     */
    public MaxMarks(float maxMarks) {
        requireNonNull(maxMarks);
        checkArgument(isValidMarks(maxMarks), MESSAGE_CONSTRAINTS);
        this.maxMarks = maxMarks;
    }

    /**
     * Returns true if a given string is a valid mark.
     */
    public static boolean isValidMarks(float m) {
        return m >= 0.0 && m <= 10000.0;
    }

    @Override
    public String toString() {
        return maxMarks.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MaxMarks)) {
            return false;
        }

        MaxMarks otherMark = (MaxMarks) other;
        return maxMarks.equals(otherMark.maxMarks);
    }

    @Override
    public int hashCode() {
        return maxMarks.hashCode();
    }

}
