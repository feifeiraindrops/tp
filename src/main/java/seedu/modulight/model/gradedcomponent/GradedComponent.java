package seedu.modulight.model.gradedcomponent;

import static seedu.modulight.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.model.studentscore.StudentScore;


/**
 * Represents a graded component in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class GradedComponent {

    // Identity fields
    private final GcName name;

    // Data fields
    private final MaxMarks maxMarks;
    private final Weightage weightage;

    private final List<StudentScore> scoreList = new ArrayList<>();

    private float meanAbsoluteScore;
    private float meanRelativeScore;




    /**
     * Every field must be present and not null.
     */
    public GradedComponent(GcName name, MaxMarks maxMarks, Weightage weightage) {
        requireAllNonNull(name);
        this.name = name;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
    }

    public GcName getName() {
        return name;
    }

    public MaxMarks getMaxMarks() {
        return maxMarks;
    }

    public Weightage getWeightage() {
        return weightage;
    }
    /**
     * Returns true if both Students have the same name.
     * This defines a weaker notion of equality between two Students.
     */

    public List<StudentScore> getScores() {
        return Collections.unmodifiableList(scoreList);
    }
    public void addScore(StudentScore score) {
        scoreList.add(score);
    }

    public void deleteScore(StudentScore score) {
        scoreList.remove(score);
    }

    private float calcMeanAbsoluteScore() {
        float size = scoreList.size();
        if (size == 0) {
            return 0;
        }
        float totalScore = 0;
        for (StudentScore sc : scoreList) {
            totalScore += sc.getScore();
        }
        return totalScore / size;
    }


    private float calcMeanRelativeScore() {
        float size = scoreList.size();
        if (size == 0) {
            return 0;
        }
        float totalScore = 0;
        for (StudentScore sc : scoreList) {
            totalScore += sc.calcRelativeScore();
        }
        return totalScore / size;
    }

    public float getMeanAbsoluteScore() {
        return this.meanAbsoluteScore;
    }

    public float getMeanRelativeScore() {
        return this.meanRelativeScore;
    }

    /**
     * Recalculates all score-related fields for this GradedComponent
     */
    public void recalculateScores() {
        this.meanAbsoluteScore = calcMeanAbsoluteScore();
        this.meanRelativeScore = calcMeanRelativeScore();
    }

    /**
     * Returns true if both Students have the same name.
     * This defines a weaker notion of equality between two Students.
     */
    public boolean isSameGc(GradedComponent otherGc) {
        if (otherGc == this) {
            return true;
        }

        return otherGc != null
                && otherGc.name.equals(name);
    }
    /**
     * Returns true if both Students have the same identity and data fields.
     * This defines a stronger notion of equality between two Students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradedComponent)) {
            return false;
        }

        GradedComponent otherGc = (GradedComponent) other;
        return otherGc.name.equals(name);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }

}
