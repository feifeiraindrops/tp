package seedu.modulight;

import static seedu.modulight.commons.util.ModelUtil.MESSAGE_INCORRECT_ENTITY_COUNT;
import static seedu.modulight.commons.util.ModelUtil.MESSAGE_WEIGHTAGES_MORE_THAN_100;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.modulight.commons.core.Config;
import seedu.modulight.commons.core.LogsCenter;
import seedu.modulight.commons.core.Version;
import seedu.modulight.commons.exceptions.DataLoadingException;
import seedu.modulight.commons.util.ConfigUtil;
import seedu.modulight.commons.util.ModelUtil;
import seedu.modulight.commons.util.StringUtil;
import seedu.modulight.logic.Logic;
import seedu.modulight.logic.LogicManager;
import seedu.modulight.model.Model;
import seedu.modulight.model.ModelManager;
import seedu.modulight.model.ReadOnlyUserPrefs;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.model.GradedComponentBook;
import seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.model.ReadOnlyStudentBook;
import seedu.modulight.model.student.model.StudentBook;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.studentscore.model.ReadOnlyStudentScoreBook;
import seedu.modulight.model.studentscore.model.StudentScoreBook;
import seedu.modulight.model.util.SampleGcDataUtil;
import seedu.modulight.model.util.SampleStudentDataUtil;
import seedu.modulight.model.util.SampleStudentScoreDataUtil;
import seedu.modulight.storage.GradedComponentBookStorage;
import seedu.modulight.storage.JsonGradedComponentBookStorage;
import seedu.modulight.storage.JsonStudentBookStorage;
import seedu.modulight.storage.JsonStudentScoreBookStorage;
import seedu.modulight.storage.JsonUserPrefsStorage;
import seedu.modulight.storage.Storage;
import seedu.modulight.storage.StorageManager;
import seedu.modulight.storage.StudentBookStorage;
import seedu.modulight.storage.StudentScoreBookStorage;
import seedu.modulight.storage.UserPrefsStorage;
import seedu.modulight.ui.Ui;
import seedu.modulight.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        StudentBookStorage studentBookStorage = new JsonStudentBookStorage(userPrefs.getStudentBookFilePath());
        StudentScoreBookStorage studentScoreBookStorage =
                new JsonStudentScoreBookStorage(userPrefs.getStudentScoreBookFilePath());
        GradedComponentBookStorage gradedComponentBookStorage =
            new JsonGradedComponentBookStorage(userPrefs.getGcBookFilePath());
        storage = new StorageManager(studentBookStorage, studentScoreBookStorage, gradedComponentBookStorage,
            userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getStudentBookFilePath());

        Optional<ReadOnlyStudentBook> studentBookOptional;
        Optional<ReadOnlyStudentScoreBook> studentScoreBookOptional;
        Optional<ReadOnlyGradedComponentBook> gradedComponentBookOptional;
        ReadOnlyStudentBook initialStudentData = new StudentBook();
        ReadOnlyStudentScoreBook initialStudentScoreData;
        ReadOnlyGradedComponentBook initialGradedComponentData;
        try {
            studentBookOptional = storage.readStudentBook();
            studentScoreBookOptional = storage.readStudentScoreBook();
            gradedComponentBookOptional = storage.readGradedComponentBook();
            if (!studentBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getStudentBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            if (!studentScoreBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getStudentBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            if (!gradedComponentBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getGcBookFilePath()
                    + " populated with a sample " + "GradedComponentBook");
            }

            initialStudentData = studentBookOptional.orElseGet(SampleStudentDataUtil::getSampleStudentBook);
            initialStudentScoreData = studentScoreBookOptional
                .orElseGet(SampleStudentScoreDataUtil::getSampleStudentScoreBook);
            initialGradedComponentData = gradedComponentBookOptional
                .orElseGet(SampleGcDataUtil::getSampleGcBook);
            checkBookValidity(initialStudentData, initialGradedComponentData, initialStudentScoreData);

        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getStudentBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialStudentData = new StudentBook();
            initialStudentScoreData = new StudentScoreBook();
            initialGradedComponentData = new GradedComponentBook();
        }

        return new ModelManager(initialStudentData, initialStudentScoreData,
                initialGradedComponentData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    private void checkBookValidity(ReadOnlyStudentBook studentBook, ReadOnlyGradedComponentBook gcBook,
                                   ReadOnlyStudentScoreBook ssb) {
        List<Student> studentList = studentBook.getStudentList();
        List<GradedComponent> gcList = gcBook.getGradedComponentList();
        List<StudentScore> studentScoreList = ssb.getStudentScoreList();
        if (ModelUtil.weightageSum(gcBook) > 100) {
            throw new RuntimeException(MESSAGE_WEIGHTAGES_MORE_THAN_100);
        }
        if (studentList.size() * gcList.size() != studentScoreList.size()) {
            throw new RuntimeException(MESSAGE_INCORRECT_ENTITY_COUNT);
        }

    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
