package seedu.modulight.model.gradedcomponent;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.commons.util.AppUtil.checkArgument;

/**
 * Represents the weightage of a graded component in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeightage(double)}
 */
public class Weightage {


    public static final String MESSAGE_CONSTRAINTS =
            "Weightage should be >= 0 and <= 100.";

    public final Float weightage;

    /**
     * Constructs a {@code Weightage}.
     *
     * @param weightage Weightage for a weighted component for the module.
     */
    public Weightage(float weightage) {
        requireNonNull(weightage);
        checkArgument(isValidWeightage(weightage), MESSAGE_CONSTRAINTS);
        this.weightage = weightage;
    }

    /**
     * Returns true if a given string is a valid weightage.
     */
    public static boolean isValidWeightage(float m) {
        return m >= 0.0 && m <= 100.0;
    }

    @Override
    public String toString() {
        return weightage.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Weightage)) {
            return false;
        }

        Weightage otherWeightage = (Weightage) other;
        return weightage.equals(otherWeightage.weightage);
    }

    @Override
    public int hashCode() {
        return weightage.hashCode();
    }

}
