<<<<<<<< HEAD:src/main/java/seedu/address/model/studentscore/exceptions/DuplicateScoresException.java
package seedu.address.model.studentscore.exceptions;
========
package seedu.modulight.model.student.exceptions;
>>>>>>>> 1d56e391038028720ed49a637ef0b1388c39e4c2:src/main/java/seedu/modulight/model/student/exceptions/DuplicateScoresException.java

import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.student.Student;

/**
 * Represents the error that one student have multiple scores for one graded component.
 */
public class DuplicateScoresException extends Exception {
    /**
     * Creates the exception that one student have multiple scores for one graded component.
     *
     * @param gcName The graded component name that one student have multiple scores.
     * @param student The student who encounter the error
     */
    public DuplicateScoresException(GcName gcName, Student student) {
        super(String.format("Duplicate scores of %s found for student with id %s",
                gcName.gcName, student.getStudentId().toString()));
    }
}

