package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.commands.DeleteStudentScoreCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;

/**
 * Parser for DeleteStudentScoreCommand
 */
public class DeleteStudentScoreCommandParser implements Parser<DeleteStudentScoreCommand> {
    /**
     * Parse method to parse strings of argument into DeleteStudentScoreCommand.
     *
     * @param args the String
     * @return Parsed string
     * @throws ParseException if there is exception
     */
    public DeleteStudentScoreCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteStudentScoreCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentScoreCommand.MESSAGE_USAGE), pe);
        }
    }
}
