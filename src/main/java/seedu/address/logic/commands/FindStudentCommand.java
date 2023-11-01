package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.gradedcomponent.GcMatchPredicate;
import seedu.address.model.student.StudentMatchPredicate;
import seedu.address.model.studentscore.ScoreMatchPredicate;

/**
 * Finds the student(s) whose student id is matching the given Student IDs exactly.
 * Keyword matching is case insensitive.
 */
public class FindStudentCommand extends Command {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose Student IDs match any of "
            + "the specified Student IDs (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: (all parameters are optional)\n"
            + PREFIX_STUDENT_ID + "STUDENT ID"
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_TUTORIAL_GROUP + "TUTORIAL GROUP] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " s/A1234567Z";

    private final StudentMatchPredicate studentPredicate;
    private final ScoreMatchPredicate scorePredicate;
    private final GcMatchPredicate gcPredicate;

    /**
     * Instantiates a new Find student command.
     *
     * @param studentPredicatepredicate the student predicatepredicate
     * @param scorePredicate            the score predicate
     * @param gcPredicate               the gc predicate
     */
    public FindStudentCommand(StudentMatchPredicate studentPredicatepredicate,
                              ScoreMatchPredicate scorePredicate, GcMatchPredicate gcPredicate) {
        this.studentPredicate = studentPredicatepredicate;
        this.scorePredicate = scorePredicate;
        this.gcPredicate = gcPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(studentPredicate);
        model.updateFilteredStudentScoreList(scorePredicate);
        model.updateFilteredGradedComponentList(gcPredicate);
        return new CommandResult(String.format(Messages.MESSAGE_TOTAL_LISTED_OVERVIEW,
                model.getFilteredStudentList().size(),
                model.getFilteredStudentScoreList().size(),
                model.getFilteredGradedComponentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindStudentCommand)) {
            return false;
        }

        FindStudentCommand otherFindCommand = (FindStudentCommand) other;
        return studentPredicate.equals(otherFindCommand.studentPredicate)
                && scorePredicate.equals(otherFindCommand.scorePredicate)
                && gcPredicate.equals((otherFindCommand.gcPredicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("student predicate", studentPredicate)
                .add("score predicate", scorePredicate)
                .add("graded component predicate", gcPredicate)
                .toString();
    }
}

