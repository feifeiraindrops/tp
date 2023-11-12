package seedu.modulight.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.logic.commands.exceptions.CommandException;
import seedu.modulight.model.Model;



/**
 * Clears all students, student scores and graded components data from the address book.
 */
public class ClearAllCommand extends Command {

    public static final String COMMAND_WORD = "clearAll";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all data. \n "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_CLEAR_SUCCESS = "Cleared all data successfully";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.getStudentBook().clearStudent();
        model.getStudentScoreBook().clearStudentScore();
        model.getGradedComponentBook().clearGradedComponent();
        return new CommandResult(String.format(MESSAGE_CLEAR_SUCCESS));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}

