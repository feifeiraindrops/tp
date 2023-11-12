package seedu.modulight.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.modulight.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class StudentIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentId(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentId(invalidId));
    }

    @Test
    void isValidName() {
        // null student id
        assertThrows(NullPointerException.class, () -> StudentId.isValidSid(null));

        // invalid phone numbers
        assertFalse(StudentId.isValidSid("")); // empty string
        assertFalse(StudentId.isValidSid(" ")); // spaces only
        assertFalse(StudentId.isValidSid("student id")); // non-numeric
        assertFalse(StudentId.isValidSid("1234567")); // no letters
        assertFalse(StudentId.isValidSid("AA1234567")); // more than 1 letter at the start
        assertFalse(StudentId.isValidSid("A123456Y")); // less than 7 digits
        assertFalse(StudentId.isValidSid("A12345678Y")); // more than 7 digits
        assertFalse(StudentId.isValidSid("a12345678y")); // lower case
        assertFalse(StudentId.isValidSid("A1234 567Y")); // spaces within student number

        // valid phone numbers
        assertTrue(StudentId.isValidSid("A0000000Y"));
        assertTrue(StudentId.isValidSid("B1234567D"));
    }

    @Test
    void testEquals() {
        StudentId sid = new StudentId("A1234567Y");

        // same values -> returns true
        assertTrue(sid.equals(new StudentId("A1234567Y")));

        // same object -> returns true
        assertTrue(sid.equals(sid));

        // null -> returns false
        assertFalse(sid.equals(null));

        // different types -> returns false
        assertFalse(sid.equals(5.0f));

        // different values -> returns false
        assertFalse(sid.equals(new StudentId("A1234567W")));
    }
}
