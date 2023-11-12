package seedu.modulight.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.modulight.logic.parser.CliSyntax;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.MaxMarks;
import seedu.modulight.model.gradedcomponent.Weightage;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.StudentEmail;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.model.studentscore.StudentScore;

public class MessagesTest {
    @Test
    public void error_duplicatePrefixes_success() {
        assertEquals(Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_NAME),
                "Multiple values specified for the following single-valued field(s): n/");
    }
    @Test
    public void format_student_success() {
        Student testStu = new Student(new StudentId("A0123456U"), new StudentName("Test Stu"),
                new StudentEmail("xyz@nus.edu.sg"), new TutorialGroup("T01"), new ArrayList<>(),
                new HashSet<>());
        String expectedOutput = "A0123456U; Name: Test Stu; Email: xyz@nus.edu.sg; Tutorial Group: T01";
        assertEquals(Messages.format(testStu), expectedOutput);
    }

    @Test
    public void format_studentScore_success() {
        StudentScore testStuScore = new StudentScore(new StudentId("A0123456U"), new GcName("Midterm"), 40);
        String expectedOutput = "; Student ID: A0123456U; Graded component name: Midterm; Score: 40.0";
        assertEquals(Messages.formatStudentScore(testStuScore), expectedOutput);
    }

    @Test
    public void format_gradedComponent_success() {
        GradedComponent testGc = new GradedComponent(new GcName("Midterm"), new MaxMarks(100), new Weightage(20));
        String expectedOutput = "Midterm; Maximum Marks: 100.0; Weightage: 20.0";
        assertEquals(Messages.formatGradedComponent(testGc), expectedOutput);
    }
}
