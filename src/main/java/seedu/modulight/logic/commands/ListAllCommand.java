package seedu.modulight.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.model.Model.PREDICATE_SHOW_ALL_GRADED_COMPONENTS;
import static seedu.modulight.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.modulight.model.Model.PREDICATE_SHOW_ALL_STUDENT_SCORES;

import seedu.modulight.model.Model;

/**
 * The type List all command.
 */
public class ListAllCommand extends Command {
    public static final String COMMAND_WORD = "listAll";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all students, student scores and graded components.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all students, student scores and graded components";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredGradedComponentList(PREDICATE_SHOW_ALL_GRADED_COMPONENTS);
        model.updateFilteredStudentScoreList(PREDICATE_SHOW_ALL_STUDENT_SCORES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
