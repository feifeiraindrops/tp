package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class StudentEmailTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentEmail(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentEmail(invalidEmail));
    }
    @Test
    void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> StudentEmail.isValidEmail(null));

        // blank email
        assertFalse(StudentEmail.isValidEmail("")); // empty string
        assertFalse(StudentEmail.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(StudentEmail.isValidEmail("@example.com")); // missing local part
        assertFalse(StudentEmail.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(StudentEmail.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(StudentEmail.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(StudentEmail.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(StudentEmail.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(StudentEmail.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(StudentEmail.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(StudentEmail.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(StudentEmail.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(StudentEmail.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(StudentEmail.isValidEmail("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(StudentEmail.isValidEmail("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(StudentEmail.isValidEmail("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(StudentEmail.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(StudentEmail.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(StudentEmail.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(StudentEmail.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(StudentEmail.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(StudentEmail.isValidEmail("peterjack@example.c")); // top level domain has less than two chars

        // valid email
        assertTrue(StudentEmail.isValidEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(StudentEmail.isValidEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(StudentEmail.isValidEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(StudentEmail.isValidEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(StudentEmail.isValidEmail("a@bc")); // minimal
        assertTrue(StudentEmail.isValidEmail("test@localhost")); // alphabets only
        assertTrue(StudentEmail.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(StudentEmail.isValidEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(StudentEmail.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(StudentEmail.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(StudentEmail.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        StudentEmail email = new StudentEmail("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new StudentEmail("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new StudentEmail("other.valid@email")));
    }
}
