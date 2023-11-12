package seedu.modulight.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.modulight.logic.commands.statscalculator.StatsCalculator;
import seedu.modulight.model.Model;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.TutorialGroup;

/**
 * Calculates the statics.
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculate the overall statistics.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Here are the statistics:\n";
    public static final String MESSAGE_EMPTY = "Please have at least one score fulfilling the condition.\n";
    public static final String MESSAGE_EMPTY_TUT = "This tutorial group does not have any valid scores."
            + "Please check if the information is correct.";

    protected final TutorialGroup tutorialGroup;
    protected final boolean isForTut;
    protected List<String> stats;

    /**
     * Creates a StatsCommand to calculate the overall stats.
     */
    public StatsCommand(List<String> allStats) {
        this.isForTut = false;
        this.tutorialGroup = null;
        this.stats = allStats;
    }

    /**
     * Creates a StatsCommand to calculate the overall stats of a given tutorial group.
     * @param tutorialGroup the tutorial group to be analyzed
     */
    public StatsCommand(List<String> allStats, TutorialGroup tutorialGroup) {
        this.isForTut = true;
        this.tutorialGroup = tutorialGroup;
        this.stats = allStats;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Student> students = model.getStudentBook().getStudentList();
        if (isForTut) {
            return new CommandResult(generateTutStatsSummary(students, tutorialGroup.toString()));
        }
        return new CommandResult(generateOverallStatsSummary(students));
    }

    private String generateOverallStatsSummary(List<Student> students) {
        if (students.isEmpty()) {
            return MESSAGE_EMPTY;
        }
        List<Float> studentScores = students.stream().map(Student::getTotalScore).collect(Collectors.toList());
        return generateSummaryFromScores(studentScores, MESSAGE_SUCCESS);
    }

    private String generateTutStatsSummary(List<Student> students, String tutGroup) {
        List<Float> studentScores = students.stream().filter(student -> student.getTutorial().toString()
                .equals(tutGroup)).map(Student::getTotalScore).collect(Collectors.toList());
        if (studentScores.isEmpty()) {
            return MESSAGE_EMPTY_TUT;
        }
        return generateSummaryFromScores(studentScores, generateSummaryOpenning(tutGroup));
    }

    protected String generateSummaryFromScores(List<Float> scores, String opening) {
        StatsCalculator st = new StatsCalculator(scores);
        StringBuilder sb = new StringBuilder(opening);
        if (scores.isEmpty()) {
            return MESSAGE_EMPTY;
        }
        if (stats.isEmpty()) {
            sb.append(String.format("MAX = %.2f, MIN = %.2f, MEAN = %.2f, STANDARD DEVIATION = %.2f\n",
                    st.getMax(), st.getMin(), st.getMean(), st.getStd()));
            sb.append(String.format("UPPER QUARTILE = %.2f, LOWER QUARTILE = %.2f, SKEWNESS = %.2f\n",
                    st.getUpperQuartile(), st.getLowerQuartile(), st.getSkewness()));
        } else {
            int count = 0;
            for (String statsMeasure : stats) {
                switch (statsMeasure) {
                case "max":
                    sb.append(String.format("MAX = %.2f", st.getMax()));
                    break;
                case "min":
                    sb.append(String.format("MIN = %.2f", st.getMin()));
                    break;
                case "mean":
                    sb.append(String.format("MEAN = %.2f", st.getMean()));
                    break;
                case "standardDeviation":
                    sb.append(String.format("STANDARD DEVIATION = %.2f", st.getStd()));
                    break;
                case "upperQuartile":
                    sb.append(String.format("UPPER QUARTILE = %.2f", st.getUpperQuartile()));
                    break;
                case "lowerQuartile":
                    sb.append(String.format("LOWER QUARTILE = %.2f", st.getLowerQuartile()));
                    break;
                case "skewness":
                    sb.append(String.format("SKEWNESS = %.2f", st.getSkewness()));
                    break;
                default:
                    sb.append(String.format("INVALID_STATS_MEASURE: %s", statsMeasure));
                }
                count++;
                if (count % 4 != 0 && count != stats.size()) {
                    sb.append(", ");
                } else {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private String generateSummaryOpenning(String tutGroup) {
        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS);
        sb.insert(MESSAGE_SUCCESS.length() - 2, String.format(" of Tutorial Group %S", tutGroup));
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatsCommand)) {
            return false;
        }
        StatsCommand otherCommand = (StatsCommand) other;
        boolean isTutMatch = !isForTut || !otherCommand.isForTut
                || Objects.equals(tutorialGroup, otherCommand.tutorialGroup);
        return stats.equals(otherCommand.stats)
                && isForTut == otherCommand.isForTut
                && isTutMatch;
    }

}
