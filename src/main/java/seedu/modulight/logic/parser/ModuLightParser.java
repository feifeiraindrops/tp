package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.modulight.commons.core.LogsCenter;
import seedu.modulight.logic.commands.AddGradedComponentCommand;
import seedu.modulight.logic.commands.AddStudentCommand;
import seedu.modulight.logic.commands.AutoGradeCommand;
import seedu.modulight.logic.commands.ClearAllCommand;
import seedu.modulight.logic.commands.Command;
import seedu.modulight.logic.commands.CompStatsCommand;
import seedu.modulight.logic.commands.DeleteGradedComponentCommand;
import seedu.modulight.logic.commands.DeleteStudentCommand;
import seedu.modulight.logic.commands.EditGradedComponentCommand;
import seedu.modulight.logic.commands.EditStudentCommand;
import seedu.modulight.logic.commands.EditStudentScoreCommand;
import seedu.modulight.logic.commands.ExitCommand;
import seedu.modulight.logic.commands.FindGradedComponentCommand;
import seedu.modulight.logic.commands.FindStudentCommand;
import seedu.modulight.logic.commands.FindStudentScoreCommand;
import seedu.modulight.logic.commands.HelpCommand;
import seedu.modulight.logic.commands.ListAllCommand;
import seedu.modulight.logic.commands.SortStudentCommand;
import seedu.modulight.logic.commands.SortStudentScoreCommand;
import seedu.modulight.logic.commands.StatsCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class ModuLightParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ModuLightParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {
        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);
        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);
        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);
        case AddGradedComponentCommand.COMMAND_WORD:
            return new AddGradedComponentCommandParser().parse(arguments);
        case EditGradedComponentCommand.COMMAND_WORD:
            return new EditGradedComponentCommandParser().parse(arguments);
        case DeleteGradedComponentCommand.COMMAND_WORD:
            return new DeleteGradedComponentCommandParser().parse(arguments);
        case EditStudentScoreCommand.COMMAND_WORD:
            return new EditStudentScoreCommandParser().parse(arguments);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case FindStudentCommand.COMMAND_WORD:
            return new FindStudentCommandParser().parse(arguments);
        case FindStudentScoreCommand.COMMAND_WORD:
            return new FindStudentScoreCommandParser().parse(arguments);
        case FindGradedComponentCommand.COMMAND_WORD:
            return new FindGradedComponentCommandParser().parse(arguments);
        case ClearAllCommand.COMMAND_WORD:
            return new ClearAllCommand();
        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);
        case CompStatsCommand.COMMAND_WORD:
            return new CompStatsCommandParser().parse(arguments);
        case SortStudentCommand.COMMAND_WORD:
            return new SortStudentCommandParser().parse(arguments);
        case SortStudentScoreCommand.COMMAND_WORD:
            return new SortStudentScoreCommandParser().parse(arguments);
        case AutoGradeCommand.COMMAND_WORD:
            return new AutoGradeCommandParser().parse(arguments);
        case ListAllCommand.COMMAND_WORD:
            return new ListAllCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
