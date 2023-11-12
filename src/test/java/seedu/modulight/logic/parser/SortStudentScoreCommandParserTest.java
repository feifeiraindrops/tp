package seedu.modulight.logic.parser;

import static seedu.modulight.logic.parser.ParserTestUtil.assertParserSuccess;

import org.junit.jupiter.api.Test;

import seedu.modulight.logic.commands.SortStudentScoreCommand;
import seedu.modulight.model.gradedcomponent.GcName;



public class SortStudentScoreCommandParserTest {
    private SortStudentScoreCommandParser parser = new SortStudentScoreCommandParser();

    @Test
    public void parse_sortStu_success() {
        String inputWithComponent = " c/Midterm";
        String inputWithBoth = " c/Final r/t";
        assertParserSuccess(inputWithComponent, parser, new SortStudentScoreCommand(new GcName("Midterm"),
                false));
        assertParserSuccess(inputWithBoth, parser, new SortStudentScoreCommand(new GcName("Final"), true));
    }
}
