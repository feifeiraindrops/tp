package seedu.modulight.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.logic.Messages;
import seedu.modulight.model.Model;
import seedu.modulight.model.student.StudentMatchPredicate;
import seedu.modulight.model.studentscore.ScoreMatchPredicate;

/**
 * Finds the student(s) whose student id is matching the given Student IDs exactly.
 * Keyword matching is case insensitive.
 */
public class FindStudentCommand extends Command {
    public static final String COMMAND_WORD = "findStu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose information match any of "
            + "the specified field and displays them as a list with index numbers.\n"
            + "Parameters: (all parameters are optional, but at least one should be present)\n"
            + "[" + PREFIX_STUDENT_ID + "STUDENT ID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TUTORIAL_GROUP + "TUTORIAL GROUP] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " s/A1234567Z";

    private final StudentMatchPredicate studentPredicate;

    private final ScoreMatchPredicate scorePredicate;

    /**
     * Instantiates a new Find student command.
     *
     * @param studentPredicate the student predicate
     * @param scorePredicate   the score predicate
     */
    public FindStudentCommand(StudentMatchPredicate studentPredicate, ScoreMatchPredicate scorePredicate) {
        this.studentPredicate = studentPredicate;
        this.scorePredicate = scorePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(studentPredicate);
        model.updateFilteredStudentScoreList(scorePredicate);
        String feedback = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW,
                model.getFilteredStudentList().size()) + "\n"
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
        if (!(other instanceof FindStudentCommand)) {
            return false;
        }

        FindStudentCommand otherFindCommand = (FindStudentCommand) other;
        return studentPredicate.equals(otherFindCommand.studentPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("student predicate", studentPredicate)
                .toString();
    }
}

