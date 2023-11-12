package seedu.modulight.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.modulight.logic.commands.Command;
import seedu.modulight.logic.parser.exceptions.ParseException;

/**
 * A utility class for test cases in parser execution.
 */
public class ParserTestUtil {
    //@@author siqirua-reused
    //Inspired by
    // https://github.com/AY2324S1-CS2103T-F10-3/tp/blob/master/src/test/java/seedu/address/testutil/TestUtil.java
    // and make some minor modifications to suit this project

    /**
     * Executes the given {@code parser} under positive test cases, checks if <br>
     * - the returned {@link Command} matches {@code expectedCommand}
     *
     * @param input Input arguments to be parsed.
     * @param parser Parser used to use the argument.
     * @param expectedCommand Expected command to be generated.
     */
    public static void assertParserSuccess(String input, Parser parser, Command expectedCommand) {
        try {
            Command result = parser.parse(input);
            assertEquals(expectedCommand, result);
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }


    /**
     * Executes the given {@code parser} under negative test cases, checks if <br>
     * - the thrown {@link Exception} matches {@code expectedException}
     *
     * @param input Input arguments to be parsed.
     * @param parser Parser used to use the argument.
     * @param expectedException Expected exception to be raised.
     */
    public static void assertCommandFailure(String input, Parser parser, ParseException expectedException) {
        try {
            parser.parse(input);
            throw new AssertionError("Execution of command in negative test cases should not succeed.");
        } catch (ParseException ce) {
            assertEquals(expectedException, ce);
        }
    }
    //@@author
}
