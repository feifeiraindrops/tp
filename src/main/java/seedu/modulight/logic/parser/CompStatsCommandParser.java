package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STATS;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.modulight.logic.commands.CompStatsCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.student.TutorialGroup;






/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class CompStatsCommandParser implements Parser<CompStatsCommand> {
    private static final Set<String> SET_ACCEPTED_STATS = new HashSet<>(Arrays.asList("max", "min", "mean",
            "standardDeviation", "upperQuartile", "lowerQuartile", "skewness"));
    private static final String MESSAGE_INVALID_STATS = "Some statistic measures are not supported yet.\n"
            + "Currently supported statistic measures: max, min, mean,"
            + "standardDeviation, upperQuartile, lowerQuartile, skewness\n";

    /**
     * Parses the given {@code String} of arguments in the context of the CompStatsCommand
     * and returns an CompStatsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompStatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPONENT_NAME, PREFIX_TUTORIAL_GROUP, PREFIX_STATS);

        if (!argMultimap.getPreamble().isEmpty() || !arePrefixesPresent(argMultimap, PREFIX_COMPONENT_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompStatsCommand.MESSAGE_USAGE));
        }

        List<String> allStats = argMultimap.getAllValues(PREFIX_STATS);
        if (!isValidStats(allStats)) {
            throw new ParseException(MESSAGE_INVALID_STATS);
        }
        GcName gcName = new GcName(argMultimap.getValue(PREFIX_COMPONENT_NAME).get());
        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_GROUP)) {
            return new CompStatsCommand(allStats, gcName);
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL_GROUP);
        TutorialGroup tg;
        try {
            tg = new TutorialGroup(argMultimap.getValue(PREFIX_TUTORIAL_GROUP).get());
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        return new CompStatsCommand(allStats, gcName, tg);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean isValidStats(List<String> stats) {
        return SET_ACCEPTED_STATS.containsAll(stats);
    }
}
