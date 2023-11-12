package seedu.modulight.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.modulight.commons.exceptions.DataLoadingException;
import seedu.modulight.model.ReadOnlyUserPrefs;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook;
import seedu.modulight.model.student.model.ReadOnlyStudentBook;
import seedu.modulight.model.studentscore.model.ReadOnlyStudentScoreBook;

/**
 * API of the Storage component
 */
public interface Storage extends StudentBookStorage, UserPrefsStorage, StudentScoreBookStorage,
    GradedComponentBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getStudentBookFilePath();

    @Override
    Optional<ReadOnlyStudentBook> readStudentBook() throws DataLoadingException;

    @Override
    void saveStudentBook(ReadOnlyStudentBook addressBook) throws IOException;

    @Override
    Path getStudentScoreBookFilePath();

    @Override
    Optional<ReadOnlyStudentScoreBook> readStudentScoreBook() throws DataLoadingException;

    @Override
    void saveStudentScoreBook(ReadOnlyStudentScoreBook addressBook) throws IOException;

    @Override
    Path getGcBookFilePath();

    @Override
    Optional<ReadOnlyGradedComponentBook> readGradedComponentBook() throws DataLoadingException;

    @Override
    void saveGradedComponentBook(ReadOnlyGradedComponentBook gradedComponentBook) throws IOException;

}
