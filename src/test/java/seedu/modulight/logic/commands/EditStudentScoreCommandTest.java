package seedu.modulight.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.DESC_AMY_SCORE;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.DESC_JAMES_SCORE;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_COMMENT_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_COMMENT_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_SCORE_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_SCORE_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_TAG_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_TAG_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.assertCommandFailure;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.assertCommandSuccess;
import static seedu.modulight.logic.commands.EditStudentScoreCommand.MESSAGE_EDIT_PERSON_SUCCESS;
import static seedu.modulight.testutil.TestGcDataUtil.getTestGcBook;
import static seedu.modulight.testutil.TestStudentDataUtil.getTestStudentBook;
import static seedu.modulight.testutil.TestStudentScoreDataUtil.getSampleStudentScoreBook;
import static seedu.modulight.testutil.TypicalIndexes.INDEX_FIRST_SCORE;

import org.junit.jupiter.api.Test;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.Messages;
import seedu.modulight.model.Model;
import seedu.modulight.model.ModelManager;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.testutil.EditStudentScoreDescriptorBuilder;
import seedu.modulight.testutil.StudentScoreBuilder;


class EditStudentScoreCommandTest {

    private Model model = new ModelManager(getTestStudentBook("create"),
            getSampleStudentScoreBook("twoScores"),
            getTestGcBook("create"), new UserPrefs());
    private boolean useFiltered = false;

    @Test
    public void execute_indexMoreThanListSize_failure() {
        int size = model.getFilteredStudentScoreList().size();
        Index outOfBoundIndex = Index.fromZeroBased(size);
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                outOfBoundIndex, descriptor, useFiltered);

        assertCommandFailure(editStudentScoreCommand, model,
                EditStudentScoreCommand.MESSAGE_INVALID_SCORE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validEditScore_success() {
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().withScore(VALID_SCORE_JAMES).build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                INDEX_FIRST_SCORE, descriptor, useFiltered);

        StudentScore studentScoreToEdit = model.getStudentScoreBook().getStudentScoreList()
                .get(INDEX_FIRST_SCORE.getZeroBased());
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScoreToEdit)
                .withScore(VALID_SCORE_JAMES).build();

        Model expectedModel = new ModelManager(getTestStudentBook("create"),
                getSampleStudentScoreBook("twoScores"),
                getTestGcBook("create"), new UserPrefs());
        expectedModel.getStudentScoreBook().setStudentScore(studentScoreToEdit, editedStudentScore);

        Student editedStudent = expectedModel.getStudentBook().getStudentById(editedStudentScore.getStudentId());
        editedStudent.deleteScore(studentScoreToEdit);
        editedStudent.addScore(editedStudentScore);

