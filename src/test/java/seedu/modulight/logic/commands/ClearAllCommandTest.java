package seedu.modulight.logic.commands;

import static seedu.modulight.logic.commands.ClearAllCommand.MESSAGE_CLEAR_SUCCESS;
import static seedu.modulight.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.modulight.model.Model;
import seedu.modulight.model.ModelManager;
import seedu.modulight.model.UserPrefs;
import seedu.modulight.testutil.TestGcDataUtil;
import seedu.modulight.testutil.TestStudentDataUtil;
import seedu.modulight.testutil.TestStudentScoreDataUtil;
public class ClearAllCommandTest {
    private Model model = new ModelManager(TestStudentDataUtil.getTestStudentBook("create"),
            TestStudentScoreDataUtil.getSampleStudentScoreBook("create"),
            TestGcDataUtil.getTestGcBook("delete"), new UserPrefs());
    private Model modelCleared = new ModelManager();

    @Test
    public void execute_clear_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_CLEAR_SUCCESS);
        assertCommandSuccess(new ClearAllCommand(), model, expectedCommandResult, modelCleared);
    }
}
