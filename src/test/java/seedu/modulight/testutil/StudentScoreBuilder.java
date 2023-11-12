package seedu.modulight.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.tag.Tag;

/**
 * A utility class to help with building Student Score object
 */
public class StudentScoreBuilder {
    public static final String DEFAULT_STUDENT_ID = "A1234567R";
    public static final String DEFAULT_GCNAME = "Midterm";
    public static final float DEFAULT_SCORE = (float) 1.1;
    public static final String DEFAULT_COMMENT = "Nice!";


    private StudentId sid;
    private StudentName name;
    private GcName gcName;
    private float score;
    private String comment;
    private Student student;
    private GradedComponent gc;
    private Set<Tag> tags;

    /**
     * Creates StudentScoreBuilder with default details.
     */
    public StudentScoreBuilder() {
        sid = new StudentId(DEFAULT_STUDENT_ID);
        gcName = new GcName(DEFAULT_GCNAME);
        score = DEFAULT_SCORE;
        comment = DEFAULT_COMMENT;
        tags = new HashSet<>();
    }

    /**
     * Create StudentScoreBuilder with and existing studentScore.
     * @param studentScoreToCopy
     */
    public StudentScoreBuilder(StudentScore studentScoreToCopy) {
        sid = studentScoreToCopy.getStudentId();
        gcName = studentScoreToCopy.getGcName();
        score = studentScoreToCopy.getScore();
        comment = studentScoreToCopy.getComment();
        tags = studentScoreToCopy.getTags();
    }

    /**
     * Return StudentScoreBuilder with newly set studentId
     * @param studentId studentId
     * @return Edited StudentScoreBuilder
     */
    public StudentScoreBuilder withStudentId(String studentId) {
        this.sid = new StudentId(studentId);
        return this;
    }

    /**
     * Return StudentScoreBuilder with newly set gcName
     * @param gcName gcName
     * @return Edited StudentScoreBuilder
     */
    public StudentScoreBuilder withGcName(String gcName) {
        this.gcName = new GcName(gcName);
        return this;
    }

    /**
     * Return StudentScoreBuilder with newly set score
     * @param score score
     * @return Edited StudentScoreBuilder
     */
    public StudentScoreBuilder withScore(float score) {
        this.score = score;
        return this;
    }

    /**
     * Return StudentScoreBuilder with newly set comment
     * @param comment comment
     * @return Edited StudentScoreBuilder
     */
    public StudentScoreBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Return StudentScoreBuilder with newly set tags
     * @param tags tags
     * @return Edited StudentScoreBuilder
     */
    public StudentScoreBuilder withTags(String ... tags) {
        this.tags = TestStudentDataUtil.getTagSet(tags);
        return this;
    }

    public StudentScore build() {
        return new StudentScore(sid, gcName, score, comment, tags);
    }
}
