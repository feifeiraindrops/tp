package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_MARKS;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.commands.EditStudentScoreCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.tag.Tag;

/**
 * A Class to handle parsing for EditStudentScoreCommand.
 */
public class EditStudentScoreCommandParser implements Parser<EditStudentScoreCommand> {

    /**
     * Parse the string arguments
     * @param args arguments in string
     * @return EditStudentScoreCommand
     * @throws ParseException an Exception
     */
    public EditStudentScoreCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MARKS, PREFIX_COMMENT, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MARKS, PREFIX_COMMENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditStudentScoreCommand.MESSAGE_USAGE), pe);
        }

        EditStudentScoreCommand.EditStudentScoreDescriptor editStudentScoreDescriptor =
                new EditStudentScoreCommand.EditStudentScoreDescriptor();
        try {
            if (argMultimap.getValue(PREFIX_MARKS).isPresent()) {
                editStudentScoreDescriptor.setScore(
                        ParserUtil.parseScore(argMultimap.getValue(PREFIX_MARKS).get())
                );
            }

            if (argMultimap.getValue(PREFIX_COMMENT).isPresent()) {
                editStudentScoreDescriptor.setComment(
                        argMultimap.getValue(PREFIX_COMMENT).get());
            }

            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editStudentScoreDescriptor::setTags);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        return new EditStudentScoreCommand(index, editStudentScoreDescriptor, true);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
