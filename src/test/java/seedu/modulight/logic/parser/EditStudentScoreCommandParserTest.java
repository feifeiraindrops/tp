package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.COMMENT_DESC_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.COMMENT_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.INVALID_SCORE_DESC;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.INVALID_TAG_DESC;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.SCORE_DESC_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.SCORE_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.SID_DESC_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.TAG_DESC_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.TAG_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_COMMENT_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_SCORE_JAMES;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_TAG_AMY;
import static seedu.modulight.logic.commands.CommandStudentScoreTestUtil.VALID_TAG_JAMES;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_MARKS;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.modulight.logic.parser.ParserTestUtil.assertParserFailure;
import static seedu.modulight.logic.parser.ParserTestUtil.assertParserSuccess;
import static seedu.modulight.testutil.TypicalIndexes.INDEX_FIRST_SCORE;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.commands.EditStudentScoreCommand;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.testutil.EditStudentScoreDescriptorBuilder;



class EditStudentScoreCommandParserTest {
    private static final EditStudentScoreCommandParser parser = new EditStudentScoreCommandParser();
    private static final String EMPTY_SCORE = " " + PREFIX_MARKS;
    private static final String EMPTY_COMMENT = " " + PREFIX_COMMENT;
    private static final String EMPTY_TAG = " " + PREFIX_TAG;

