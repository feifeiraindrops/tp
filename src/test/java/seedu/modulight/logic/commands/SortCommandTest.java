package seedu.modulight.logic.commands;

import static seedu.modulight.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.modulight.model.Model;
import seedu.modulight.model.ModelManager;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.testutil.TestGcDataUtil;
import seedu.modulight.testutil.TestStudentDataUtil;
import seedu.modulight.testutil.TestStudentScoreDataUtil;



public class SortCommandTest {
    private Model model = new ModelManager(TestStudentDataUtil.getTestStudentBook("create"),
            TestStudentScoreDataUtil.getSampleStudentScoreBook("create"),
            TestGcDataUtil.getTestGcBook("delete"), new UserPrefs());

    private Model expectedModelSortedByName = new ModelManager(TestStudentDataUtil
            .getTestStudentBook("sortByName"),
            TestStudentScoreDataUtil.getSampleStudentScoreBook("create"),
            TestGcDataUtil.getTestGcBook("delete"), new UserPrefs());

    private Model expectedModelSortedByGradeReverse = new ModelManager(TestStudentDataUtil
            .getTestStudentBook("sortByTsReverse"),
            TestStudentScoreDataUtil.getSampleStudentScoreBook("create"),
            TestGcDataUtil.getTestGcBook("delete"), new UserPrefs());

    @Test
    public void execute_sort_success() {
        String expectedOutput = "6 students sorted!";
        assertCommandSuccess(new SortStudentCommand("n", false), model, expectedOutput,
                expectedModelSortedByName);
        assertCommandSuccess(new SortStudentCommand("o", true), model, expectedOutput,
                expectedModelSortedByGradeReverse);
    }

}
