package seedu.modulight.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.modulight.commons.exceptions.IllegalValueException;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.studentscore.model.ReadOnlyStudentScoreBook;
import seedu.modulight.model.studentscore.model.StudentScoreBook;


/**
 * An Immutable StudentScoreBook that is serializable to JSON format.
 */
@JsonRootName(value = "studentscorebook")
public class JsonSerializableStudentScoreBook {

    public static final String MESSAGE_DUPLICATE_SCORE = "Score list contains duplicate Scores.";

    private final List<JsonAdaptedStudentScore> studentScores = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableStudentScoreBook} with the given Students.
     */
    @JsonCreator
    public JsonSerializableStudentScoreBook(@JsonProperty("studentScores")
                            List<JsonAdaptedStudentScore> studentScores) {
        this.studentScores.addAll(studentScores);
    }

    /**
     * Converts a given {@code ReadOnlyStudentBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableStudentBook}.
     */
    public JsonSerializableStudentScoreBook(ReadOnlyStudentScoreBook source) {
        studentScores.addAll(source.getStudentScoreList().stream().map(JsonAdaptedStudentScore::new)
                             .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudentScoreBook toModelType() throws IllegalValueException {
        StudentScoreBook studentScoreBook = new StudentScoreBook();
        for (JsonAdaptedStudentScore jsonAdaptedStudentScore : studentScores) {
            StudentScore studentScore = jsonAdaptedStudentScore.toModelType();
            if (studentScoreBook.hasStudentScore(studentScore)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SCORE);
            }
            studentScoreBook.addStudentScore(studentScore);
        }
        return studentScoreBook;
    }
}