    private static final String INVALID_FORMAT_MESSAGE = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, EditStudentScoreCommand.MESSAGE_USAGE);


    @Test
    public void parse_validIndex_success() {
        // index within range
        Index targetIndex = INDEX_FIRST_SCORE;
        String input = targetIndex.getOneBased() + SCORE_DESC_JAMES + COMMENT_DESC_JAMES;
        boolean useFiltered = false;

        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor = new EditStudentScoreDescriptorBuilder()
                .withScore(VALID_SCORE_JAMES).withComment(VALID_COMMENT_JAMES).build();
        EditStudentScoreCommand expectedCommand = new EditStudentScoreCommand(targetIndex, descriptor, useFiltered);
        assertParserSuccess(input, parser, expectedCommand);

    }

    @Test
    public void parse_notParsableIndex_failure() {
        // not a number
        String inputIndex = "one";
        try {
            Index index = ParserUtil.parseIndex(inputIndex);
        } catch (ParseException e) {
            String input = inputIndex + SCORE_DESC_AMY + COMMENT_DESC_AMY;
            ParseException pe = new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditStudentScoreCommand.MESSAGE_USAGE), e);
            assertParserFailure(input, parser, pe.getMessage());
        }

        // non integer
        inputIndex = "1.3";
        try {
            Index index = ParserUtil.parseIndex(inputIndex);
        } catch (ParseException e) {
            String input = inputIndex + SCORE_DESC_AMY + COMMENT_DESC_AMY;
            ParseException pe = new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditStudentScoreCommand.MESSAGE_USAGE), e);
            assertParserFailure(input, parser, pe.getMessage());
        }

        // negative value
        inputIndex = "-2";
        try {
            Index index = ParserUtil.parseIndex(inputIndex);
        } catch (ParseException e) {
            String input = inputIndex + SCORE_DESC_AMY + COMMENT_DESC_AMY;
            ParseException pe = new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditStudentScoreCommand.MESSAGE_USAGE), e);
            assertParserFailure(input, parser, pe.getMessage());
        }

    }

    @Test
    public void parse_invalidPrefixToEdit_failure() {
        // single invalid prefix
        String input = INDEX_FIRST_SCORE.getOneBased() + SID_DESC_AMY;
        assertParserFailure(input, parser, INVALID_FORMAT_MESSAGE);

        input = INDEX_FIRST_SCORE.getOneBased() + " zzz/Hello";
        assertParserFailure(input, parser, INVALID_FORMAT_MESSAGE);

        // valid prefixes with an invalid prefix
        input = INDEX_FIRST_SCORE.getOneBased() + SID_DESC_AMY + COMMENT_DESC_AMY;
        assertParserFailure(input, parser, INVALID_FORMAT_MESSAGE);

        input = INDEX_FIRST_SCORE.getOneBased() + " qwe/Hello" + COMMENT_DESC_AMY;
        assertParserFailure(input, parser, INVALID_FORMAT_MESSAGE);

    }

    @Test
    public void parse_validValues_success() {
        // Valid score
        Index targetIndex = INDEX_FIRST_SCORE;
        String input = targetIndex.getOneBased() + SCORE_DESC_JAMES;
        boolean useFiltered = false;

        EditStudentScoreCommand.EditStudentScoreDescriptor descriptor = new EditStudentScoreDescriptorBuilder()
                .withScore(VALID_SCORE_JAMES).build();
        EditStudentScoreCommand expectedCommand = new EditStudentScoreCommand(targetIndex, descriptor, useFiltered);
        assertParserSuccess(input, parser, expectedCommand);

        // valid comment
        input = targetIndex.getOneBased() + COMMENT_DESC_JAMES;
        descriptor = new EditStudentScoreDescriptorBuilder().withComment(VALID_COMMENT_JAMES).build();
        expectedCommand = new EditStudentScoreCommand(targetIndex, descriptor, useFiltered);
        assertParserSuccess(input, parser, expectedCommand);

        // valid single tag
        input = targetIndex.getOneBased() + TAG_DESC_JAMES;
        descriptor = new EditStudentScoreDescriptorBuilder().withTags(VALID_TAG_JAMES).build();
        expectedCommand = new EditStudentScoreCommand(targetIndex, descriptor, useFiltered);
        assertParserSuccess(input, parser, expectedCommand);

        // valid multiple tag
        input = targetIndex.getOneBased() + TAG_DESC_JAMES + TAG_DESC_AMY;
        descriptor = new EditStudentScoreDescriptorBuilder().withTags(VALID_TAG_JAMES, VALID_TAG_AMY).build();
        expectedCommand = new EditStudentScoreCommand(targetIndex, descriptor, useFiltered);
        assertParserSuccess(input, parser, expectedCommand);

        // valid combined attributes
        input = targetIndex.getOneBased() + SCORE_DESC_JAMES
                + COMMENT_DESC_JAMES + TAG_DESC_JAMES + TAG_DESC_AMY;
        descriptor = new EditStudentScoreDescriptorBuilder().withScore(VALID_SCORE_JAMES)
                .withComment(VALID_COMMENT_JAMES).withTags(VALID_TAG_JAMES, VALID_TAG_AMY).build();
        expectedCommand = new EditStudentScoreCommand(targetIndex, descriptor, useFiltered);
        assertParserSuccess(input, parser, expectedCommand);
    }
    @Test
    public void parse_invalidValues_failure() {
        // score with no value
        Index targetIndex = INDEX_FIRST_SCORE;
        String input = targetIndex.getOneBased() + EMPTY_SCORE;
        Optional<Object> optional = Optional.of("");
        try {
            optional.get();
        } catch (Exception e) {
            assertParserFailure(input, parser, e.getMessage());
        }

        // score not parsable
        input = targetIndex.getOneBased() + INVALID_SCORE_DESC;
        optional = Optional.of(INVALID_SCORE_DESC);
        try {
            optional.get();
        } catch (Exception e) {
            assertParserFailure(input, parser, e.getMessage());
        }

        // invalid tag name
        input = targetIndex.getOneBased() + INVALID_TAG_DESC;
        optional = Optional.of(INVALID_TAG_DESC);
        try {
            optional.get();
        } catch (Exception e) {
            assertParserFailure(input, parser, e.getMessage());
        }

        // tag with no value
        input = targetIndex.getOneBased() + EMPTY_TAG;
        optional = Optional.of("");
        try {
            optional.get();
        } catch (Exception e) {
            assertParserFailure(input, parser, e.getMessage());
        }

        // multiple valid edit attributes with single invalid keyword
        input = targetIndex.getOneBased() + SCORE_DESC_JAMES + COMMENT_DESC_JAMES
                + TAG_DESC_AMY + INVALID_TAG_DESC;
        optional = Optional.of(INVALID_TAG_DESC);
        try {
            optional.get();
        } catch (Exception e) {
            assertParserFailure(input, parser, e.getMessage());
        }
    }
}
