package seedu.modulight.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_REVERSE;

import java.util.function.Predicate;

import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.logic.Messages;
import seedu.modulight.model.Model;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.model.StudentBook;

/**
 * Sorts the student(s) whose student id by the given order.
 */
public class SortStudentCommand extends Command {
    public static final String COMMAND_WORD = "sortStu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all students by the given order.\n"
            + "Parameters:\n"
            + "[" + PREFIX_ORDER + "attribute to be sorted (optional, by default sorted by overall score)]: \n"
            + "Must be one of \"n\", \"name\", \"s\", \"studentId\", "
            + "\"studentID\", \"e\", \"email\", \"g\", \"tutorial\", \"tut\", \"tutGroup\""
            + "\"ts\", \"totalScore\", \"totalscore\", \"overall\", \"score\"\n"
            + "[" + PREFIX_REVERSE + "Reverse (optional, by default increasing)]:\n"
            + "If true, the sorted list is reversed (or sorted in Descending order)\n"
            + "Example: " + COMMAND_WORD + " o/s r/true";

    private final String sortingOrder;
    private final boolean isReverse;

    /**
     * Creates an SortStudentCommand to sort the displayed students.
     */
    public SortStudentCommand(String sortingOrder, boolean isReverse) {
        this.sortingOrder = sortingOrder;
        this.isReverse = isReverse;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Student> currentPredicate = model.getCurrentStudentsPredicate();
        StudentBook studentBook = model.getStudentBook();
        studentBook.sortStudent(sortingOrder, isReverse);
        if (currentPredicate != null) {
            model.updateFilteredStudentList(currentPredicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_SORTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortStudentCommand)) {
            return false;
        }

        SortStudentCommand otherSortCommand = (SortStudentCommand) other;
        return sortingOrder.equals(otherSortCommand.sortingOrder)
                && isReverse == otherSortCommand.isReverse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("by ", sortingOrder)
                .toString();
    }
}

