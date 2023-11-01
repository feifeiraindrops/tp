package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentEmail;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.StudentName;
import seedu.address.model.student.TutorialGroup;
import seedu.address.model.studentscore.StudentScore;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Student}.
 */
public class JsonAdaptedStudent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String studentId;
    private final String studentName;
    private final String studentEmail;
    private final String tutorialGroup;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("studentId") String studentId,
                              @JsonProperty("studentName") String studentName,
                              @JsonProperty("studentEmail") String studentEmail,
                              @JsonProperty("tutorialGroup") String tutorialGroup,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.tutorialGroup = tutorialGroup;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        studentId = source.getStudentId().sid;
        studentName = source.getName().fullName;
        studentEmail = source.getEmail().value;
        tutorialGroup = source.getTutorial().groupName;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Student toModelType() throws IllegalValueException {
        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidName(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        final StudentId modelStudentId = new StudentId(studentId);

        if (studentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                StudentName.class.getSimpleName()));
        }
        if (!StudentName.isValidName(studentName)) {
            throw new IllegalValueException(StudentName.MESSAGE_CONSTRAINTS);
        }
        final StudentName modelStudentName = new StudentName(studentName);

        if (studentEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                StudentEmail.class.getSimpleName()));
        }
        if (!StudentEmail.isValidEmail(studentEmail)) {
            throw new IllegalValueException(StudentEmail.MESSAGE_CONSTRAINTS);
        }
        final StudentEmail modelStudentEmail = new StudentEmail(studentEmail);

        if (tutorialGroup == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TutorialGroup.class.getSimpleName()));
        }
        if (!TutorialGroup.isValidTutorial(tutorialGroup)) {
            throw new IllegalValueException(TutorialGroup.MESSAGE_CONSTRAINTS);
        }
        final TutorialGroup modelTutorialGroup = new TutorialGroup(tutorialGroup);
        final List<Tag> studentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            studentTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(studentTags);

        final List<StudentScore> modelStudentScores = new ArrayList<>();

        return new Student(modelStudentId, modelStudentName, modelStudentEmail, modelTutorialGroup,
            modelStudentScores, modelTags);
    }

}
