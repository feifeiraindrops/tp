package seedu.modulight.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.logic.Messages;
import seedu.modulight.model.Model;
import seedu.modulight.model.studentscore.ScoreMatchPredicate;

/**
 * The type Find student score command.
 */
public class FindStudentScoreCommand extends Command {
    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "findScore";

    /**
     * The constant MESSAGE_USAGE.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all student score that match any of "
            + "the specified fields and displays them as a list with index numbers.\n"
            + "Parameters: (all parameters are optional)\n"
            + "[" + PREFIX_STUDENT_ID + "STUDENT ID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COMPONENT_NAME + "GRADED COMPONENT NAME] "
            + "[" + PREFIX_TUTORIAL_GROUP + "TUTORIAL GROUP] "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " s/A1234567Z" + " c/midterm";

    private final ScoreMatchPredicate scorePredicate;

    /**
     * Instantiates a new Find student command.
     *
     * @param scorePredicate the score predicate
     */
    public FindStudentScoreCommand(ScoreMatchPredicate scorePredicate) {
        this.scorePredicate = scorePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentScoreList(scorePredicate);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_NO_STUDENTS);
        model.updateFilteredGradedComponentList(Model.PREDICATE_SHOW_NO_COMPONENT);
        return new CommandResult(String.format(Messages.MESSAGE_SCORE_LISTED_OVERVIEW,
                model.getFilteredStudentScoreList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindStudentScoreCommand)) {
            return false;
        }

        FindStudentScoreCommand otherFindCommand = (FindStudentScoreCommand) other;
        return scorePredicate.equals(otherFindCommand.scorePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("score predicate", scorePredicate)
                .toString();
    }
}
