package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.gradedcomponent.GradedComponent;

/**
 * An UI component that displays information of a {@code GradedComponent}.
 */
public class GradedComponentCard extends UiPart<Region> {

    private static final String FXML = "GradedComponentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final GradedComponent gradedComponent;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label gcName;
    @FXML
    private Label maxMarks;
    @FXML
    private Label weightage;


    /**
     * Creates a {@code GradedComponentCard} with the given {@code GradedComponent} and index to display.
     */
    public GradedComponentCard(GradedComponent gradedComponent, int displayedIndex) {
        super(FXML);
        this.gradedComponent = gradedComponent;
        id.setText(displayedIndex + ". ");
        gcName.setText(gradedComponent.getName().gcName);
        maxMarks.setText("Max marks: " + gradedComponent.getMaxMarks());
        weightage.setText("Weightage: " + gradedComponent.getWeightage());
    }
}
