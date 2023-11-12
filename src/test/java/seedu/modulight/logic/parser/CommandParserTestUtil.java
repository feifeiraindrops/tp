package seedu.modulight.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.modulight.logic.commands.Command;
import seedu.modulight.logic.parser.exceptions.ParseException;

/**
 * The type Command parser test util.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     *
     * @param parser          the parser
     * @param userInput       the user input
     * @param expectedCommand the expected command
     */
    public static void assertParseSuccess(Parser<? extends Command> parser, String userInput,
                                          Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     *
     * @param parser          the parser
     * @param userInput       the user input
     * @param expectedMessage the expected message
     */
    public static void assertParseFailure(Parser<? extends Command> parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
