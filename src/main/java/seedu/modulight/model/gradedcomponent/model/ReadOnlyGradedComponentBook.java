package seedu.modulight.model.gradedcomponent.model;

import javafx.collections.ObservableList;
import seedu.modulight.model.gradedcomponent.GradedComponent;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyGradedComponentBook {

    /**
     * Returns an unmodifiable view of the gradedComponents list.
     * This list will not contain any duplicate gradedComponents.
     */
    ObservableList<GradedComponent> getGradedComponentList();

}
