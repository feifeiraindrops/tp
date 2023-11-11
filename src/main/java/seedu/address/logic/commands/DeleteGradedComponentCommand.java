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
 * Deletes a gradedComponent identified using it's displayed index from the address book.
 */
public class DeleteGradedComponentCommand extends Command {

    public static final String COMMAND_WORD = "deleteComp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the gradedComponent identified by the index number used "
            + "in the displayed gradedComponent list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_COMPONENT_SUCCESS = "Deleted GradedComponent: %1$s";

    private final Index targetIndex;

    public DeleteGradedComponentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        GradedComponentBook gradedComponentBook = model.getGradedComponentBook();
        StudentBook studentBook = model.getStudentBook();
        StudentScoreBook studentScoreBook = model.getStudentScoreBook();
        List<GradedComponent> lastShownList = model.getFilteredGradedComponentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        GradedComponent gradedComponentToDelete = lastShownList.get(targetIndex.getZeroBased());
        gradedComponentBook.removeGradedComponent(gradedComponentToDelete);
        List<StudentScore> studentScoreList = studentScoreBook.getStudentScoreList();
        for (int i = studentScoreList.size() - 1; i >= 0; i--) {
            StudentScore curScore = studentScoreList.get(i);
            if (curScore.getGcName().equals(gradedComponentToDelete.getName())) {
                // somewhat inefficient, to change
                Student student = studentBook.getStudentById(curScore.getStudentId());
                student.deleteScore(curScore);
                studentBook.setStudent(student, student);
                studentScoreBook.removeStudentScore(curScore);
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_COMPONENT_SUCCESS,
                Messages.formatGradedComponent(gradedComponentToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGradedComponentCommand)) {
            return false;
        }

        DeleteGradedComponentCommand otherDeleteCommand = (DeleteGradedComponentCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

