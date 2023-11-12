package seedu.modulight.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.commons.util.AppUtil.checkArgument;

/**
 * The type Tutorial group.
 */
public class TutorialGroup {
    /**
     * The constant MESSAGE_CONSTRAINTS.
     */
    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial groups should only contain a capital letter and a 2 digit number, and it should not be blank";

    /**
     * The constant VALIDATION_REGEX.
     */

    public static final String VALIDATION_REGEX = "[A-Z]\\d{2}";
    public final String groupName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid tutorial group.
     */
    public TutorialGroup(String name) {
        requireNonNull(name);
        checkArgument(isValidTutorial(name), MESSAGE_CONSTRAINTS);
        groupName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @param test the test
     * @return the boolean
     */
    public static boolean isValidTutorial(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.modulight.model.student.TutorialGroup)) {
            return false;
        }

        seedu.modulight.model.student.TutorialGroup otherName = (seedu.modulight.model.student.TutorialGroup) other;
        return groupName.equals(otherName.groupName);
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }
}
