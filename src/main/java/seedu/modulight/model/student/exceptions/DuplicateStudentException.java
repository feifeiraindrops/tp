package seedu.modulight.model.student.exceptions;

/**
 * The type Duplicate student exception.
 */
public class DuplicateStudentException extends RuntimeException {
    /**
     * Instantiates a new Duplicate student exception
     */
    public DuplicateStudentException() {
        super("Operation would result in duplicate persons");
    }
}
