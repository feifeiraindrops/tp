package seedu.modulight.logic.parser.exceptions;

import seedu.modulight.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ParseException)) {
            return false;
        }
        ParseException otherExc = (ParseException) other;
        return getMessage().equals(otherExc.getMessage());
    }
}
