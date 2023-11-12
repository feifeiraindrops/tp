package seedu.modulight.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.MaxMarks;
import seedu.modulight.model.gradedcomponent.Weightage;
import seedu.modulight.model.student.StudentEmail;
import seedu.modulight.model.student.StudentGrade;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.model.tag.Tag;



public class ParserUtilTest {
    @Test
    public void parse_name_success() {
        try {
            assertEquals(ParserUtil.parseName("   Ben"), new StudentName("Ben"));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_id_success() {
        try {
            assertEquals(ParserUtil.parseId("   A0123456U"), new StudentId("A0123456U"));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_tut_success() {
        try {
            assertEquals(ParserUtil.parseTg("   T01"), new TutorialGroup("T01"));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_index_success() {
        try {
            assertEquals(ParserUtil.parseIndex("   10"), Index.fromOneBased(10));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_gcName_success() {
        try {
            assertEquals(ParserUtil.parseGcName("   Midterm"), new GcName("Midterm"));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_email_success() {
        try {
            assertEquals(ParserUtil.parseEmail("   xyz@gmail.com"), new StudentEmail("xyz@gmail.com"));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_tag_success() {
        try {
            assertEquals(ParserUtil.parseTag("   good"), new Tag("good"));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_tags_success() {
        try {
            Set<Tag> output = new HashSet<>(Arrays.asList(new Tag("good"), new Tag("TA")));
            assertEquals(ParserUtil.parseTags(List.of("good", "TA")), output);
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_studentGrade_success() {
        try {
            assertEquals(ParserUtil.parseStudentGrade("A"), new StudentGrade("A"));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_weightage_success() {
        try {
            assertEquals(ParserUtil.parseWeightage("70"), new Weightage(70));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_maxMark_success() {
        try {
            assertEquals(ParserUtil.parseMaxMarks("100"), new MaxMarks(100));
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }

    @Test
    public void parse_score_success() {
        try {
            assertEquals(ParserUtil.parseScore("100"), 100);
        } catch (ParseException ce) {
            throw new AssertionError("Execution of parser in positive test cases should not fail.", ce);
        }
    }
    @Test
    public void parse_maxMark_failure() {
        try {
            assertEquals(ParserUtil.parseMaxMarks("abc"), new MaxMarks(100));
            throw new AssertionError("Execution of command in negative test cases should not succeed.");
        } catch (ParseException ce) {
            ParseException expectedException = new ParseException("A value could not be parsed into a number.");
            assertEquals(expectedException, ce);
        }
    }

}
