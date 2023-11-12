package seedu.modulight.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.modulight.commons.core.LogsCenter;
import seedu.modulight.model.studentscore.StudentScore;



/**
 * Panel containing the list of students.
 */
public class StudentScoreListPanel extends UiPart<Region> {
    private static final String FXML = "StudentScoreListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentScoreListPanel.class);

    @FXML
    private ListView<StudentScore> studentScoreListView;

    /**
     * Creates a {@code StudentScoreListPanel} with the given {@code ObservableList}.
     */
    public StudentScoreListPanel(ObservableList<StudentScore> studentScoreList) {
        super(FXML);
        studentScoreListView.setItems(studentScoreList);
        studentScoreListView.setCellFactory(listView -> new StudentScoreListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StudentScore} using a {@code StudentScoreCard}.
     */
    class StudentScoreListViewCell extends ListCell<StudentScore> {
        @Override
        protected void updateItem(StudentScore studentScore, boolean isEmpty) {
            super.updateItem(studentScore, isEmpty);

            if (isEmpty || studentScore == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentScoreCard(studentScore, getIndex() + 1).getRoot());
            }
        }
    }

}
