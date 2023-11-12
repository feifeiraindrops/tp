package seedu.modulight.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;

import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.logic.Messages;
import seedu.modulight.model.Model;
import seedu.modulight.model.gradedcomponent.GcMatchPredicate;
import seedu.modulight.model.studentscore.ScoreMatchPredicate;

/**
 * The type Find graded component command.
 */
public class FindGradedComponentCommand extends Command {
    public static final String COMMAND_WORD = "findComp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all graded components whose name contains "
            + "the specified graded component names and displays them as a list with index numbers."
            + "The relevant student scores and all students will be displayed as well. \n"
            + "Parameters: \n"
            + PREFIX_COMPONENT_NAME + "GRADED COMPONENT NAME \n"
            + "Example: " + COMMAND_WORD + "c/midterms";

    private final GcMatchPredicate gcPredicate;
    private final ScoreMatchPredicate scorePredicate;

    /**
     * Instantiates a new Find student command.
     *
     * @param gcPredicate the gc predicate
     */
    public FindGradedComponentCommand(GcMatchPredicate gcPredicate, ScoreMatchPredicate scorePredicate) {
        this.gcPredicate = gcPredicate;
        this.scorePredicate = scorePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGradedComponentList(gcPredicate);
        model.updateFilteredStudentScoreList(scorePredicate);
        String feedback = String.format(Messages.MESSAGE_COMP_LISTED_OVERVIEW,
                model.getFilteredGradedComponentList().size()) + "\n"
                + String.format(Messages.MESSAGE_SCORE_LISTED_OVERVIEW,
                model.getFilteredStudentScoreList().size());
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindGradedComponentCommand)) {
            return false;
        }

        FindGradedComponentCommand otherFindCommand = (FindGradedComponentCommand) other;
        return gcPredicate.equals((otherFindCommand.gcPredicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("graded component predicate", gcPredicate)
                .toString();
    }
}
