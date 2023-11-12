package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_MAX_MARKS;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_WEIGHTAGE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.commands.EditGradedComponentCommand;
import seedu.modulight.logic.commands.EditGradedComponentCommand.EditGradedComponentDescriptor;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class EditGradedComponentCommandParser implements Parser<EditGradedComponentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditGradedComponentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPONENT_NAME, PREFIX_WEIGHTAGE, PREFIX_MAX_MARKS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditGradedComponentCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMPONENT_NAME, PREFIX_WEIGHTAGE, PREFIX_MAX_MARKS);
        EditGradedComponentDescriptor editGradedComponentDescriptor = new EditGradedComponentDescriptor();
        try {
            if (argMultimap.getValue(PREFIX_COMPONENT_NAME).isPresent()) {
                editGradedComponentDescriptor.setGcName(
                        ParserUtil.parseGcName(argMultimap.getValue(PREFIX_COMPONENT_NAME).get())
                );
            }
            if (argMultimap.getValue(PREFIX_WEIGHTAGE).isPresent()) {
                editGradedComponentDescriptor.setWeightage(
                        ParserUtil.parseWeightage(argMultimap.getValue(PREFIX_WEIGHTAGE).get())
                );
            }
            if (argMultimap.getValue(PREFIX_MAX_MARKS).isPresent()) {
                editGradedComponentDescriptor.setMaxMarks(
                        ParserUtil.parseMaxMarks(argMultimap.getValue(PREFIX_MAX_MARKS).get())
                );
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }


        return new EditGradedComponentCommand(index, editGradedComponentDescriptor);
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
