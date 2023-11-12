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
import seedu.modulight.model.studentscore.model.ReadOnlyStudentScoreBook;


/**
 * A class to access StudentScoreBook data stored as a json file on the hard disk.
 */
public class JsonStudentScoreBookStorage implements StudentScoreBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStudentScoreBookStorage.class);

    private Path filePath;

    public JsonStudentScoreBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getStudentScoreBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyStudentScoreBook> readStudentScoreBook() throws DataLoadingException {
        return readStudentScoreBook(filePath);
    }

    /**
     * Similar to {@link #readStudentScoreBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyStudentScoreBook> readStudentScoreBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableStudentScoreBook> jsonStudentScoreBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableStudentScoreBook.class);
        if (!jsonStudentScoreBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonStudentScoreBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveStudentScoreBook(ReadOnlyStudentScoreBook studentScoreBook) throws IOException {
        saveStudentScoreBook(studentScoreBook, filePath);
    }

    /**
     * Similar to {@link #saveStudentScoreBook(ReadOnlyStudentScoreBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveStudentScoreBook(ReadOnlyStudentScoreBook studentScoreBook, Path filePath) throws IOException {
        requireNonNull(studentScoreBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStudentScoreBook(studentScoreBook), filePath);
    }
}
