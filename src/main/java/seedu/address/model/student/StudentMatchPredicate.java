package seedu.address.model.student;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Student}'s attributes matches any of the attributes given.
 */
public class StudentMatchPredicate implements Predicate<Student> {
    private final List<String> idKeywords;
    private final List<String> nameKeywords;
    private final List<String> emailKeywords;
    private final List<String> tutorialGroupKeywords;
    private final List<String> tagKeywords;

    /**
     * Constructs a predicate to search for certain students.
     *
     * @param idKeywords The keywords for Student ID (can be empty)
     * @param nameKeywords The keywords for Student name (can be empty)
     * @param emailKeywords The keywords for Student email (can be empty)
     * @param tutorialGroupKeywords The keywords for Student tutorial group (can be empty)
     * @param tagKeywords The keywords for Student tag (can be empty)
     */
    public StudentMatchPredicate(List<String> idKeywords, List<String> nameKeywords, List<String> emailKeywords,
                                 List<String> tutorialGroupKeywords, List<String> tagKeywords) {
        this.idKeywords = idKeywords;
        this.emailKeywords = emailKeywords;
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
        this.tutorialGroupKeywords = tutorialGroupKeywords;
    }

    @Override
    public boolean test(Student student) {
        boolean idMatch = idKeywords.isEmpty() || idKeywords.stream()
                .anyMatch(keyword -> student.getStudentId().toString().toLowerCase().contains(keyword.toLowerCase()));
        boolean nameMatch = nameKeywords.isEmpty() || nameKeywords.stream()
                .anyMatch(keyword -> student.getName().toString().toLowerCase().contains(keyword.toLowerCase()));
        boolean emailMatch = emailKeywords.isEmpty() || emailKeywords.stream()
                .anyMatch(keyword -> student.getEmail().toString().toLowerCase().contains(keyword.toLowerCase()));
        boolean tutMatch = tutorialGroupKeywords.isEmpty() || tutorialGroupKeywords.stream()
                .anyMatch(keyword -> student.getTutorial().toString().equalsIgnoreCase(keyword));
        boolean tagMatch = tagKeywords.isEmpty() || student.getTags().stream()
                .anyMatch(tag -> tagKeywords.contains(tag.tagName.toLowerCase()));
        return idMatch && nameMatch && emailMatch && tutMatch && tagMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentMatchPredicate)) {
            return false;
        }

        StudentMatchPredicate otherStudentIdMatchPredicate =
                (StudentMatchPredicate) other;
        return idKeywords.equals(otherStudentIdMatchPredicate.idKeywords)
                && nameKeywords.equals(otherStudentIdMatchPredicate.nameKeywords)
                && emailKeywords.equals(otherStudentIdMatchPredicate.emailKeywords)
                && tutorialGroupKeywords.equals(otherStudentIdMatchPredicate.tutorialGroupKeywords)
                && tagKeywords.equals(otherStudentIdMatchPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return "Find students by given criteria";
    }
}





