package seedu.modulight.model.gradedcomponent;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.commons.util.AppUtil.checkArgument;

/**
 * Represents a graded component's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class GcName {

    public static final String MESSAGE_CONSTRAINTS =
            "Graded component names must only contain alphanumeric characters, and should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String gcName;

    /**
     * Constructs a {@code gcName}.
     *
     * @param name A valid student id.
     */
    public GcName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.gcName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return gcName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GcName)) {
            return false;
        }

        GcName otherName = (GcName) other;
        return gcName.equals(otherName.gcName);
    }

    @Override
    public int hashCode() {
        return gcName.hashCode();
    }

}
