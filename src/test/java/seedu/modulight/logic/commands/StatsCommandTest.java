package seedu.modulight.logic.commands;

import static seedu.modulight.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.modulight.model.Model;
import seedu.modulight.model.ModelManager;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.testutil.TestGcDataUtil;
import seedu.modulight.testutil.TestStudentDataUtil;
import seedu.modulight.testutil.TestStudentScoreDataUtil;

public class StatsCommandTest {
    private Model model = new ModelManager(TestStudentDataUtil.getTestStudentBook("create"),
            TestStudentScoreDataUtil.getSampleStudentScoreBook("create"),
            TestGcDataUtil.getTestGcBook("delete"), new UserPrefs());

    @Test
    public void execute_stat_success() {
        String expectedOutput = "Here are the statistics:\n"
                + "MAX = 100.00, MIN = 0.00, MEAN = 50.00, STANDARD DEVIATION = 34.16\n"
                + "UPPER QUARTILE = 85.00, LOWER QUARTILE = 15.00, SKEWNESS = 0.00\n";
        assertCommandSuccess(new StatsCommand(new ArrayList<>()), model, expectedOutput, model);
    }

    @Test
    public void execute_tutStat_success() {
        String expectedOutputOfTut3 = "Here are the statistics of Tutorial Group T03:\n"
                + "MAX = 80.00, MIN = 0.00, MEAN = 40.00, STANDARD DEVIATION = 28.28\n"
                + "UPPER QUARTILE = 70.00, LOWER QUARTILE = 10.00, SKEWNESS = 0.00\n";
        assertCommandSuccess(new StatsCommand(new ArrayList<>(), new TutorialGroup("T03")), model,
                expectedOutputOfTut3, model);
    }

    //ADD ONE TEST TO CHECK SPECIFIC GRADED COMPONENT
    @Test
    public void execute_specificStat_success() {
        String expectedOutput1 = "Here are the statistics:\n"
                + "MAX = 100.00, MIN = 0.00, MEAN = 50.00, STANDARD DEVIATION = 34.16\n";
        String expectedOutput2 = "Here are the statistics:\n"
                + "UPPER QUARTILE = 85.00, LOWER QUARTILE = 15.00, SKEWNESS = 0.00\n";
        ArrayList<String> stats1 = new ArrayList<>(List.of(new String[] {
            "max", "min", "mean", "standardDeviation"
        }));
        ArrayList<String> stats2 = new ArrayList<>(List.of(new String[] {
            "upperQuartile", "lowerQuartile", "skewness"
        }));
        assertCommandSuccess(new StatsCommand(stats1), model, expectedOutput1, model);
        assertCommandSuccess(new StatsCommand(stats2), model, expectedOutput2, model);
    }
}
