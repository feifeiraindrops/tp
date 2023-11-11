package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradedcomponent.GradedComponent;
import seedu.address.model.gradedcomponent.model.GradedComponentBook;
import seedu.address.model.student.Student;
import seedu.address.model.student.model.StudentBook;
import seedu.address.model.studentscore.StudentScore;
import seedu.address.model.studentscore.model.StudentScoreBook;


/**
 * Deletes a Student identified using it's displayed index from the address book.
 */
public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "deleteStu";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    private final Index targetIndex;

    public DeleteStudentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        GradedComponentBook gradedComponentBook = model.getGradedComponentBook();
        StudentBook studentBook = model.getStudentBook();
        StudentScoreBook studentScoreBook = model.getStudentScoreBook();
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student studentToDelete = lastShownList.get(targetIndex.getZeroBased());
        studentBook.removeStudent(studentToDelete);
        List<StudentScore> studentScoreList = studentScoreBook.getStudentScoreList();
        for (int i = studentScoreList.size() - 1; i >= 0; i--) {
            StudentScore curScore = studentScoreList.get(i);
            if (curScore.getStudentId().equals(studentToDelete.getStudentId())) {
                // somewhat inefficient, to change
                GradedComponent gc = gradedComponentBook.getGradedComponentByName(curScore.getGcName());
                gc.deleteScore(curScore);
                gradedComponentBook.setGradedComponent(gc, gc);
                studentScoreBook.removeStudentScore(studentScoreList.get(i));
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, Messages.format(studentToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteCommand = (DeleteStudentCommand) other;

        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
