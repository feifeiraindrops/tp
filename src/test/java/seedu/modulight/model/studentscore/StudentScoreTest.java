package seedu.modulight.model.studentscore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_COMMENT_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_COMMENT_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_GCNAME_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_GCNAME_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_SCORE_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_SID_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_SID_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_TAG_AMY;

import org.junit.jupiter.api.Test;

import seedu.modulight.testutil.StudentScoreBuilder;
import seedu.modulight.testutil.TestStudentScoreDataUtil;


class StudentScoreTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        StudentScore studentScore = new StudentScoreBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> studentScore.getTags().remove(0));
    }

    @Test
    public void testIsValidScore() {
    // StudentScore studentScore = TestStudentScoreDataUtil.getTestStudentScores().get(5); //sid = A0000005Y
    // GradedComponent gradedComponent = TestGcDataUtil.getTestGradedComponents()[0];

    }

    @Test
    public void testCalcRelativeScore() {

    }

    @Test
    public void testIsSameScore() {
        StudentScore studentScore = TestStudentScoreDataUtil.getTestStudentScores().get(5); //sid = A0000005Y
        studentScore.setComment(VALID_COMMENT_JAMES);

        // same object -> returns true
        assertTrue(studentScore.isSameScore(studentScore));

        // null -> returns false
        assertFalse(studentScore.isSameScore(null));

        // same studentId, same gradedComponent Name, all other attributes different -> returns true
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScore).withScore(VALID_SCORE_AMY)
                .withComment(VALID_COMMENT_AMY).build();
        assertTrue(studentScore.isSameScore(editedStudentScore));

        // different student id, all other attributes same -> returns false
        editedStudentScore = new StudentScoreBuilder(studentScore).withStudentId(VALID_SID_JAMES).build();
        assertFalse(studentScore.isSameScore(editedStudentScore));

        // different gradedComponent Name, all other attributes same -> return false
        editedStudentScore = new StudentScoreBuilder(studentScore).withGcName(VALID_GCNAME_JAMES).build();
        assertFalse(studentScore.isSameScore(editedStudentScore));

    }

    @Test
    public void testEquals() {
        StudentScore studentScore = TestStudentScoreDataUtil.getTestStudentScores().get(5); //sid = A0000005Y
        studentScore.setComment(VALID_COMMENT_JAMES);

        // Same values -> return true
        StudentScore studentScoreCopy = new StudentScoreBuilder(studentScore).build();
        assertTrue(studentScore.equals(studentScoreCopy));

        // Same object -> return true
        assertTrue(studentScore.equals(studentScore));

        // null -> return false
        assertFalse(studentScore.equals(null));

        // Different Type -> return false
        assertFalse(studentScore.equals("Score!"));

        // Different Student Score -> return false
        StudentScore anotherStudentScore = TestStudentScoreDataUtil.getTestStudentScores().get(4);
        assertFalse(studentScore.equals(anotherStudentScore));

        // Different Student Id -> return False
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScore).withStudentId(VALID_SID_AMY).build();
        assertFalse(studentScore.equals(editedStudentScore));

        // Different gcName -> return false
        editedStudentScore = new StudentScoreBuilder(studentScore).withGcName(VALID_GCNAME_AMY).build();
        assertFalse(studentScore.equals(editedStudentScore));

        // Different Score -> return false
        editedStudentScore = new StudentScoreBuilder(studentScore).withScore(VALID_SCORE_AMY).build();
        assertFalse(studentScore.equals(editedStudentScore));

        // Different Comment -> return false
        editedStudentScore = new StudentScoreBuilder(studentScore).withComment(VALID_COMMENT_AMY).build();
        assertFalse(studentScore.equals(editedStudentScore));

        // Different Tags -> return false
        editedStudentScore = new StudentScoreBuilder(studentScore).withTags(VALID_TAG_AMY).build();
        assertFalse(studentScore.equals(editedStudentScore));
    }

    @Test
    public void toStringMethod() {
        StudentScore studentScore = TestStudentScoreDataUtil.getTestStudentScores().get(5); //sid = A0000005Y
        studentScore.setComment(VALID_COMMENT_JAMES);

        String expected = StudentScore.class.getCanonicalName() + "{student id=" + studentScore.getStudentId()
                + ", component name=" + studentScore.getGcName() + ", score=" + studentScore.getScore()
                + ", comment=" + studentScore.getComment() + ", tags=" + studentScore.getTags() + "}";

        assertEquals(expected, studentScore.toString());
    }
}
