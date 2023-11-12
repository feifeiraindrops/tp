package seedu.modulight.logic.parser;

import static seedu.modulight.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modulight.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.EMAIL_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.modulight.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.modulight.logic.commands.CommandTestUtil.INVALID_SID_DESC;
import static seedu.modulight.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.modulight.logic.commands.CommandTestUtil.INVALID_TG_DESC;
import static seedu.modulight.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.SID_DESC_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.SID_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.TAG_DESC_MAKEUP_EXAM;
import static seedu.modulight.logic.commands.CommandTestUtil.TAG_DESC_TA;
import static seedu.modulight.logic.commands.CommandTestUtil.TG_DESC_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.TG_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_SID_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_SID_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TAG_MAKEUP_EXAM;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TAG_TA;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TG_AMY;
import static seedu.modulight.logic.commands.EditStudentCommand.EditStudentDescriptor;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;
import static seedu.modulight.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modulight.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.modulight.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.modulight.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.modulight.testutil.TypicalIndexes.INDEX_THIRD_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.Messages;
import seedu.modulight.logic.commands.EditStudentCommand;
import seedu.modulight.model.student.StudentEmail;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.model.tag.Tag;
import seedu.modulight.testutil.EditStudentDescriptorBuilder;

public class EditStudentCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE);

    private EditStudentCommandParser parser = new EditStudentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditStudentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, StudentName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_SID_DESC, StudentId.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, StudentEmail.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_TG_DESC, TutorialGroup.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_SID_DESC + EMAIL_DESC_AMY, StudentId.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Student} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_TA + TAG_DESC_MAKEUP_EXAM + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_TA + TAG_EMPTY + TAG_DESC_MAKEUP_EXAM, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_TA + TAG_DESC_MAKEUP_EXAM, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_TG_AMY + VALID_SID_AMY,
                StudentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_STUDENT;
        String userInput = targetIndex.getOneBased() + SID_DESC_JAMES + TAG_DESC_MAKEUP_EXAM
                + EMAIL_DESC_AMY + TG_DESC_AMY + NAME_DESC_AMY + TAG_DESC_TA;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withId(VALID_SID_JAMES).withEmail(VALID_EMAIL_AMY).withTg(VALID_TG_AMY)
                .withTags(VALID_TAG_MAKEUP_EXAM, VALID_TAG_TA).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + SID_DESC_JAMES + EMAIL_DESC_AMY;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withId(VALID_SID_JAMES)
                .withEmail(VALID_EMAIL_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_STUDENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + SID_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withId(VALID_SID_AMY).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + TG_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withTg(VALID_TG_AMY).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_TA;
        descriptor = new EditStudentDescriptorBuilder().withTags(VALID_TAG_TA).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + INVALID_SID_DESC + SID_DESC_JAMES;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + SID_DESC_JAMES + INVALID_SID_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + SID_DESC_AMY + TG_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_TA + SID_DESC_AMY + TG_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_TA
                + SID_DESC_JAMES + TG_DESC_JAMES + EMAIL_DESC_JAMES + TAG_DESC_MAKEUP_EXAM;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID, PREFIX_EMAIL, PREFIX_TUTORIAL_GROUP));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_SID_DESC + INVALID_TG_DESC + INVALID_EMAIL_DESC
                + INVALID_SID_DESC + INVALID_TG_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID, PREFIX_EMAIL, PREFIX_TUTORIAL_GROUP));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_STUDENT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withTags().build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
