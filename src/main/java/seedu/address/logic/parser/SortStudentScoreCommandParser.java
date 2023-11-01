package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortStudentScoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradedcomponent.GcName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SortStudentScoreCommandParser implements Parser<SortStudentScoreCommand> {
    private static final List<String> REVERSES_ACCEPTABLE = Arrays.asList("increasing", "decreasing", "true", "false",
            "1", "0", "t", "f");
    private static final Boolean REVERSE_DEFAULT = false;

    /**
     * Parses the given {@code String} of arguments in the context of the SortStudentCommand
     * and returns an SortStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortStudentScoreCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPONENT_NAME, PREFIX_REVERSE);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMPONENT_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortStudentScoreCommand.MESSAGE_USAGE));
        }

        List<String> reverses = argMultimap.getAllValues(PREFIX_REVERSE);

        if (reverses.size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortStudentScoreCommand.MESSAGE_USAGE));
        }

        if (reverses.size() == 1 && !REVERSES_ACCEPTABLE.contains(reverses.get(0))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortStudentScoreCommand.MESSAGE_USAGE));
        }

        boolean reverse = REVERSE_DEFAULT;
        GcName gcName = new GcName(argMultimap.getValue(PREFIX_COMPONENT_NAME).get());
        if (reverses.size() != 1) {
            return new SortStudentScoreCommand(gcName, reverse);
        }
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
                    SortStudentScoreCommand.MESSAGE_USAGE));
        }

        return new SortStudentScoreCommand(gcName, reverse);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
