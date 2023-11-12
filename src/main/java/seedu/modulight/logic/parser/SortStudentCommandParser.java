package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_REVERSE;

import java.util.Arrays;
import java.util.List;

import seedu.modulight.logic.commands.SortStudentCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SortStudentCommandParser implements Parser<SortStudentCommand> {
    private static final List<String> ORDERS_ACCEPTABLE = Arrays.asList("n", "name", "s", "studentId", "studentID",
            "e", "email", "g", "tutorial", "tut", "tutGroup", "ts", "totalScore", "score", "overall", "totalscore");
    private static final List<String> REVERSES_ACCEPTABLE = Arrays.asList("increasing", "decreasing", "true", "false",
            "1", "0", "t", "f");
    private static final Boolean IS_REVERSE_DEFAULT = false;
    private static final String ORDER_DEFAULT = "o";

    /**
     * Parses the given {@code String} of arguments in the context of the SortStudentCommand
     * and returns an SortStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER, PREFIX_REVERSE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));
        }

        List<String> orders = argMultimap.getAllValues(PREFIX_ORDER);
        List<String> reverses = argMultimap.getAllValues(PREFIX_REVERSE);

        if (orders.size() > 1 && reverses.size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));
        }

        if (orders.size() == 1 && !ORDERS_ACCEPTABLE.contains(orders.get(0))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));
        }

        if (reverses.size() == 1 && !REVERSES_ACCEPTABLE.contains(reverses.get(0))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortStudentCommand.MESSAGE_USAGE));
        }

        String order = ORDER_DEFAULT;
        boolean reverse = IS_REVERSE_DEFAULT;
        if (orders.size() == 1) {
            switch (orders.get(0)) {
            case "n":
            case "name":
                order = "n";
                break;
            case "s":
            case "studentId":
            case "studentID":
                order = "s";
                break;
            case "e":
            case "email":
                order = "e";
                break;
            case "g":
            case "tut":
            case "tutorial":
            case "tutGroup":
                order = "g";
                break;
            case "overall":
            case "score":
            case "ts":
            case "totalscore":
            case "totalScore":
                order = "o";
                break;
            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SortStudentCommand.MESSAGE_USAGE));
            }
        }

        if (reverses.size() == 1) {
            switch (reverses.get(0)) {
            case "decreasing":
            case "0":
            case "false":
            case "f":
                reverse = false;
                break;
            case "increasing":
            case "1":
            case "true":
            case "t":
                reverse = true;
                break;
            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        SortStudentCommand.MESSAGE_USAGE));
            }
        }

        return new SortStudentCommand(order, reverse);
    }
}
