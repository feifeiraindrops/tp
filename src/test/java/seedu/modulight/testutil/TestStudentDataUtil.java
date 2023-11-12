package seedu.modulight.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.StudentEmail;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.model.student.model.ReadOnlyStudentBook;
import seedu.modulight.model.student.model.StudentBook;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.tag.Tag;

/**
 * Contains utility methods for populating StudentBook with sample data.
 */
public class TestStudentDataUtil {
    private static GradedComponent gc1 = TestGcDataUtil.getTestGradedComponents()[0];
    private static GradedComponent gc2 = TestGcDataUtil.getTestGradedComponents()[1];

    private static List<StudentScore> scoreList = new ArrayList<>();

    private static Set<Tag> tags = new HashSet<>();

    public static ArrayList<Student> getTestStudents() {
        Student[] students = new Student[]{
            new Student(new StudentId("A0000000Y"), new StudentName("Lionel Messi"),
                new StudentEmail("alexyeoh@example.com"),
                new TutorialGroup("T03"), scoreList, tags),
            new Student(new StudentId("A0000001Y"), new StudentName("Ah Beng"), new StudentEmail("blexyeoh@example"
                + ".com"),
                new TutorialGroup("T03"), scoreList, tags),
            new Student(new StudentId("A0000002Y"), new StudentName("Paul McCartney"),
                new StudentEmail("clexyeoh@example.com"),
                new TutorialGroup("T03"), scoreList, tags),
            new Student(new StudentId("A0000003Y"), new StudentName("Li Ming"), new StudentEmail("dlexyeoh@example"
                + ".com"),
                new TutorialGroup("T03"), scoreList, tags),
            new Student(new StudentId("A0000004Y"), new StudentName("Catherine"),
                new StudentEmail("elexyeoh@example.com"),
                new TutorialGroup("T03"), scoreList, tags),
            new Student(new StudentId("A0000005Y"), new StudentName("Goodman"), new StudentEmail("flexyeoh@example"
                + ".com"),
                new TutorialGroup("G03"), scoreList, tags)
        };
        return new ArrayList<>(List.of(students));
    }

    public static ArrayList<Student> getTestStudentsAfterAdding() {
        ArrayList<Student> newStudents = getTestStudents();
        newStudents.add(new Student(new StudentId("A1234567U"), new StudentName("Newbie"),
                new StudentEmail("newbie@gmail.com"),
                new TutorialGroup("T05"), scoreList, tags));
        return newStudents;
    }

    public static ArrayList<Student> getTestStudentsAfterDeleting() {
        ArrayList<Student> newStudents = getTestStudents();
        newStudents.remove(1);
        return newStudents;
    }

    public static ArrayList<Student> getTestStudentsWithScore(ArrayList<StudentScore> scores) {
        ArrayList<Student> oldStudents = getTestStudents();
        ArrayList<Student> newStudents = new ArrayList<>(oldStudents);
        if (!getTestStudents().isEmpty()) {
            Collections.copy(newStudents, oldStudents);
        }
        for (Student stu : newStudents) {
            List<StudentScore> matchingScores = scores.stream()
                    .filter(score -> score.getStudentId().equals(stu.getStudentId())).collect(Collectors.toList());
            for (StudentScore score : matchingScores) {
                stu.addScore(score);
            }
        }
        return newStudents;
    }

    public static ReadOnlyStudentBook getTestStudentBook(String selectedStu) {
        StudentBook sampleStu = new StudentBook();
        ArrayList<Student> stuToBeAdded;
        switch (selectedStu) {
        case "add":
            stuToBeAdded = getTestStudentsAfterAdding();
            break;
        case "delete":
            stuToBeAdded = getTestStudentsAfterDeleting();
            break;
        case "create":
            stuToBeAdded = getTestStudents();
            break;
        case "score":
            stuToBeAdded = getTestStudentsWithScore(TestStudentScoreDataUtil.getTestStudentScores());
            break;
        case "twoScores":
            stuToBeAdded = getTestStudentsWithScore(TestStudentScoreDataUtil.getTestStudentTwoScores());
            break;
        case "zeroScore":
            stuToBeAdded = getTestStudentsWithScore(TestStudentScoreDataUtil.getTestStudentZeroScores());
            break;
        case "sortByName":
            stuToBeAdded = getTestStudents();
            stuToBeAdded.sort(new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.getName().fullName.compareTo(o2.getName().fullName);
                }
            });
            break;
        case "sortByTsReverse":
            stuToBeAdded = getTestStudents();
            Collections.reverse(stuToBeAdded);
            break;
        default:
            stuToBeAdded = new ArrayList<>();
        }
        for (Student testStu: stuToBeAdded) {
            sampleStu.addStudent(testStu);
        }
        return sampleStu;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
