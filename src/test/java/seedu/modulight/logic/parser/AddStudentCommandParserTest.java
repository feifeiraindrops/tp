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
import static seedu.modulight.logic.commands.CommandTestUtil.NAME_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.modulight.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.modulight.logic.commands.CommandTestUtil.SID_DESC_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.SID_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.TAG_DESC_MAKEUP_EXAM;
import static seedu.modulight.logic.commands.CommandTestUtil.TAG_DESC_TA;
import static seedu.modulight.logic.commands.CommandTestUtil.TG_DESC_AMY;
import static seedu.modulight.logic.commands.CommandTestUtil.TG_DESC_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_EMAIL_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_NAME_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_SID_JAMES;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TAG_MAKEUP_EXAM;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TAG_TA;
import static seedu.modulight.logic.commands.CommandTestUtil.VALID_TG_JAMES;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.modulight.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;
import static seedu.modulight.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modulight.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.modulight.testutil.TypicalStudents.AMY;
import static seedu.modulight.testutil.TypicalStudents.JAMES;

import org.junit.jupiter.api.Test;

import seedu.modulight.logic.Messages;
import seedu.modulight.logic.commands.AddStudentCommand;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.StudentEmail;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.model.tag.Tag;
import seedu.modulight.testutil.StudentBuilder;

public class AddStudentCommandParserTest {
    private AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(JAMES).withTags(VALID_TAG_TA).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SID_DESC_JAMES + NAME_DESC_JAMES + EMAIL_DESC_JAMES
                + TG_DESC_JAMES + TAG_DESC_TA, new AddStudentCommand(expectedStudent));


        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(JAMES).withTags(VALID_TAG_MAKEUP_EXAM, VALID_TAG_TA)
                .build();
        assertParseSuccess(parser, SID_DESC_JAMES + NAME_DESC_JAMES + EMAIL_DESC_JAMES + TG_DESC_JAMES
                        + TAG_DESC_MAKEUP_EXAM + TAG_DESC_TA, new AddStudentCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedStudentString = SID_DESC_JAMES + NAME_DESC_JAMES + EMAIL_DESC_JAMES + TG_DESC_JAMES
                + TAG_DESC_TA;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple sid
        assertParseFailure(parser, SID_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple tutorial groups
        assertParseFailure(parser, TG_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_GROUP));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedStudentString + SID_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + TG_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_STUDENT_ID, PREFIX_EMAIL,
                        PREFIX_TUTORIAL_GROUP));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid student id
        assertParseFailure(parser, INVALID_SID_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID));

        // invalid address
        assertParseFailure(parser, INVALID_TG_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_GROUP));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedStudentString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedStudentString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid student id
        assertParseFailure(parser, validExpectedStudentString + INVALID_SID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID));

        // invalid tutorial group
        assertParseFailure(parser, validExpectedStudentString + INVALID_TG_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL_GROUP));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + SID_DESC_AMY + EMAIL_DESC_AMY + TG_DESC_AMY,
                new AddStudentCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // tag and tutorial are optional inputs
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_JAMES + SID_DESC_JAMES + EMAIL_DESC_JAMES + TG_DESC_JAMES,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_JAMES + VALID_SID_JAMES + EMAIL_DESC_JAMES + TG_DESC_JAMES,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_JAMES + SID_DESC_JAMES + VALID_EMAIL_JAMES + TG_DESC_JAMES,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_JAMES + VALID_SID_JAMES + VALID_EMAIL_JAMES + VALID_TG_JAMES,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + SID_DESC_JAMES + EMAIL_DESC_JAMES + TG_DESC_JAMES
                + TAG_DESC_MAKEUP_EXAM + TAG_DESC_TA, StudentName.MESSAGE_CONSTRAINTS);

        // invalid student id
        assertParseFailure(parser, NAME_DESC_JAMES + INVALID_SID_DESC + EMAIL_DESC_JAMES + TG_DESC_JAMES
                + TAG_DESC_MAKEUP_EXAM + TAG_DESC_TA, StudentId.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_JAMES + SID_DESC_JAMES + INVALID_EMAIL_DESC + TG_DESC_JAMES
                + TAG_DESC_MAKEUP_EXAM + TAG_DESC_TA, StudentEmail.MESSAGE_CONSTRAINTS);

        // invalid tutorial
        assertParseFailure(parser, NAME_DESC_JAMES + SID_DESC_JAMES + EMAIL_DESC_JAMES + INVALID_TG_DESC
                + TAG_DESC_MAKEUP_EXAM + TAG_DESC_TA, TutorialGroup.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_JAMES + SID_DESC_JAMES + EMAIL_DESC_JAMES + TG_DESC_JAMES
                + INVALID_TAG_DESC + VALID_TAG_TA, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + SID_DESC_JAMES + EMAIL_DESC_JAMES + INVALID_TG_DESC,
                StudentName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_JAMES + SID_DESC_JAMES + EMAIL_DESC_JAMES
                        + TG_DESC_JAMES + TAG_DESC_MAKEUP_EXAM + TAG_DESC_TA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
    }
}
