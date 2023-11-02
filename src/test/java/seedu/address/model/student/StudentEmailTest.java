package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentEmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentEmail(null));
    }

    @Test
    public void constructor_Invalid_Email_throwsIllegalArgumentException() {
        //empty string
        assertThrows(IllegalArgumentException.class, () -> new StudentEmail(""));

        //missing domain
        assertThrows(IllegalArgumentException.class, () -> new StudentEmail("abc"));
        assertThrows(IllegalArgumentException.class, () -> new StudentEmail("abc@"));

        //missing @
        assertThrows(IllegalArgumentException.class, () -> new StudentEmail("abc.com"));

        //no local
        assertThrows(IllegalArgumentException.class, () -> new StudentEmail("@email.com"));

        //last part of domain is less than 2 characters
        assertThrows(IllegalArgumentException.class, () -> new StudentEmail("@email.c"));
    }

    @Test
    void isValidEmail() {

    }

    @Test
    void testEquals() {
    }
}