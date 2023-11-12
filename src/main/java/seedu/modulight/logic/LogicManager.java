package seedu.modulight.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.modulight.commons.core.GuiSettings;
import seedu.modulight.commons.core.LogsCenter;
import seedu.modulight.logic.commands.Command;
import seedu.modulight.logic.commands.CommandResult;
import seedu.modulight.logic.commands.exceptions.CommandException;
import seedu.modulight.logic.parser.ModuLightParser;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.Model;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ModuLightParser moduLightParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        moduLightParser = new ModuLightParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = moduLightParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveStudentBook(model.getStudentBook());
            storage.saveStudentScoreBook(model.getStudentScoreBook());
            storage.saveGradedComponentBook(model.getGradedComponentBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }


        return commandResult;
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return model.getFilteredStudentList();
    }

    @Override
    public ObservableList<GradedComponent> getFilteredGradedComponentList() {
        return model.getFilteredGradedComponentList();
    }

    @Override
    public ObservableList<StudentScore> getFilteredStudentScoreList() {
        return model.getFilteredStudentScoreList();
    }

    @Override
    public Path getApplicationFilePath() {
        return model.getApplicationFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
