package seedu.modulight.model.studentscore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.modulight.model.gradedcomponent.GcMatchPredicate;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.StudentMatchPredicate;

/**
 * Tests that a {@code Student}'s attributes matches any of the attributes given.
 */
public class ScoreMatchPredicate implements Predicate<StudentScore> {
    private final List<String> gcNames;
    private final List<String> idKeywords;
    private final List<String> nameKeywords;
    private final List<String> tutorialGroupKeywords;
    private final List<String> tagKeywords;
    private final List<String> commentKeywords;
    private final Predicate<Student> studentMatchPredicate;
    private final GcMatchPredicate gcMatchPredicate;

    /**
     * Constructs a predicate to search for certain students.
     *
     * @param idKeywords            the id keywords
     * @param nameKeywords          the name keywords
     * @param tutorialGroupKeywords the tutorial group keywords
     * @param tagKeywords           the tag keywords
     * @param comments              the comments
     * @param gcNames               The keywords for Component Name
     */
    public ScoreMatchPredicate(List<String> idKeywords, List<String> nameKeywords,
                                      List<String> tutorialGroupKeywords, List<String> tagKeywords,
                                      List<String> comments, List<String> gcNames) {
        this.gcNames = gcNames;
        this.idKeywords = idKeywords;
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
        this.commentKeywords = comments;
        this.tutorialGroupKeywords = tutorialGroupKeywords;
        this.studentMatchPredicate = new StudentMatchPredicate();
        this.gcMatchPredicate = new GcMatchPredicate();

    }

    /**
     * Instantiates a new Score match predicate.
     *
     * @param studentMatchPredicate the student match predicate
     */
    public ScoreMatchPredicate(Predicate<Student> studentMatchPredicate) {
        this.gcNames = new ArrayList<>();
        this.idKeywords = new ArrayList<>();
        this.nameKeywords = new ArrayList<>();
        this.tagKeywords = new ArrayList<>();
        this.tutorialGroupKeywords = new ArrayList<>();
        this.commentKeywords = new ArrayList<>();
        this.studentMatchPredicate = studentMatchPredicate;
        this.gcMatchPredicate = new GcMatchPredicate();
    }

    /**
     * Constructs a predicate to search for certain students scores.
     *
     * @param gcKeywords            the gc keywords
     */
    public ScoreMatchPredicate(List<String> gcKeywords) {
        this.gcNames = gcKeywords;
        this.idKeywords = new ArrayList<>();
        this.nameKeywords = new ArrayList<>();
        this.tagKeywords = new ArrayList<>();
        this.tutorialGroupKeywords = new ArrayList<>();
        this.commentKeywords = new ArrayList<>();
        this.studentMatchPredicate = new StudentMatchPredicate();
        this.gcMatchPredicate = new GcMatchPredicate();
    }

    /**
     * Instantiates a new Score match predicate.
     *
     * @param gcMatchPredicate the gc match predicate
     */
    public ScoreMatchPredicate(GcMatchPredicate gcMatchPredicate) {
        this.gcNames = new ArrayList<>();
        this.idKeywords = new ArrayList<>();
        this.nameKeywords = new ArrayList<>();
        this.tagKeywords = new ArrayList<>();
        this.tutorialGroupKeywords = new ArrayList<>();
        this.commentKeywords = new ArrayList<>();
        this.studentMatchPredicate = new StudentMatchPredicate();
        this.gcMatchPredicate = gcMatchPredicate;
    }

    @Override
    public boolean test(StudentScore score) {
        boolean isGcNameMatch = gcNames.isEmpty() || gcNames.stream()
                .anyMatch(keyword -> score.getGcName().toString().toLowerCase().contains(keyword.toLowerCase()));
        boolean isIdMatch = idKeywords.isEmpty() || idKeywords.stream()
                .anyMatch(keyword -> score.getStudentId().toString().toLowerCase().contains(keyword.toLowerCase()));
        boolean isCommentMatch = commentKeywords.isEmpty() || commentKeywords.stream()
                .anyMatch(keyword -> score.getComment().toString().toLowerCase().contains(keyword.toLowerCase()));
        boolean isTutMatch = tutorialGroupKeywords.isEmpty() || tutorialGroupKeywords.stream()
                .anyMatch(keyword -> score.getStudent().getTutorial().toString().equalsIgnoreCase(keyword));
        boolean isTagMatch = tagKeywords.isEmpty() || score.getTags().stream()
                .anyMatch(tag -> tagKeywords.contains(tag.tagName.toLowerCase()));
        boolean isStudentMatch = studentMatchPredicate.test(score.getStudent());
        boolean isGcMatch = gcMatchPredicate.test(score.getGradedComponent());
        return isGcNameMatch && isIdMatch && isCommentMatch && isTutMatch && isTagMatch
                && isStudentMatch && isGcMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScoreMatchPredicate)) {
            return false;
        }

        ScoreMatchPredicate otherScoreMatchPredicate =
                (ScoreMatchPredicate) other;
        return idKeywords.equals(otherScoreMatchPredicate.idKeywords)
                && nameKeywords.equals(otherScoreMatchPredicate.nameKeywords)
                && tutorialGroupKeywords.equals(otherScoreMatchPredicate.tutorialGroupKeywords)
                && tagKeywords.equals(otherScoreMatchPredicate.tagKeywords)
                && gcNames.equals(otherScoreMatchPredicate.gcNames)
                && commentKeywords.equals((otherScoreMatchPredicate.commentKeywords))
                && studentMatchPredicate.equals(otherScoreMatchPredicate.studentMatchPredicate)
                && gcMatchPredicate.equals(otherScoreMatchPredicate.gcMatchPredicate);
    }

    @Override
    public String toString() {
        return "Find student score by given criteria";
    }
}

