package seedu.modulight.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.modulight.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.modulight.model.Model;
import seedu.modulight.model.ModelManager;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.testutil.TestGcDataUtil;
import seedu.modulight.testutil.TestStudentDataUtil;
import seedu.modulight.testutil.TestStudentScoreDataUtil;

public class CompStatsCommandTest {
    private final Model model = new ModelManager(TestStudentDataUtil.getTestStudentBook("create"),
            TestStudentScoreDataUtil.getSampleStudentScoreBook("twoScores"),
            TestGcDataUtil.getTestGcBook("create"), new UserPrefs());

    @Test
    public void execute_stat_success() {
        String expectedOutput = "Here are the statistics:\n"
                + "MAX = 50.00, MIN = 0.00, MEAN = 25.00, STANDARD DEVIATION = 17.08\n"
                + "UPPER QUARTILE = 42.50, LOWER QUARTILE = 7.50, SKEWNESS = 0.00\n";
        assertCommandSuccess(new CompStatsCommand(new ArrayList<>(), new GcName("Midterm")),
                model, expectedOutput, model);
    }

    @Test
    public void execute_tutStat_success() {
        String expectedOutputOfTut3 = "Here are the statistics of Final of Tutorial Group T03:\n"
                + "MAX = 100.00, MIN = 60.00, MEAN = 80.00, STANDARD DEVIATION = 14.14\n"
                + "UPPER QUARTILE = 95.00, LOWER QUARTILE = 65.00, SKEWNESS = 0.00\n";
        assertCommandSuccess(new CompStatsCommand(new ArrayList<>(), new GcName("Final"),
                        new TutorialGroup("T03")), model, expectedOutputOfTut3, model);
    }

    @Test
    public void execute_equal() {
        CompStatsCommand testCommand = new CompStatsCommand(new ArrayList<>(),
                new GcName("Midterm"), new TutorialGroup("T01"));
        CompStatsCommand testCommand1 = new CompStatsCommand(new ArrayList<>(),
                new GcName("Midterm"), new TutorialGroup("T01"));
        CompStatsCommand testCommand2 = new CompStatsCommand(new ArrayList<>(),
                new GcName("Midterm"), new TutorialGroup("T02"));
        CompStatsCommand testCommand3 = new CompStatsCommand(new ArrayList<>(),
                new GcName("Final"), new TutorialGroup("T01"));
        assertNotEquals(testCommand, "Test String");
        assertNotEquals(testCommand, testCommand2);
        assertNotEquals(testCommand, testCommand3);
        assertEquals(testCommand, testCommand1);
    }
}
