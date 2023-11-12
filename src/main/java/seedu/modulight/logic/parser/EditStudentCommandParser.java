package seedu.modulight.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_GRADE;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.commands.EditStudentCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.tag.Tag;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditStudentCommandParser implements Parser<EditStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_ID, PREFIX_NAME,
                        PREFIX_EMAIL, PREFIX_TUTORIAL_GROUP, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditStudentCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_ID, PREFIX_NAME,
                PREFIX_EMAIL, PREFIX_TUTORIAL_GROUP);
        EditStudentCommand.EditStudentDescriptor editStudentDescriptor = new EditStudentCommand.EditStudentDescriptor();

        try {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editStudentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()) {
                editStudentDescriptor.setId(ParserUtil.parseId(argMultimap.getValue(PREFIX_STUDENT_ID).get()));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editStudentDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            }
            if (argMultimap.getValue(PREFIX_TUTORIAL_GROUP).isPresent()) {
                editStudentDescriptor.setTutorialGroup(
                        ParserUtil.parseTg(argMultimap.getValue(PREFIX_TUTORIAL_GROUP).get()));
            }
            if (argMultimap.getValue(PREFIX_STUDENT_GRADE).isPresent()) {
                editStudentDescriptor.setStudentGrade(ParserUtil.parseStudentGrade(
                        argMultimap.getValue(PREFIX_STUDENT_GRADE).get()));
            }
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editStudentDescriptor::setTags);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditStudentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditStudentCommand(index, editStudentDescriptor);
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
