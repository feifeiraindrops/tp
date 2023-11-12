package seedu.modulight.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.commands.AutoGradeCommand.AutoGradeType.ABSOLUTE;
import static seedu.modulight.logic.commands.AutoGradeCommand.AutoGradeType.PERCENTILE;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_AUTOGRADE_TYPE;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_PASSING_GRADE;

import java.util.stream.Stream;

import seedu.modulight.logic.commands.AutoGradeCommand;
import seedu.modulight.logic.commands.AutoGradeCommand.AutoGradeType;
import seedu.modulight.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AutoGradeCommand object
 */
public class AutoGradeCommandParser implements Parser<AutoGradeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AutoGradeCommand
     * and returns an AutoGradeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutoGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AUTOGRADE_TYPE, PREFIX_PASSING_GRADE);

        if (!arePrefixesPresent(argMultimap, PREFIX_AUTOGRADE_TYPE, PREFIX_PASSING_GRADE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutoGradeCommand.MESSAGE_USAGE));
        }

        String[] input = argMultimap.getValue(PREFIX_PASSING_GRADE).get().split(" ");
        float[] arrayOfFloat = mapToFloat(input);

        String autoGradeTypeString = argMultimap.getValue(PREFIX_AUTOGRADE_TYPE).get();
        AutoGradeType autoGradeType = checkAutoGradeType(autoGradeTypeString);

        return new AutoGradeCommand(arrayOfFloat, autoGradeType);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private float[] mapToFloat(String[] input) throws ParseException {
        float[] array = new float[input.length];

        for (int i = 0; i < input.length; i++) {
            try {
                array[i] = Float.parseFloat(input[i]);
            } catch (NumberFormatException e) {
                throw new ParseException(AutoGradeCommand.MESSAGE_PARSE_FLOAT);
            }

            boolean isLessThanZero = array[i] < 0;
            if (isLessThanZero) {
                throw new ParseException(AutoGradeCommand.MESSAGE_VALUE_LESS_THAN_ZERO);
            }

            boolean isMoreThanMaximumMark = array[i] > 100;
            if (isMoreThanMaximumMark) {
                throw new ParseException(AutoGradeCommand.MESSAGE_VALUE_MORE_THAN_MAXIMUM);
            }
        }

        for (int i = 1; i < input.length; i++) {
            if (array[i - 1] <= array[i]) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AutoGradeCommand.MESSAGE_INCREASING_VALUE));
            }
        }

        return array;
    }

    private AutoGradeType checkAutoGradeType(String autoGradeType) throws ParseException {
        switch (autoGradeType) {
        case "percentile":
        case "Percentile":
        case "p":
            return PERCENTILE;
        case "absolute":
        case "Absolute":
        case "a":
            return ABSOLUTE;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutoGradeCommand.MESSAGE_USAGE));
        }
    }
}
