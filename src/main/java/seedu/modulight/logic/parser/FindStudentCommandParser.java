package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.List;

import seedu.modulight.logic.commands.FindStudentCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.student.StudentMatchPredicate;
import seedu.modulight.model.studentscore.ScoreMatchPredicate;

/**
 * Parses input arguments and creates a new FindStudentCommand object
 */
public class FindStudentCommandParser implements Parser<FindStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindStudentCommand
     * and returns an FindStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_ID, PREFIX_NAME, PREFIX_EMAIL,
                        PREFIX_TUTORIAL_GROUP, PREFIX_TAG);


        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStudentCommand.MESSAGE_USAGE));
        }

        List<String> idKeywords = argMultimap.getAllValues(PREFIX_STUDENT_ID);
        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> emailKeywords = argMultimap.getAllValues(PREFIX_EMAIL);
        List<String> tutorialGroupKeywords = argMultimap.getAllValues(PREFIX_TUTORIAL_GROUP);
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);


        StudentMatchPredicate stuMatchPredicate = new StudentMatchPredicate(idKeywords, nameKeywords, emailKeywords,
                tutorialGroupKeywords, tagKeywords);
        ScoreMatchPredicate scoreMatchPredicate = new ScoreMatchPredicate(stuMatchPredicate);
        return new FindStudentCommand(stuMatchPredicate, scoreMatchPredicate);
    }
}
