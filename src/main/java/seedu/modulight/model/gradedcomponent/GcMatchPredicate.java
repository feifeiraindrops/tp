package seedu.modulight.model.gradedcomponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * The type Gc match predicate.
 */
public class GcMatchPredicate implements Predicate<GradedComponent> {

    private final List<String> gcKeywords;

    /**
     * Constructs a predicate to search for certain students.
     *
     * @param gcKeywords the gc keywords
     */
    public GcMatchPredicate(List<String> gcKeywords) {
        this.gcKeywords = gcKeywords;
    }

    /**
     * Instantiates a new empty Gc match predicate that always returns true.
     */
    public GcMatchPredicate() {
        this.gcKeywords = new ArrayList<>();
    }

    @Override
    public boolean test(GradedComponent gc) {
        boolean isGcMatch = gcKeywords.isEmpty() || gcKeywords.stream()
                .anyMatch(keyword -> gc.getName().toString().toLowerCase().contains(keyword.toLowerCase()));
        return isGcMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GcMatchPredicate)) {
            return false;
        }

        GcMatchPredicate otherStudentIdMatchPredicate =
                (GcMatchPredicate) other;
        return gcKeywords.equals(otherStudentIdMatchPredicate.gcKeywords);
    }

    @Override
    public String toString() {
        return "Find graded component by given criteria";
    }
}
