package seedu.modulight.model.comment;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.commons.util.AppUtil.checkArgument;

/**
 * Represents a comment for a student or graded component.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Comment {

    public static final String MESSAGE_CONSTRAINTS =
            "Comments must only contain alphanumeric characters, should not be blank, "
                    + "and be less than or equal to 500 characters.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String comment;

    /**
     * Constructs a {@code gcName}.
     *
     * @param name A valid student id.
     */
    public Comment(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.comment = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 500;
    }


    @Override
    public String toString() {
        return comment;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Comment)) {
            return false;
        }

        Comment otherName = (Comment) other;
        return comment.equals(otherName.comment);
    }

    @Override
    public int hashCode() {
        return comment.hashCode();
    }

}


