package seedu.modulight.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.modulight.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TutorialGroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorialGroup(null));
    }

    @Test
    public void constructor_invalidtg_throwsIllegalArgumentException() {
        String invalidTg = "";
        assertThrows(IllegalArgumentException.class, () -> new TutorialGroup(invalidTg));
    }

    @Test
    void isValidTutorial() {
        // null tg number
        assertThrows(NullPointerException.class, () -> TutorialGroup.isValidTutorial(null));

        // invalid tg numbers
        assertFalse(TutorialGroup.isValidTutorial("")); // empty string
        assertFalse(TutorialGroup.isValidTutorial(" ")); // spaces only
        assertFalse(TutorialGroup.isValidTutorial("tutorial")); // non-numeric
        assertFalse(TutorialGroup.isValidTutorial("91")); // only digits
        assertFalse(TutorialGroup.isValidTutorial("T")); // only capital letter
        assertFalse(TutorialGroup.isValidTutorial("T1")); // only 1 digit
        assertFalse(TutorialGroup.isValidTutorial("T1234")); // more than 2 digits

        // valid tg numbers
        assertTrue(TutorialGroup.isValidTutorial("T12")); // exactly 1 capital letter and 2 digits
        assertTrue(TutorialGroup.isValidTutorial("T99"));
    }

    @Test
    void equals() {
        TutorialGroup tg = new TutorialGroup("T01");

        // same values -> returns true
        assertTrue(tg.equals(new TutorialGroup("T01")));

        // same object -> returns true
        assertTrue(tg.equals(tg));

        // null -> returns false
        assertFalse(tg.equals(null));

        // different types -> returns false
        assertFalse(tg.equals(5.0f));

        // different values -> returns false
        assertFalse(tg.equals(new TutorialGroup("T02")));
    }
}
