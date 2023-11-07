package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.ModelUtil.MESSAGE_INCORRECT_ENTItY_COUNT;
import static seedu.address.commons.util.ModelUtil.MESSAGE_WEIGHTAGES_MORE_THAN_100;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ModelUtil;
import seedu.address.model.gradedcomponent.GcName;
import seedu.address.model.gradedcomponent.GradedComponent;
import seedu.address.model.gradedcomponent.model.GradedComponentBook;
import seedu.address.model.gradedcomponent.model.ReadOnlyGradedComponentBook;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.model.ReadOnlyStudentBook;
import seedu.address.model.student.model.StudentBook;
import seedu.address.model.studentscore.StudentScore;
import seedu.address.model.studentscore.model.ReadOnlyStudentScoreBook;
import seedu.address.model.studentscore.model.StudentScoreBook;
/**
 * Represents the in-memory model of the application data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StudentBook studentBook;
    private final StudentScoreBook studentScoreBook;
    private final GradedComponentBook gradedComponentBook;
    private final UserPrefs userPrefs;

    private final FilteredList<Student> filteredStudentList;
    private final FilteredList<GradedComponent> filteredGradedComponentList;
    private final FilteredList<StudentScore> filteredStudentScoreList;


    /**
     * Initializes a UnifiedBookManager with the given studentBook, gradedComponentBook,
     * studentScoreBook and userPrefs.
     */
    public ModelManager(ReadOnlyStudentBook studentBook, ReadOnlyStudentScoreBook studentScoreBook,
                               ReadOnlyGradedComponentBook gradedComponentBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(studentBook, studentScoreBook, gradedComponentBook, userPrefs);

        logger.fine("Initializing with students: " + studentBook
                + "student scores: " + studentScoreBook
                + "graded components: " + gradedComponentBook
                + "and user prefs " + userPrefs);

        this.studentBook = new StudentBook(studentBook);
        this.gradedComponentBook = new GradedComponentBook(gradedComponentBook);
        this.studentScoreBook = new StudentScoreBook(studentScoreBook);
        checkBookValidity(this.studentBook, this.gradedComponentBook, this.studentScoreBook);
        assignStudentScores(this.studentBook, this.gradedComponentBook, this.studentScoreBook);
        calculateInitialScores(this.studentBook, this.gradedComponentBook, this.studentScoreBook);

        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredStudentList = new FilteredList<>(this.studentBook.getStudentList());
        this.filteredGradedComponentList = new FilteredList<>(this.gradedComponentBook.getGradedComponentList());
        this.filteredStudentScoreList = new FilteredList<>(this.studentScoreBook.getStudentScoreList());
    }

    /**
     * Initializes a UnifiedBookManager with an empty studentBook, gradedComponentBook,
     * studentScoreBook and userPrefs.
     */
    public ModelManager() {
        this(new StudentBook(), new StudentScoreBook(),
                new GradedComponentBook(), new UserPrefs());
    }

    private void assignStudentScores(StudentBook studentBook, GradedComponentBook gcBook,
                                     StudentScoreBook ssb) {
        HashMap<StudentId, Student> studentHash = new HashMap<>();
        HashMap<GcName, GradedComponent> gcHash = new HashMap<>();
        List<Student> studentList = studentBook.getStudentList();
        List<GradedComponent> gcList = gcBook.getGradedComponentList();
        List<StudentScore> studentScoreList = ssb.getStudentScoreList();
        for (Student s : studentList) {
            studentHash.put(s.getStudentId(), s);
        }
        for (GradedComponent g : gcList) {
            gcHash.put(g.getName(), g);
        }
        for (StudentScore sc : studentScoreList) {
            Student student = studentHash.get(sc.getStudentId());
            GradedComponent gc = gcHash.get(sc.getGcName());
            sc.setStudent(student);
            sc.setGradedComponent(gc);
            student.addScore(sc);
            gc.addScore(sc);
        }
    }

    private void calculateInitialScores(StudentBook studentBook, GradedComponentBook gcBook,
                                   StudentScoreBook ssb) {
        List<Student> studentList = studentBook.getStudentList();
        List<GradedComponent> gcList = gcBook.getGradedComponentList();
        for (Student s : studentList) {
            s.recalculateScores();
        }
        for (GradedComponent g : gcList) {
            g.recalculateScores();
        }
    }

    private void checkBookValidity(StudentBook studentBook, GradedComponentBook gcBook,
                                   StudentScoreBook ssb) {
        List<Student> studentList = studentBook.getStudentList();
        List<GradedComponent> gcList = gcBook.getGradedComponentList();
        List<StudentScore> studentScoreList = ssb.getStudentScoreList();
        if (ModelUtil.weightageSum(gcBook) > 100) {
            throw new RuntimeException(MESSAGE_WEIGHTAGES_MORE_THAN_100);
        }
        if (studentList.size() * gcList.size() != studentScoreList.size()) {
            throw new RuntimeException(MESSAGE_INCORRECT_ENTItY_COUNT);
        }

    }
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getApplicationFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setApplicationFilePath(Path applicationFilePath) {
        requireNonNull(applicationFilePath);
        userPrefs.setAddressBookFilePath(applicationFilePath);
    }

    @Override
    public StudentBook getStudentBook() {
        return studentBook;
    }

    @Override
    public StudentScoreBook getStudentScoreBook() {
        return studentScoreBook;
    }

    @Override
    public GradedComponentBook getGradedComponentBook() {
        return gradedComponentBook;
    }

    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudentList;
    }

    public ObservableList<GradedComponent> getFilteredGradedComponentList() {
        return filteredGradedComponentList;
    }

    public ObservableList<StudentScore> getFilteredStudentScoreList() {
        return filteredStudentScoreList;
    }

    @Override
    public Predicate<Student> getCurrentStudentsPredicate() {
        return (Predicate<Student>) filteredStudentList.getPredicate();
    }

    @Override
    public Predicate<StudentScore> getCurrentScoresPredicate() {
        return (Predicate<StudentScore>) filteredStudentScoreList.getPredicate();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return studentBook.equals(otherModelManager.studentBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && gradedComponentBook.equals(otherModelManager.gradedComponentBook)
                && studentScoreBook.equals(otherModelManager.studentScoreBook);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudentList.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStudentScoreList(Predicate<StudentScore> predicate) {
        requireNonNull(predicate);
        filteredStudentScoreList.setPredicate(predicate);
    }

    @Override
    public void updateFilteredGradedComponentList(Predicate<GradedComponent> predicate) {
        requireNonNull(predicate);
        filteredGradedComponentList.setPredicate(predicate);
    }

}
