package seedu.modulight.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_GCNAME_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_GCNAME_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_SCORE_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_SCORE_JAMES;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.modulight.logic.commands.exceptions.CommandException;
import seedu.modulight.model.Model;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.testutil.StudentScoreBuilder;


/**
 * A utility class for test cases in command execution.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_JAMES = "James Choo";
    public static final String VALID_SID_AMY = "A2233445R";
    public static final String VALID_SID_JAMES = "A7654321Y";
    public static final String VALID_TG_AMY = "T01";
    public static final String VALID_TG_JAMES = "T02";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_JAMES = "JAMES@example.com";
    public static final String VALID_TAG_TA = "TA";
    public static final String VALID_TAG_MAKEUP_EXAM = "makeup";

    public static final StudentScore VALID_STUDENT_SCORE_AMY = new StudentScoreBuilder()
            .withScore(VALID_SCORE_AMY).withStudentId(VALID_SID_AMY).withGcName(VALID_GCNAME_AMY).build();
    public static final StudentScore VALID_STUDENT_SCORE_JAMES = new StudentScoreBuilder()
            .withScore(VALID_SCORE_JAMES).withStudentId(VALID_SID_JAMES).withGcName(VALID_GCNAME_JAMES).build();

    public static final List<String> EMPTY_ID_KEYWORDS = new ArrayList<>();
    public static final List<String> EMPTY_NAME_KEYWORDS = new ArrayList<>();
    public static final List<String> EMPTY_EMAIL_KEYWORDS = new ArrayList<>();
    public static final List<String> EMPTY_TG_KEYWORDS = new ArrayList<>();
    public static final List<String> EMPTY_TAG_KEYWORDS = new ArrayList<>();
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_JAMES = " " + PREFIX_NAME + VALID_NAME_JAMES;
    public static final String SID_DESC_AMY = " " + PREFIX_PHONE + VALID_SID_AMY;
    public static final String SID_DESC_JAMES = " " + PREFIX_PHONE + VALID_SID_JAMES;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_TA;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_MAKEUP_EXAM;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "JAMES!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //@@author siqirua-reused
    //Inspired by
    // https://github.com/AY2324S1-CS2103T-F10-3/tp/blob/master/src/test/java/seedu/address/testutil/TestUtil.java
    // and make some minor modifications to suit this project

    /**
     * Executes the given {@code command} under positive test cases, checks if <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} after execution matches {@code expectedModel}
     *
     * @param command Command to be executed.
     * @param actualModel Actual model before the execution.
     * @param expectedResultString Expected string representing the expected command result.
     * @param expectedModel Expected model after the execution.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedResultString,
                                            Model expectedModel) {

        CommandResult expectedResult;
        try {
            expectedResult = new CommandResult(expectedResultString);
        } catch (Exception e) {
            throw new AssertionError("Command Result not successfully created.", e);
        }
        assertCommandSuccess(command, actualModel, expectedResult, expectedModel);
    }

    /**
     * Executes the given {@code command} under positive test cases, checks if <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} after execution matches {@code expectedModel}
     *
     * @param command Command to be executed.
     * @param actualModel Actual model before the execution.
     * @param expectedResult Expected command result.
     * @param expectedModel Expected model after the execution.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command in positive test cases should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command} under negative test cases, checks if <br>
     * - the thrown {@link Exception} matches {@code expectedException} <br>
     * - the {@code actualModel} after execution matches {@code expectedModel}
     *
     * @param command Command to be executed.
     * @param actualModel Actual model before the execution.
     * @param expectedException Expected exception to be thrown.
     * @param expectedModel Expected model after the execution, usually the same as the actual model before execution.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandException expectedException,
                                            Model expectedModel) {
        try {
            command.execute(actualModel);
            throw new AssertionError("Execution of command in negative test cases should not succeed.");
        } catch (CommandException ce) {
            assertEquals(expectedException, ce);
        } finally {
            assertEquals(actualModel, expectedModel);
        }
    }
    //@@author
}
