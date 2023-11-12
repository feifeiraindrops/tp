package seedu.modulight.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.modulight.commons.exceptions.IllegalValueException;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.MaxMarks;
import seedu.modulight.model.gradedcomponent.Weightage;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.tag.Tag;


/**
 * Jackson-friendly version of {@link StudentScore}.
 */
public class JsonAdaptedStudentScore {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Score's %s field is missing!";
    private static final String DEFAULT_MAX = "100";
    private static final String DEFAULT_WEIGHTAGE = "100";

    private final String studentId;
    private final String gcName;
    private final String gcMaxMarks;
    private final String gcWeightage;
    private final String score;
    private final String comment;

    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given Student details.
     */
    @JsonCreator
    public JsonAdaptedStudentScore(@JsonProperty("studentId") String studentId,
                                   @JsonProperty("gradedComponentName") String gcName,
                                   @JsonProperty("gradedComponentMaximumMarks") String gcMax,
                                   @JsonProperty("gradedComponentWeightage") String gcWeightage,
                                   @JsonProperty("score") String score,
                                   @JsonProperty("comment") String comment,
                                   @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.studentId = studentId;
        this.gcName = gcName;
        this.gcMaxMarks = gcMax;
        this.gcWeightage = gcWeightage;
        this.score = score;
        this.comment = comment;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudentScore(StudentScore source) {
        studentId = source.getStudentId().sid;
        gcName = source.getGcName().gcName;
        gcMaxMarks = DEFAULT_MAX;
        gcWeightage = DEFAULT_WEIGHTAGE;
        score = String.valueOf(source.getScore());
        comment = source.getComment();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Student.
     */
    public StudentScore toModelType() throws IllegalValueException {
        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidSid(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        final StudentId modelStudentId = new StudentId(studentId);

        final GcName newGcName = new GcName(gcName);
        final GradedComponent gradedComponent = new GradedComponent(newGcName,
                new MaxMarks(Float.parseFloat(gcMaxMarks)), new Weightage(Float.parseFloat(gcWeightage)));

        //check validity of scores
        final float modelScore = Float.parseFloat(score);

        final String modelComment = comment;

        final List<Tag> studentScoreTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            studentScoreTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(studentScoreTags);

        return new StudentScore(modelStudentId, newGcName, modelScore, modelComment, modelTags);
    }

}
