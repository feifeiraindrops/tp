package seedu.modulight.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.modulight.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.DESC_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_NAME_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TG_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.modulight.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modulight.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.modulight.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.modulight.testutil.TypicalStudents.getTypicalStudentBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.Messages;
import seedu.modulight.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.modulight.logic.commands.exceptions.CommandException;
import seedu.modulight.model.Model;
import seedu.modulight.model.ModelManager;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.model.gradedcomponent.model.GradedComponentBook;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.StudentMatchPredicate;
import seedu.modulight.model.student.model.StudentBook;
import seedu.modulight.model.studentscore.model.StudentScoreBook;
import seedu.modulight.testutil.EditStudentDescriptorBuilder;
import seedu.modulight.testutil.StudentBuilder;

public class EditStudentCommandTest {
    private Model model = new ModelManager(getTypicalStudentBook(), new StudentScoreBook(),
            new GradedComponentBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student editedStudent = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(INDEX_FIRST_STUDENT, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));
        Model expectedModel = new ModelManager(new StudentBook(model.getStudentBook()), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());
        expectedModel.getStudentBook().setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student editedStudent = studentInList.withName(VALID_NAME_JAMES).withTg(VALID_TG_JAMES).build();

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_JAMES)
                .withTg(VALID_TG_JAMES).build();
        EditStudentCommand editCommand = new EditStudentCommand(indexLastStudent, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new StudentBook(model.getStudentBook()), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());
        expectedModel.getStudentBook().setStudent(lastStudent, editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_STUDENT, new EditStudentDescriptor());
        Student editedStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new StudentBook(model.getStudentBook()), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_filteredList_success() {
        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList).withName(VALID_NAME_JAMES).build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_STUDENT,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_JAMES).build());

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS,
                Messages.format(editedStudent));

        Model expectedModel = new ModelManager(new StudentBook(model.getStudentBook()), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());
        expectedModel.getStudentBook().setStudent(model.getFilteredStudentList().get(0), editedStudent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudentUnfilteredList_failure() {
        Model modelCopy = new ModelManager(getTypicalStudentBook(), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(firstStudent).build();
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_SECOND_STUDENT, descriptor);
        assertCommandFailure(editCommand, model, new CommandException(EditStudentCommand.MESSAGE_DUPLICATE_STUDENT),
                modelCopy);
    }

    @Test
    public void execute_duplicateStudentFilteredList_failure() {
        Model modelCopy = new ModelManager(getTypicalStudentBook(), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());
        // edit student in filtered list into a duplicate in modulight book
        Student studentInList = model.getStudentBook().getStudentList().get(INDEX_SECOND_STUDENT.getZeroBased());
        EditStudentCommand editCommand = new EditStudentCommand(INDEX_FIRST_STUDENT,
                new EditStudentDescriptorBuilder(studentInList).build());

        assertCommandFailure(editCommand, model, new CommandException(EditStudentCommand.MESSAGE_DUPLICATE_STUDENT),
                modelCopy);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Model modelCopy = new ModelManager(getTypicalStudentBook(), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 2);
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_JAMES).build();
        EditStudentCommand editCommand = new EditStudentCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX),
                modelCopy);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of modulight book
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        Model modelCopy = new ModelManager(getTypicalStudentBook(), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());
        List<String> emptyKeywords = new ArrayList<>();
        List<String> noMatchKeywords = new ArrayList<>(List.of("no match"));
        StudentMatchPredicate noMatchPredicate = new StudentMatchPredicate(noMatchKeywords,
                emptyKeywords, emptyKeywords, emptyKeywords,
                emptyKeywords);
        model.updateFilteredStudentList(noMatchPredicate);
        modelCopy.updateFilteredStudentList(noMatchPredicate);
        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        // ensures that outOfBoundIndex is still in bounds of modulight book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getStudentBook().getStudentList().size());

        EditStudentCommand editCommand = new EditStudentCommand(outOfBoundIndex,
                new EditStudentDescriptorBuilder().withName(VALID_NAME_JAMES).build());

        assertCommandFailure(editCommand, model, new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX),
                modelCopy);
    }

    @Test
    public void equals() {
        final EditStudentCommand standardCommand = new EditStudentCommand(INDEX_FIRST_STUDENT, DESC_AMY);

        // same values -> returns true
        EditStudentDescriptor copyDescriptor = new EditStudentDescriptor(DESC_AMY);
        EditStudentCommand commandWithSameValues = new EditStudentCommand(INDEX_FIRST_STUDENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearAllCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_SECOND_STUDENT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(INDEX_FIRST_STUDENT, DESC_JAMES)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();
        EditStudentCommand editCommand = new EditStudentCommand(index, editStudentDescriptor);
        String expected = EditStudentCommand.class.getCanonicalName() + "{index=" + index + ", editStudentDescriptor="
                + editStudentDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }
}
