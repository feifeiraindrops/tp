package seedu.modulight.logic.parser;

import static seedu.modulight.logic.parser.ParserTestUtil.assertParserSuccess;

import org.junit.jupiter.api.Test;

import seedu.modulight.logic.commands.SortStudentCommand;

public class SortStudentCommandParserTest {
    private SortStudentCommandParser parser = new SortStudentCommandParser();

    @Test
    public void parse_sortStu_success() {
        String input = "";
        String inputWithOrder = " o/name";
        String inputWithIsReverse = " r/t";
        String inputWithBoth = " o/tut r/f";
        assertParserSuccess(input, parser, new SortStudentCommand("o", false));
        assertParserSuccess(inputWithOrder, parser, new SortStudentCommand("n", false));
        assertParserSuccess(inputWithIsReverse, parser, new SortStudentCommand("o", true));
        assertParserSuccess(inputWithBoth, parser, new SortStudentCommand("g", false));
    }
}