        String expectedMessage = String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudentScore(editedStudentScore));
        assertCommandSuccess(editStudentScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_negativeScore_failure() {
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().withScore(-2).build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                INDEX_FIRST_SCORE, descriptor, useFiltered);

        assertCommandFailure(editStudentScoreCommand, model, EditStudentScoreCommand.SCORE_VALUE_NOT_VALID);
    }

    @Test
    public void execute_validEditComment_success() {
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().withComment(VALID_COMMENT_AMY).build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                INDEX_FIRST_SCORE, descriptor, useFiltered);

        StudentScore studentScoreToEdit = model.getStudentScoreBook().getStudentScoreList()
                .get(INDEX_FIRST_SCORE.getZeroBased());
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScoreToEdit)
                .withComment(VALID_COMMENT_AMY).build();

        Model expectedModel = new ModelManager(getTestStudentBook("create"),
                getSampleStudentScoreBook("twoScores"),
                getTestGcBook("create"), new UserPrefs());
        expectedModel.getStudentScoreBook().setStudentScore(studentScoreToEdit, editedStudentScore);

        Student editedStudent = expectedModel.getStudentBook().getStudentById(editedStudentScore.getStudentId());
        editedStudent.deleteScore(studentScoreToEdit);
        editedStudent.addScore(editedStudentScore);

        String expectedMessage = String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudentScore(editedStudentScore));
        assertCommandSuccess(editStudentScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editCommentWithNoValue_success() {
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().withComment(VALID_COMMENT_AMY).build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                INDEX_FIRST_SCORE, descriptor, useFiltered);

        StudentScore studentScoreToEdit = model.getStudentScoreBook().getStudentScoreList()
                .get(INDEX_FIRST_SCORE.getZeroBased());
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScoreToEdit)
                .withComment(VALID_COMMENT_AMY).build();

        Model expectedModel = new ModelManager(getTestStudentBook("create"),
                getSampleStudentScoreBook("twoScores"),
                getTestGcBook("create"), new UserPrefs());
        expectedModel.getStudentScoreBook().setStudentScore(studentScoreToEdit, editedStudentScore);

        Student editedStudent = expectedModel.getStudentBook().getStudentById(editedStudentScore.getStudentId());
        editedStudent.deleteScore(studentScoreToEdit);
        editedStudent.addScore(editedStudentScore);

        String expectedMessage = String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudentScore(editedStudentScore));
        assertCommandSuccess(editStudentScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEditTag_success() {
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().withTags(VALID_TAG_JAMES).build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                INDEX_FIRST_SCORE, descriptor, useFiltered);

        StudentScore studentScoreToEdit = model.getStudentScoreBook().getStudentScoreList()
                .get(INDEX_FIRST_SCORE.getZeroBased());
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScoreToEdit)
                .withTags(VALID_TAG_JAMES).build();

        Model expectedModel = new ModelManager(getTestStudentBook("create"),
                getSampleStudentScoreBook("twoScores"),
                getTestGcBook("create"), new UserPrefs());
        expectedModel.getStudentScoreBook().setStudentScore(studentScoreToEdit, editedStudentScore);

        Student editedStudent = expectedModel.getStudentBook().getStudentById(editedStudentScore.getStudentId());
        editedStudent.deleteScore(studentScoreToEdit);
        editedStudent.addScore(editedStudentScore);

        String expectedMessage = String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudentScore(editedStudentScore));
        assertCommandSuccess(editStudentScoreCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_validMultipleTags_success() {
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().withTags(VALID_TAG_JAMES, VALID_TAG_AMY).build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                INDEX_FIRST_SCORE, descriptor, useFiltered);

        StudentScore studentScoreToEdit = model.getStudentScoreBook().getStudentScoreList()
                .get(INDEX_FIRST_SCORE.getZeroBased());
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScoreToEdit)
                .withTags(VALID_TAG_JAMES, VALID_TAG_AMY).build();

        Model expectedModel = new ModelManager(getTestStudentBook("create"),
                getSampleStudentScoreBook("twoScores"),
                getTestGcBook("create"), new UserPrefs());
        expectedModel.getStudentScoreBook().setStudentScore(studentScoreToEdit, editedStudentScore);

        Student editedStudent = expectedModel.getStudentBook().getStudentById(editedStudentScore.getStudentId());
        editedStudent.deleteScore(studentScoreToEdit);
        editedStudent.addScore(editedStudentScore);

        String expectedMessage = String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudentScore(editedStudentScore));
        assertCommandSuccess(editStudentScoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTags_success() {
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().withTags(VALID_TAG_JAMES, VALID_TAG_JAMES).build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                INDEX_FIRST_SCORE, descriptor, useFiltered);

        StudentScore studentScoreToEdit = model.getStudentScoreBook().getStudentScoreList()
                .get(INDEX_FIRST_SCORE.getZeroBased());
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScoreToEdit)
                .withTags(VALID_TAG_JAMES, VALID_TAG_JAMES).build();

        Model expectedModel = new ModelManager(getTestStudentBook("create"),
                getSampleStudentScoreBook("twoScores"),
                getTestGcBook("create"), new UserPrefs());
        expectedModel.getStudentScoreBook().setStudentScore(studentScoreToEdit, editedStudentScore);

        Student editedStudent = expectedModel.getStudentBook().getStudentById(editedStudentScore.getStudentId());
        editedStudent.deleteScore(studentScoreToEdit);
        editedStudent.addScore(editedStudentScore);

        String expectedMessage = String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudentScore(editedStudentScore));
        assertCommandSuccess(editStudentScoreCommand, model, expectedMessage, expectedModel);
    }

    // More test on edit component dll

    @Test
    public void execute_validMultipleEditComponents_success() {
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreDescriptorBuilder().withTags(VALID_TAG_JAMES, VALID_TAG_JAMES)
                        .withScore(VALID_SCORE_JAMES).withComment(VALID_COMMENT_JAMES).build();
        EditStudentScoreCommand editStudentScoreCommand = new EditStudentScoreCommand(
                INDEX_FIRST_SCORE, descriptor, useFiltered);

        StudentScore studentScoreToEdit = model.getStudentScoreBook().getStudentScoreList()
                .get(INDEX_FIRST_SCORE.getZeroBased());
        StudentScore editedStudentScore = new StudentScoreBuilder(studentScoreToEdit)
                .withScore(VALID_SCORE_JAMES).withComment(VALID_COMMENT_JAMES)
                .withTags(VALID_TAG_JAMES, VALID_TAG_JAMES).build();

        Model expectedModel = new ModelManager(getTestStudentBook("create"),
                getSampleStudentScoreBook("twoScores"),
                getTestGcBook("create"), new UserPrefs());
        expectedModel.getStudentScoreBook().setStudentScore(studentScoreToEdit, editedStudentScore);

        Student editedStudent = expectedModel.getStudentBook().getStudentById(editedStudentScore.getStudentId());
        editedStudent.deleteScore(studentScoreToEdit);
        editedStudent.addScore(editedStudentScore);

        String expectedMessage = String.format(MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.formatStudentScore(editedStudentScore));
        assertCommandSuccess(editStudentScoreCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void testEquals() {
        final EditStudentScoreCommand editStudentScoreCommand =
                new EditStudentScoreCommand(INDEX_FIRST_SCORE, DESC_AMY_SCORE, useFiltered);
        // same values -> returns true
        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor =
                new EditStudentScoreCommand.EditStudentScoreDescriptor(DESC_AMY_SCORE);
        EditStudentScoreCommand commandSameValue =
                new EditStudentScoreCommand(INDEX_FIRST_SCORE, descriptor, useFiltered);

        assertTrue(editStudentScoreCommand.equals(commandSameValue));

        // sam object -> returns true
        assertTrue(editStudentScoreCommand.equals(editStudentScoreCommand));

        // null -> returns false
        assertFalse(editStudentScoreCommand.equals(null));

        // different types -> returns false
        assertFalse(editStudentScoreCommand.equals(new ExitCommand()));

        //different descriptor -> returns false
        assertFalse(editStudentScoreCommand.equals(
                new EditStudentScoreCommand(INDEX_FIRST_SCORE, DESC_JAMES_SCORE, useFiltered)));


    }

    @Test
    void testToString() {
        final StudentScore sc = model.getFilteredStudentScoreList().get(INDEX_FIRST_SCORE.getZeroBased());
        final EditStudentScoreCommand.EditStudentScoreDescriptor editStudentScoreDescriptor =
                new EditStudentScoreDescriptorBuilder(sc).withScore(VALID_SCORE_AMY).build();
        final StudentScore expectedStudentScore = new StudentScoreBuilder(sc).withScore(VALID_SCORE_AMY).build();

        EditStudentScoreCommand editStudentScoreCommand =
                new EditStudentScoreCommand(INDEX_FIRST_SCORE, editStudentScoreDescriptor, useFiltered);

        String expectedString = EditStudentScoreCommand.class.getCanonicalName()
                + "{editStudentScoreDescriptor="
                + EditStudentScoreCommand.EditStudentScoreDescriptor.class.getCanonicalName()
                + "{student id=" + expectedStudentScore.getStudentId()
                + ", student name=" + "null"
                + ", component name=" + expectedStudentScore.getGcName()
                + ", score=" + expectedStudentScore.getScore()
                + ", comment=" + expectedStudentScore.getComment()
                + ", tags=" + expectedStudentScore.getTags() + "}}";
        assertEquals(editStudentScoreCommand.toString(), expectedString);
    }

    @Test
    public void editStudentDescriptor_equals() {
        // same value -> true
        EditStudentScoreCommand.EditStudentScoreDescriptor editStudentScoreDescriptor =
                new EditStudentScoreDescriptorBuilder().withScore(VALID_SCORE_AMY).build();
        EditStudentScoreCommand.EditStudentScoreDescriptor anotherEditStudentScoreDescriptor =
                new EditStudentScoreCommand.EditStudentScoreDescriptor(editStudentScoreDescriptor);

        assertTrue(editStudentScoreDescriptor.equals(anotherEditStudentScoreDescriptor));

        // same object -> True
        assertTrue(editStudentScoreDescriptor.equals(editStudentScoreDescriptor));

        // null -> false
        assertFalse(editStudentScoreDescriptor.equals(null));

        // different type -> false
        assertFalse(editStudentScoreDescriptor.equals("Edit"));

        // Compared with editStudentCommand -> false
        assertFalse(editStudentScoreDescriptor.equals(
                new EditStudentScoreCommand(INDEX_FIRST_SCORE, editStudentScoreDescriptor, useFiltered)));
    }

    @Test
    public void editStudentDescriptor_toString() {
        final StudentScore sc = model.getFilteredStudentScoreList().get(INDEX_FIRST_SCORE.getZeroBased());
        final EditStudentScoreCommand.EditStudentScoreDescriptor editStudentScoreDescriptor =
                new EditStudentScoreDescriptorBuilder(sc).withScore(VALID_SCORE_AMY).build();
        final StudentScore expectedStudentScore = new StudentScoreBuilder(sc).withScore(VALID_SCORE_AMY).build();

        String expectedString = EditStudentScoreCommand.EditStudentScoreDescriptor.class.getCanonicalName()
                + "{student id=" + expectedStudentScore.getStudentId()
                + ", student name=" + "null"
                + ", component name=" + expectedStudentScore.getGcName()
                + ", score=" + expectedStudentScore.getScore()
                + ", comment=" + expectedStudentScore.getComment()
                + ", tags=" + expectedStudentScore.getTags() + "}";
        assertEquals(editStudentScoreDescriptor.toString(), expectedString);
    }
}
