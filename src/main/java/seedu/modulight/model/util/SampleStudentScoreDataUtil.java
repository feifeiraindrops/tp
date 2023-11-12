package seedu.modulight.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.studentscore.model.ReadOnlyStudentScoreBook;
import seedu.modulight.model.studentscore.model.StudentScoreBook;
import seedu.modulight.model.tag.Tag;


/**
 * Contains utility methods for populating StudentBook with sample data.
 */
public class SampleStudentScoreDataUtil {

    private static List<StudentScore> scoreList = new ArrayList<>();

    private static Set<Tag> tags = new HashSet<>();

    public static StudentScore[] getSampleStudentScores() {
        return new StudentScore[]{ };
    }

    public static ReadOnlyStudentScoreBook getSampleStudentScoreBook() {
        StudentScoreBook sampleAb = new StudentScoreBook();
        for (StudentScore sampleStudentScore : getSampleStudentScores()) {
            sampleAb.addStudentScore(sampleStudentScore);
        }
        return sampleAb;
    }
}
