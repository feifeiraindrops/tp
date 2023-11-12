package seedu.modulight.model.student;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_EMAIL_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_NAME_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_SID_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_SID_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_STUDENT_SCORE_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TAG_MAKEUP_EXAM;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TAG_TA;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TG_JAMES;
import static seedu.modulight.testutil.Assert.assertThrows;
import static seedu.modulight.testutil.TypicalStudents.ALICE;
import static seedu.modulight.testutil.TypicalStudents.JAMES;

import org.junit.jupiter.api.Test;

import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.testutil.StudentBuilder;
import seedu.modulight.testutil.StudentScoreBuilder;

class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // same name and sid, all other attributes different -> returns true
        StudentScore editedAmyScore = new StudentScoreBuilder(VALID_STUDENT_SCORE_JAMES)
                .withStudentId(VALID_SID_AMY).build();
        Student editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_JAMES)
                .withTg(VALID_TG_JAMES).withTags(VALID_TAG_TA).withScore(editedAmyScore).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // different name and sid, all other attributes same -> returns false
        editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_JAMES).withId(VALID_SID_JAMES).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Student editedJames = new StudentBuilder(JAMES).withName(VALID_NAME_JAMES.toLowerCase()).build();
        assertFalse(JAMES.isSameStudent(editedJames));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_JAMES + " ";
        editedJames = new StudentBuilder(JAMES).withName(nameWithTrailingSpaces).build();
        assertFalse(JAMES.isSameStudent(editedJames));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different Student -> returns false
        assertFalse(ALICE.equals(JAMES));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_JAMES).build();
        assertFalse(ALICE.equals(editedAlice));

        // different sid -> returns false
        editedAlice = new StudentBuilder(ALICE).withId(VALID_SID_JAMES).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_JAMES).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tutorial -> returns false
        editedAlice = new StudentBuilder(ALICE).withTg(VALID_TG_JAMES).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_MAKEUP_EXAM).build();
        assertFalse(ALICE.equals(editedAlice));

        // different scores -> return false
        StudentScore editedAmyScore = new StudentScoreBuilder(VALID_STUDENT_SCORE_JAMES).build();
        editedAlice = new StudentBuilder(ALICE).withScore(editedAmyScore).build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", student id=" + ALICE.getStudentId() + ", email=" + ALICE.getEmail()
                + ", tutorial group=" + ALICE.getTutorial() + ", tags=" + ALICE.getTags()
                + ", student grade=" + ALICE.getStudentGrade() + "}";
        assertEquals(expected, ALICE.toString());
    }

}

