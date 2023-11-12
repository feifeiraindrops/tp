package seedu.modulight.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.modulight.commons.exceptions.DataLoadingException;
import seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook;

/**
 * Represents a storage for {@link seedu.modulight.model.gradedcomponent.model.GradedComponentBook}.
 */
public interface GradedComponentBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGcBookFilePath();

    /**
     * Returns StudentBook data as a {@link seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyGradedComponentBook> readGradedComponentBook() throws DataLoadingException;

    /**
     * @see #getGcBookFilePath()
     */
    Optional<ReadOnlyGradedComponentBook> readGradedComponentBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyGradedComponentBook} to the storage.
     * @param gradedComponentBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGradedComponentBook(ReadOnlyGradedComponentBook gradedComponentBook) throws IOException;

    /**
     * @see #saveGradedComponentBook(ReadOnlyGradedComponentBook)
     */
    void saveGradedComponentBook(ReadOnlyGradedComponentBook gradedComponentBook, Path filePath) throws IOException;
}
