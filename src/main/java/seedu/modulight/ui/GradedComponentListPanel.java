package seedu.modulight.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.modulight.commons.core.LogsCenter;
import seedu.modulight.model.gradedcomponent.GradedComponent;




/**
 * Panel containing the list of students.
 */
public class GradedComponentListPanel extends UiPart<Region> {
    private static final String FXML = "GradedComponentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GradedComponentListPanel.class);

    @FXML
    private ListView<GradedComponent> gradedComponentListView;

    /**
     * Creates a {@code GradedComponentListPanel} with the given {@code ObservableList}.
     */
    public GradedComponentListPanel(ObservableList<GradedComponent> gradedComponentList) {
        super(FXML);
        gradedComponentListView.setItems(gradedComponentList);
        gradedComponentListView.setCellFactory(listView -> new GradedComponentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code GradedComponent}
     * using a {@code GradedComponentCard}.
     */
    class GradedComponentListViewCell extends ListCell<GradedComponent> {
        @Override
        protected void updateItem(GradedComponent gradedComponent, boolean empty) {
            super.updateItem(gradedComponent, empty);
            if (empty || gradedComponent == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GradedComponentCard(gradedComponent, getIndex() + 1).getRoot());
            }


        }
    }

}
