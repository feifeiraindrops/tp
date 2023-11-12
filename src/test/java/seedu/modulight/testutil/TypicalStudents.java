package seedu.modulight.testutil;

import static seedu.modulight.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_EMAIL_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_NAME_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TAG_MAKEUP_EXAM;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TAG_TA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.model.StudentBook;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withId("A0000000Y").withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withId("A0000001Y").withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withId("A0000002Y").withName("Carl Kurz")
            .withEmail("heinz@example.com").build();
    public static final Student DANIEL = new StudentBuilder().withId("A0000003Y").withName("Daniel Meier")
            .withEmail("cornelia@example.com").withTags("plagiarism").build();
    public static final Student ELLE = new StudentBuilder().withId("A0000004Y").withName("Elle Meyer")
            .withEmail("werner@example.com").build();
    public static final Student FIONA = new StudentBuilder().withId("A0000005Y").withName("Fiona Kunz")
            .withEmail("lydia@example.com").build();
    public static final Student GEORGE = new StudentBuilder().withId("A0000006Y").withName("George Best")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withId("A0000007Y").withName("Hoon Meier")
            .withEmail("stefan@example.com").build();
    public static final Student IDA = new StudentBuilder().withId("A0000008Y").withName("Ida Mueller")
            .withEmail("hans@example.com").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_TA).build();
    public static final Student JAMES = new StudentBuilder().withName(VALID_NAME_JAMES)
            .withEmail(VALID_EMAIL_JAMES).withTags(VALID_TAG_TA, VALID_TAG_MAKEUP_EXAM)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical Students.
     */
    public static StudentBook getTypicalAddressBook() {
        StudentBook ab = new StudentBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
