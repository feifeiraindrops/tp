package seedu.modulight.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.modulight.commons.core.LogsCenter;
import seedu.modulight.commons.exceptions.DataLoadingException;
import seedu.modulight.model.ReadOnlyUserPrefs;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook;
import seedu.modulight.model.student.model.ReadOnlyStudentBook;
import seedu.modulight.model.studentscore.model.ReadOnlyStudentScoreBook;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private StudentBookStorage studentBookStorage;
    private StudentScoreBookStorage studentScoreBookStorage;
    private GradedComponentBookStorage gradedComponentBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(StudentBookStorage studentBookStorage, StudentScoreBookStorage studentScoreBookStorage,
        GradedComponentBookStorage gradedComponentBookStorage, UserPrefsStorage userPrefsStorage) {
        this.studentBookStorage = studentBookStorage;
        this.studentScoreBookStorage = studentScoreBookStorage;
        this.gradedComponentBookStorage = gradedComponentBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getStudentBookFilePath() {
        return studentBookStorage.getStudentBookFilePath();
    }

    @Override
    public Path getStudentScoreBookFilePath() {
        return studentScoreBookStorage.getStudentScoreBookFilePath();
    }

    @Override
    public Path getGcBookFilePath() {
        return gradedComponentBookStorage.getGcBookFilePath();
    }

    @Override
    public Optional<ReadOnlyStudentBook> readStudentBook() throws DataLoadingException {
        return readStudentBook(studentBookStorage.getStudentBookFilePath());
    }

    @Override
    public Optional<ReadOnlyStudentBook> readStudentBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return studentBookStorage.readStudentBook(filePath);
    }

    @Override
    public void saveStudentBook(ReadOnlyStudentBook studentBook) throws IOException {
        saveStudentBook(studentBook, studentBookStorage.getStudentBookFilePath());
    }

    @Override
    public void saveStudentBook(ReadOnlyStudentBook studentBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        studentBookStorage.saveStudentBook(studentBook, filePath);
    }

    @Override
    public Optional<ReadOnlyStudentScoreBook> readStudentScoreBook() throws DataLoadingException {
        return readStudentScoreBook(studentScoreBookStorage.getStudentScoreBookFilePath());
    }

    @Override
    public Optional<ReadOnlyStudentScoreBook> readStudentScoreBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return studentScoreBookStorage.readStudentScoreBook(filePath);
    }

    @Override
    public void saveStudentScoreBook(ReadOnlyStudentScoreBook studentScoreBook) throws IOException {
        saveStudentScoreBook(studentScoreBook, studentScoreBookStorage.getStudentScoreBookFilePath());
    }

    @Override
    public void saveStudentScoreBook(ReadOnlyStudentScoreBook studentScoreBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        studentScoreBookStorage.saveStudentScoreBook(studentScoreBook, filePath);
    }

    @Override
    public Optional<ReadOnlyGradedComponentBook> readGradedComponentBook() throws DataLoadingException {
        return readGradedComponentBook(gradedComponentBookStorage.getGcBookFilePath());
    }

    @Override
    public Optional<ReadOnlyGradedComponentBook> readGradedComponentBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return gradedComponentBookStorage.readGradedComponentBook(filePath);
    }

    @Override
    public void saveGradedComponentBook(ReadOnlyGradedComponentBook gradedComponentBook) throws IOException {
        saveGradedComponentBook(gradedComponentBook, gradedComponentBookStorage.getGcBookFilePath());
    }

    @Override
    public void saveGradedComponentBook(ReadOnlyGradedComponentBook gradedComponentBook, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        gradedComponentBookStorage.saveGradedComponentBook(gradedComponentBook, filePath);
    }

}
