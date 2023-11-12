package seedu.modulight.logic.commands;

import static seedu.modulight.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.modulight.model.Model;
import seedu.modulight.model.ModelManager;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.testutil.TestGcDataUtil;
import seedu.modulight.testutil.TestStudentDataUtil;
import seedu.modulight.testutil.TestStudentScoreDataUtil;

public class SortStudentScoreCommandTest {
    private Model model = new ModelManager(TestStudentDataUtil.getTestStudentBook("create"),
            TestStudentScoreDataUtil.getSampleStudentScoreBook("create"),
            TestGcDataUtil.getTestGcBook("delete"), new UserPrefs());
    private Model expectedModelSortedByMidtermReverse = new ModelManager(TestStudentDataUtil
            .getTestStudentBook("sortByTsReverse"),
            TestStudentScoreDataUtil.getSampleStudentScoreBook("reverse"),
            TestGcDataUtil.getTestGcBook("delete"), new UserPrefs());

    @Test
    public void execute_sortScore_success() {
        String expectedOutput = "6 scores sorted!";
        assertCommandSuccess(new SortStudentScoreCommand(new GcName("Midterm"), true), model, expectedOutput,
                expectedModelSortedByMidtermReverse);
    }

}
