package seedu.modulight.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.modulight.commons.core.LogsCenter;
import seedu.modulight.commons.exceptions.DataLoadingException;
import seedu.modulight.commons.exceptions.IllegalValueException;
import seedu.modulight.commons.util.FileUtil;
import seedu.modulight.commons.util.JsonUtil;
import seedu.modulight.model.gradedcomponent.model.ReadOnlyGradedComponentBook;

/**
 * A class to access GradedComponentBook data stored as a json file on the hard disk.
 */
public class JsonGradedComponentBookStorage implements GradedComponentBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGradedComponentBookStorage.class);

    private Path filePath;

    public JsonGradedComponentBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGcBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGradedComponentBook> readGradedComponentBook() throws DataLoadingException {
        return readGradedComponentBook(filePath);
    }

    /**
     * Similar to {@link #readGradedComponentBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyGradedComponentBook> readGradedComponentBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableGcBook> jsonGradedComponentBook = JsonUtil.readJsonFile(
            filePath, JsonSerializableGcBook.class);
        if (!jsonGradedComponentBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGradedComponentBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveGradedComponentBook(ReadOnlyGradedComponentBook gradedComponentBook) throws IOException {
        saveGradedComponentBook(gradedComponentBook, filePath);
    }

    /**
     * Similar to {@link #saveGradedComponentBook(ReadOnlyGradedComponentBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGradedComponentBook(ReadOnlyGradedComponentBook gradedComponentBook, Path filePath)
            throws IOException {
        requireNonNull(gradedComponentBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGcBook(gradedComponentBook), filePath);
    }
}
