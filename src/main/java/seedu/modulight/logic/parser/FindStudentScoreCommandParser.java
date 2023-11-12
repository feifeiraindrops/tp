package seedu.modulight.logic.parser;


import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.List;

import seedu.modulight.logic.commands.FindStudentCommand;
import seedu.modulight.logic.commands.FindStudentScoreCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.studentscore.ScoreMatchPredicate;


/**
 * The type Find student score command parser.
 */
public class FindStudentScoreCommandParser implements Parser<FindStudentScoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindStudentScoreCommand
     * and returns an FindStudentScoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindStudentScoreCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_ID, PREFIX_NAME, PREFIX_TUTORIAL_GROUP,
                        PREFIX_COMPONENT_NAME, PREFIX_TAG, PREFIX_COMMENT);


        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStudentCommand.MESSAGE_USAGE));
        }

        List<String> idKeywords = argMultimap.getAllValues(PREFIX_STUDENT_ID);
        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> commentKeywords = argMultimap.getAllValues(PREFIX_COMMENT);
        List<String> tutorialGroupKeywords = argMultimap.getAllValues(PREFIX_TUTORIAL_GROUP);
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);
        List<String> gcKeywords = argMultimap.getAllValues(PREFIX_COMPONENT_NAME);


        ScoreMatchPredicate scoreMatchPredicate = new ScoreMatchPredicate(idKeywords, nameKeywords,
                tutorialGroupKeywords, tagKeywords, commentKeywords, gcKeywords);
        return new FindStudentScoreCommand(scoreMatchPredicate);
    }
}
