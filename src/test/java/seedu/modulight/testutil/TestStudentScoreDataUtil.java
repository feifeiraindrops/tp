package seedu.modulight.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.studentscore.model.ReadOnlyStudentScoreBook;
import seedu.modulight.model.studentscore.model.StudentScoreBook;


/**
 * Contains utility methods for populating StudentBook with sample data.
 */
public class TestStudentScoreDataUtil {
    private static GradedComponent gc1 = TestGcDataUtil.getTestGradedComponents()[0];
    private static GradedComponent gc2 = TestGcDataUtil.getTestGradedComponents()[1];

    public static ArrayList<StudentScore> getTestStudentScoresEmpty() {
        return new ArrayList<>();
    }

    public static ArrayList<StudentScore> getTestStudentZeroScores() {
        GcName gcName1 = gc1.getName();
        StudentScore[] studentScores = new StudentScore[]{
            new StudentScore(new StudentId("A0000000Y"), gcName1, 0),
            new StudentScore(new StudentId("A0000001Y"), gcName1, 0),
            new StudentScore(new StudentId("A0000002Y"), gcName1, 0),
            new StudentScore(new StudentId("A0000003Y"), gcName1, 0),
            new StudentScore(new StudentId("A0000004Y"), gcName1, 0),
            new StudentScore(new StudentId("A0000005Y"), gcName1, 0),
        };
        for (StudentScore score : studentScores) {
            score.setGradedComponent(gc1);
        }
        return new ArrayList<>(List.of(studentScores));
    }

    public static ArrayList<StudentScore> getTestStudentScores() {
        float markToBeAdded = 0;
        int size = getTestStudentZeroScores().size();
        ArrayList<StudentScore> newScores = new ArrayList<>();
        for (StudentScore score : getTestStudentZeroScores()) {
            StudentScore newScore = new StudentScore(score.getStudentId(), score.getGcName(), markToBeAdded);
            newScore.setGradedComponent(gc1);
            newScores.add(newScore);
            markToBeAdded += size > 1 ? (float) 50 / (size - 1) : 0;
        }
        return newScores;
    }

    public static ArrayList<StudentScore> getTestStudentTwoScores() {
        float markToBeAdded = 100;
        int size = getTestStudentScores().size();
        ArrayList<StudentScore> oldScores = getTestStudentScores();
        ArrayList<StudentScore> newScores = new ArrayList<>(oldScores);
        for (StudentScore score : oldScores) {
            StudentScore newScore = new StudentScore(score.getStudentId(), gc2.getName(), markToBeAdded);
            newScore.setGradedComponent(gc2);
            newScores.add(newScore);
            markToBeAdded -= size > 1 ? (float) 50 / (size - 1) : 0;
        }
        return newScores;
    }

    public static ReadOnlyStudentScoreBook getSampleStudentScoreBook(String selectedScore) {
        StudentScoreBook sampleScore = new StudentScoreBook();
        ArrayList<StudentScore> scoreToBeAdded;
        switch (selectedScore) {
        case "create":
            scoreToBeAdded = getTestStudentScores();
            break;
        case "reverse":
            scoreToBeAdded = getTestStudentScores();
            Collections.reverse(scoreToBeAdded);
            break;
        case "zeroScore":
            scoreToBeAdded = getTestStudentZeroScores();
            break;
        case "twoScores":
            scoreToBeAdded = getTestStudentTwoScores();
            break;
        default:
            scoreToBeAdded = getTestStudentScoresEmpty();
        }
        for (StudentScore testScore: scoreToBeAdded) {
            sampleScore.addStudentScore(testScore);
        }
        return sampleScore;
    }
}
