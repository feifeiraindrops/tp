package seedu.modulight.logic.parser;

import static seedu.modulight.logic.parser.ParserTestUtil.assertParserSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.modulight.logic.commands.CompStatsCommand;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.student.TutorialGroup;




public class CompStatsCommandParserTest {
    private CompStatsCommandParser parser = new CompStatsCommandParser();
    @Test
    public void parse_stat_success() {
        String input = " c/Midterm";
        String inputWithTut = " g/T01 c/Midterm";
        String inputWithCompStats = " c/Midterm st/max";
        assertParserSuccess(input, parser, new CompStatsCommand(new ArrayList<>(), new GcName("Midterm")));
        assertParserSuccess(inputWithTut, parser, new CompStatsCommand(new ArrayList<>(),
                new GcName("Midterm"), new TutorialGroup("T01")));
        assertParserSuccess(inputWithCompStats, parser, new CompStatsCommand(new ArrayList<>(List.of("max")),
                new GcName("Midterm")));
    }
}
