package seedu.modulight.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.modulight.commons.exceptions.DataLoadingException;
import seedu.modulight.model.student.model.ReadOnlyStudentBook;

/**
 * Represents a storage for {@link seedu.modulight.model.student.model.StudentBook}.
 */
public interface StudentBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStudentBookFilePath();

    /**
     * Returns StudentBook data as a {@link ReadOnlyStudentBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyStudentBook> readStudentBook() throws DataLoadingException;

    /**
     * @see #getStudentBookFilePath()
     */
    Optional<ReadOnlyStudentBook> readStudentBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyStudentBook} to the storage.
     * @param studentBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStudentBook(ReadOnlyStudentBook studentBook) throws IOException;

    /**
     * @see #saveStudentBook(ReadOnlyStudentBook)
     */
    void saveStudentBook(ReadOnlyStudentBook studentBook, Path filePath) throws IOException;
}
