package seedu.modulight.logic.parser;

import seedu.modulight.logic.commands.ListAllCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;


/**
 * The type List all command parser.
 */
public class ListAllCommandParser implements Parser<ListAllCommand> {
    public ListAllCommand parse(String args) throws ParseException {
        return new ListAllCommand();
    }
}
