package seedu.modulight.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.StudentEmail;
import seedu.modulight.model.student.StudentGrade;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.tag.Tag;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    /**
     * The constant DEFAULT_ID.
     */
    public static final String DEFAULT_ID = "A0000000W";
    /**
     * The constant DEFAULT_NAME.
     */
    public static final String DEFAULT_NAME = "Amy Bee";
    /**
     * The constant DEFAULT_EMAIL.
     */
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    /**
     * The constant DEFAULT_TG.
     */
    public static final String DEFAULT_TG = "T00";
    /**
     * The constant DEFAULT_GRADE.
     */
    public static final String DEFAULT_GRADE = "";

    /**
     * The constant DEFAULT_GC_NAME_1.
     */
    public static final String DEFAULT_GC_NAME_1 = "Midterm";
    /**
     * The constant DEFAULT_GC_NAME_2.
     */
    public static final String DEFAULT_GC_NAME_2 = "Final";

    private StudentId sid;
    private StudentName name;
    private StudentEmail email;
    private TutorialGroup tg;
    private List<StudentScore> scoreList;

    private Set<Tag> tags;
    private float totalScore;
    private StudentGrade studentGrade;


    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        sid = new StudentId(DEFAULT_ID);
        name = new StudentName(DEFAULT_NAME);
        email = new StudentEmail(DEFAULT_EMAIL);
        tg = new TutorialGroup(DEFAULT_TG);
        scoreList = new ArrayList<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code StudentToCopy}.
     *
     * @param studentToCopy the student to copy
     */
    public StudentBuilder(Student studentToCopy) {
        sid = studentToCopy.getStudentId();
        name = studentToCopy.getName();
        email = studentToCopy.getEmail();
        tg = studentToCopy.getTutorial();
        tags = new HashSet<>(studentToCopy.getTags());
        scoreList = new ArrayList<>(studentToCopy.getScores());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     *
     * @param name the name
     * @return the student builder
     */
    public StudentBuilder withName(String name) {
        this.name = new StudentName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     *
     * @param tags the tags
     * @return the student builder
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = TestStudentDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     *
     * @param sid the sid
     * @return the student builder
     */
    public StudentBuilder withId(String sid) {
        this.sid = new StudentId(sid);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     *
     * @param tg the tg
     * @return the student builder
     */
    public StudentBuilder withTg(String tg) {
        this.tg = new TutorialGroup(tg);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     *
     * @param email the email
     * @return the student builder
     */
    public StudentBuilder withEmail(String email) {
        this.email = new StudentEmail(email);
        return this;
    }

    /**
     * With score student builder.
     *
     * @param scores the scores
     * @return the student builder
     */
    public StudentBuilder withScore(StudentScore ... scores) {
        this.scoreList.addAll(List.of(scores));
        return this;
    }

    /**
     * Build student.
     *
     * @return the student
     */
    public Student build() {
        return new Student(sid, name, email, tg, scoreList, tags);
    }

}

