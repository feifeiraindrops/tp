package seedu.modulight.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_MARKS;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.modulight.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.commands.exceptions.CommandException;
import seedu.modulight.model.Model;
import seedu.modulight.model.studentscore.ScoreMatchPredicate;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.studentscore.model.StudentScoreBook;
import seedu.modulight.testutil.EditStudentScoreDescriptorBuilder;

/**
 * Contains helper method for testing Student Score related command.
 */
public class CommandStudentScoreTestUtil {
    public static final String VALID_SID_JAMES = "A1122334Y";
    public static final String VALID_SID_AMY = "A2233445R";
    public static final String VALID_GCNAME_JAMES = "CA2";
    public static final String VALID_GCNAME_AMY = "Finals";
    public static final float VALID_SCORE_JAMES = 2;
    public static final float VALID_SCORE_AMY = 1; //
    public static final String VALID_COMMENT_JAMES = "Nice Work!";
    public static final String VALID_COMMENT_AMY = "Work Harder!";
    public static final String VALID_TAG_JAMES = "TutorPotential";
    public static final String VALID_TAG_AMY = "NeedHelp";

    public static final String SID_DESC_JAMES = " " + PREFIX_STUDENT_ID + VALID_SID_JAMES;
    public static final String SID_DESC_AMY = " " + PREFIX_STUDENT_ID + VALID_SID_AMY;
    public static final String GCNAME_DESC_JAMES = " " + PREFIX_COMPONENT_NAME + VALID_GCNAME_JAMES;
    public static final String GCNAME_DESC_AMY = " " + PREFIX_COMPONENT_NAME + VALID_GCNAME_AMY;
    public static final String SCORE_DESC_JAMES = " " + PREFIX_MARKS + VALID_SCORE_JAMES;
    public static final String SCORE_DESC_AMY = " " + PREFIX_MARKS + VALID_SCORE_AMY;
    public static final String COMMENT_DESC_JAMES = " " + PREFIX_COMMENT + VALID_COMMENT_JAMES;
    public static final String COMMENT_DESC_AMY = " " + PREFIX_COMMENT + VALID_COMMENT_AMY;
    public static final String TAG_DESC_JAMES = " " + PREFIX_TAG + VALID_TAG_JAMES;
    public static final String TAG_DESC_AMY = " " + PREFIX_TAG + VALID_TAG_AMY;

    public static final String INVALID_SID_DESC = " " + PREFIX_STUDENT_ID
            + "A12345678"; //The end of SID must be a Letter
    public static final String INVALID_GCNAME_DESC = " " + PREFIX_COMPONENT_NAME
            + "CS1234+"; //+ is not alphanumeric
    public static final String INVALID_SCORE_DESC = " " + PREFIX_MARKS
            + "10^2"; //Such expression is not parsable
    //public static final String INVALID_COMMENT_DESC
    public static final String INVALID_TAG = "Nice work"; //spaces is not allowed

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditStudentScoreCommand.EditStudentScoreDescriptor DESC_JAMES_SCORE;
    public static final EditStudentScoreCommand.EditStudentScoreDescriptor DESC_AMY_SCORE;


    static {
        DESC_JAMES_SCORE = new EditStudentScoreDescriptorBuilder().withStudentId(VALID_SID_JAMES)
                .withGcName(VALID_GCNAME_JAMES).withScore(VALID_SCORE_JAMES)
                .withComment(VALID_COMMENT_JAMES).withTags(VALID_TAG_JAMES).build();

        DESC_AMY_SCORE = new EditStudentScoreDescriptorBuilder().withStudentId(VALID_SID_AMY)
                .withGcName(VALID_GCNAME_AMY).withScore(VALID_SCORE_AMY)
                .withComment(VALID_COMMENT_AMY).withTags(VALID_TAG_AMY).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        StudentScoreBook expectedAddressBook = new StudentScoreBook(actualModel.getStudentScoreBook());
        List<StudentScore> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentScoreList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getStudentScoreBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentScoreList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStudentScoreAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentScoreList().size());

        StudentScore studentScore = model.getFilteredStudentScoreList().get(targetIndex.getZeroBased());
        final String studentId = studentScore.getStudentId().sid;
        final List<String> emptyList = new ArrayList<>();
        model.updateFilteredStudentScoreList(new ScoreMatchPredicate(Arrays.asList(studentId),
                emptyList, emptyList, emptyList, emptyList, emptyList));

        assertEquals(1, model.getFilteredStudentScoreList().size());
    }
}
