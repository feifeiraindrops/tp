package seedu.modulight.logic.parser;

import static seedu.modulight.logic.parser.ParserTestUtil.assertParserSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.modulight.logic.commands.StatsCommand;
import seedu.modulight.model.student.TutorialGroup;




public class StatsCommandParserTest {
    private StatsCommandParser parser = new StatsCommandParser();
    @Test
    public void parse_stat_success() {
        String input = "";
        String inputWithTut = " g/T01";
        String inputWithStats = " st/max";
        assertParserSuccess(input, parser, new StatsCommand(new ArrayList<>()));
        assertParserSuccess(inputWithTut, parser, new StatsCommand(new ArrayList<>(), new TutorialGroup("T01")));
        assertParserSuccess(inputWithStats, parser, new StatsCommand(new ArrayList<>(List.of("max"))));
    }
}
