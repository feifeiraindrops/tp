package seedu.modulight.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.student.exceptions.DuplicateStudentException;
import seedu.modulight.model.student.exceptions.StudentNotFoundException;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.studentscore.exceptions.DuplicateScoresException;



/**
 * A list of students that enforces uniqueness between its elements and does not allow nulls.
 * A student is considered unique by comparing using {@code Student#isSameStudent(Student)}. As such, adding
 * and updating of students uses Student#isSameStudent(Student) for equality  to ensure that the student being added
 * or updated is unique in terms of identity in the UniqueStudentList. However, the removal of a student uses
 * Student#equals(Object) to ensure that the student with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Student#isSameStudent(Student)
 */
public class UniqueStudentList implements Iterable<Student> {

    private final ObservableList<Student> internalList = FXCollections.observableArrayList();
    private final ObservableList<Student> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent student as the given argument.
     */
    public boolean contains(Student toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStudent);
    }

    /**
     * Adds a student to the list.
     * The student must not already exist in the list.
     */
    public void add(Student toAdd) throws DuplicateStudentException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the list.
     * The student identity of {@code editedStudent} must not be the same as another existing
     * student in the list.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StudentNotFoundException();
        }

        if (!target.isSameStudent(editedStudent) && contains(editedStudent)) {
            throw new DuplicateStudentException();
        }

        internalList.set(index, editedStudent);
    }

    /**
     * Removes the equivalent student from the list.
     * The student must exist in the list.
     */
    public void remove(Student toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StudentNotFoundException();
        }
    }

    /**
     * Clears the Student list.
     */
    public void clear() {
        internalList.clear();
    }

    /**
     * Sorts the Student list.
     */
    public void sort(String order, boolean reverse) {
        internalList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                String s1Value;
                String s2Value;

                Float s1TotalScore = s1.getTotalScore();
                Float s2TotalScore = s2.getTotalScore();
                switch (order) {
                case "s":
                    s1Value = s1.getStudentId().toString();
                    s2Value = s2.getStudentId().toString();
                    break;
                case "n":
                    s1Value = s1.getName().toString();
                    s2Value = s2.getName().toString();
                    break;
                case "e":
                    s1Value = s1.getEmail().toString();
                    s2Value = s2.getEmail().toString();
                    break;
                case "g":
                    s1Value = s1.getTutorial().toString();
                    s2Value = s2.getTutorial().toString();
                    break;
                default:
                    s1Value = s1.getStudentId().toString();
                    s2Value = s2.getStudentId().toString();
                }

                if (order.equals("o")) {
                    return (!reverse ? 1 : -1) * s1TotalScore.compareTo(s2TotalScore);
                }

                return (!reverse ? 1 : -1) * s1Value.compareTo(s2Value);
            }
        });
    }

    /**
     * Sorts the Student list based on the performance in a graded component.
     */
    public void sortScore(GcName gcName, boolean reverse) {
        internalList.sort(new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                List<Float> s1MatchingValue = s1.getScores().stream()
                        .filter(studentScore -> studentScore.getGcName().equals(gcName))
                        .map(StudentScore::getScore).collect(Collectors.toList());
                List<Float> s2MatchingValue = s2.getScores().stream()
                        .filter(studentScore -> studentScore.getGcName().equals(gcName))
                        .map(StudentScore::getScore).collect(Collectors.toList());
                if (s1MatchingValue.size() > 1) {
                    DuplicateScoresException exc = new DuplicateScoresException(gcName, s1);
                    throw new RuntimeException(exc.getMessage(), exc);
                } else if (s2MatchingValue.size() > 1) {
                    DuplicateScoresException exc = new DuplicateScoresException(gcName, s2);
                    throw new RuntimeException(exc.getMessage(), exc);
                }
                Float s1Value = 0F;
                Float s2Value = 0F;
                if (s1MatchingValue.size() == 1) {
                    s1Value = s1MatchingValue.get(0);
                }
                if (s2MatchingValue.size() == 1) {
                    s2Value = s2MatchingValue.get(0);
                }
                if (!reverse) {
                    return s1Value.compareTo(s2Value);
                } else {
                    return s2Value.compareTo(s1Value);
                }
            }
        });
    }

    /**
     * Get size of the List.
     * @return the size.
     */
    public int getSize() {
        return this.internalList.size();
    }

    public void setStudents(UniqueStudentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        requireAllNonNull(students);
        if (!studentsAreUnique(students)) {
            // to change
            throw new DuplicateStudentException();
        }

        internalList.setAll(students);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Student> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Student> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueStudentList)) {
            return false;
        }

        UniqueStudentList otherUniqueStudentList =
                (UniqueStudentList) other;
        return internalList.equals(otherUniqueStudentList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code students} contains only unique students.
     */
    private boolean studentsAreUnique(List<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(i).isSameStudent(students.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
