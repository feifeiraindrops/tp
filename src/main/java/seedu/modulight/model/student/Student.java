package seedu.modulight.model.student;

import static seedu.modulight.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.Weightage;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.tag.Tag;


/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final StudentId sid;

    private final StudentName name;
    private final StudentEmail email;
    private final TutorialGroup tg;
    private final List<StudentScore> scoreList = new ArrayList<>();

    private final Set<Tag> tags = new HashSet<>();
    private float totalScore;
    private final StudentGrade studentGrade;

    /**
     * Every field must be present and not null.
     */
    public Student(StudentId sid, StudentName name, StudentEmail email, TutorialGroup tg,
                   List<StudentScore> scores, Set<Tag> tagSet) {
        requireAllNonNull(sid, name, email, tg);
        this.sid = sid;
        this.name = name;
        this.email = email;
        this.tg = tg;
        this.scoreList.addAll(scores);
        this.tags.addAll(tagSet);
        this.studentGrade = new StudentGrade("");
    }

    /**
     * Every field must be present and not null.
     */
    public Student(StudentId sid, StudentName name, StudentEmail email, TutorialGroup tg,
                   List<StudentScore> scores, Set<Tag> tagSet, StudentGrade studentGrade) {
        requireAllNonNull(sid, name, email, tg, studentGrade);
        this.sid = sid;
        this.name = name;
        this.email = email;
        this.tg = tg;
        this.scoreList.addAll(scores);
        this.tags.addAll(tagSet);
        this.studentGrade = studentGrade;
    }

    public StudentId getStudentId() {
        return this.sid;
    }

    public StudentEmail getEmail() {
        return this.email;
    }

    public StudentName getName() {
        return this.name;
    }

    public TutorialGroup getTutorial() {
        return this.tg;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<StudentScore> getScores() {
        return Collections.unmodifiableList(scoreList);
    }

    public StudentGrade getStudentGrade() {
        return this.studentGrade;
    }

    public void addScore(StudentScore score) {
        scoreList.add(score);
    }

    public void deleteScore(StudentScore score) {
        scoreList.remove(score);
    }

    public float getTotalScore() {
        return this.totalScore;
    }

    private float calcTotalScore() {
        float totalWeightage = 0;
        for (StudentScore sc : scoreList) {
            GradedComponent gc = sc.getGradedComponent();
            Weightage w = gc.getWeightage();
            totalWeightage += w.weightage;
        }
        float totalScore = 0;
        for (StudentScore sc : scoreList) {
            GradedComponent gc = sc.getGradedComponent();
            Weightage w = gc.getWeightage();
            float weight = w.weightage;
            totalScore += weight / totalWeightage * sc.calcRelativeScore();
        }
        return totalScore;
    }

    public void recalculateScores() {
        this.totalScore = calcTotalScore();
    }



    /**
     * Returns true if both students have the same student ID.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getStudentId().equals(getStudentId()) && otherStudent.getName().equals(getName());
    }
    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return sid.equals(otherStudent.sid) && name.equals(otherStudent.name) && email.equals(otherStudent.email)
                && tg.equals(otherStudent.tg) && tags.equals(otherStudent.tags)
                && scoreList.equals(otherStudent.scoreList);

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(sid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("student id", sid)
                .add("email", email)
                .add("tutorial group", tg)
                .add("tags", tags)
                .add("student grade", studentGrade)
                .toString();

    }

}
