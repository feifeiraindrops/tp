package seedu.modulight.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.modulight.commons.core.GuiSettings;
import seedu.modulight.logic.commands.CommandResult;
import seedu.modulight.logic.commands.exceptions.CommandException;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.studentscore.StudentScore;


/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;


    /** Returns an unmodifiable view of the filtered list of students */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered list of graded components */
    ObservableList<GradedComponent> getFilteredGradedComponentList();

    /** Returns an unmodifiable view of the filtered list of student scores */
    ObservableList<StudentScore> getFilteredStudentScoreList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getApplicationFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
