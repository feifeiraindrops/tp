package seedu.modulight.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_REVERSE;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.logic.Messages;
import seedu.modulight.model.Model;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.model.StudentBook;
import seedu.modulight.model.studentscore.ExactScoreMatchPredicate;
import seedu.modulight.model.studentscore.model.StudentScoreBook;

/**
 * Sorts the student(s) whose student id by the given order.
 */
public class SortStudentScoreCommand extends Command {
    public static final String COMMAND_WORD = "sortScore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all students scores under a given existing graded component.\n"
            + "Parameters:\n"
            + PREFIX_COMPONENT_NAME + "COMPONENT NAME\n"
            + "[" + PREFIX_REVERSE + "Reverse (optional, by default increasing)]: "
            + "If true, the sorted list is reversed (or sorted in Descending order)\n"
            + "Example: " + COMMAND_WORD + " c/Midterm r/true";
    public static final String MESSAGE_NO_GC = "This graded component is not created. "
            + "Please check if the information is correct\n";

    private final GcName gcName;
    private final boolean isReverse;

    /**
     * Creates a SortStudentCommand to sort the displayed students.
     */
    public SortStudentScoreCommand(GcName gcName, boolean isReverse) {
        this.gcName = gcName;
        this.isReverse = isReverse;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Student> currentStuPredicate = model.getCurrentStudentsPredicate();
        StudentBook studentBook = model.getStudentBook();
        StudentScoreBook studentScoreBook = model.getStudentScoreBook();
        List<GradedComponent> gradedComponents = model.getGradedComponentBook().getGradedComponentList();
        boolean isCreated = gradedComponents.stream().map(gc -> gc.getName())
                .anyMatch(name -> name.equals(gcName));
        if (!isCreated) {
            return new CommandResult(MESSAGE_NO_GC);
        }
        studentScoreBook.sortStudentScore(isReverse);
        studentBook.sortStudentScore(gcName, isReverse);
        model.updateFilteredStudentScoreList(new ExactScoreMatchPredicate(Collections.singletonList(gcName)));
        if (currentStuPredicate != null) {
            model.updateFilteredStudentList(currentStuPredicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_SCORES_SORTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortStudentScoreCommand)) {
            return false;
        }

        SortStudentScoreCommand otherSortCommand = (SortStudentScoreCommand) other;
        return gcName.equals(otherSortCommand.gcName) && isReverse == otherSortCommand.isReverse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("of graded component ", gcName.toString())
                .toString();
    }
}

