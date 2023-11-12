package seedu.modulight.model.gradedcomponent.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.modulight.commons.util.ToStringBuilder;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.UniqueGradedComponentList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameGradedComponent comparison)
 */
public class GradedComponentBook implements ReadOnlyGradedComponentBook {

    private final UniqueGradedComponentList gradedComponents;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        gradedComponents = new UniqueGradedComponentList();
    }

    public GradedComponentBook() {}

    /**
     * Creates an GradedComponentBook using the GradedComponents in the {@code toBeCopied}
     */
    public GradedComponentBook(ReadOnlyGradedComponentBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the gradedComponent list with {@code gradedComponents}.
     * {@code gradedComponents} must not contain duplicate gradedComponents.
     */
    public void setGradedComponents(List<GradedComponent> gradedComponents) {
        this.gradedComponents.setGradedComponents(gradedComponents);
    }

    /**
     * Resets the existing data of this {@code GradedComponentBook} with {@code newData}.
     */
    public void resetData(ReadOnlyGradedComponentBook newData) {
        requireNonNull(newData);

        setGradedComponents(newData.getGradedComponentList());
    }

    //// gradedComponent-level operations

    /**
     * Returns true if a gradedComponent with the same identity as {@code gradedComponent} exists in the address book.
     */
    public boolean hasGradedComponent(GradedComponent gradedComponent) {
        requireNonNull(gradedComponent);
        return gradedComponents.contains(gradedComponent);
    }

    /**
     * Adds a gradedComponent to the address book.
     * The gradedComponent must not already exist in the address book.
     */
    public void addGradedComponent(GradedComponent p) {
        gradedComponents.add(p);
    }

    /**
     * Replaces the given gradedComponent {@code target} in the list with {@code editedGradedComponent}.
     * {@code target} must exist in the address book.
     * The gradedComponent identity of {@code editedGradedComponent} must not be the same as another existing
     * gradedComponent in the address book.
     */
    public void setGradedComponent(GradedComponent target, GradedComponent editedGradedComponent) {
        requireNonNull(editedGradedComponent);

        gradedComponents.setGradedComponent(target, editedGradedComponent);
    }

    /**
     * Removes {@code key} from this {@code GradedComponentBook}.
     * {@code key} must exist in the address book.
     */
    public void removeGradedComponent(GradedComponent key) {
        gradedComponents.remove(key);
    }

    /**
     * Clears the existing data of this {@code GradedComponentBook}.
     */
    public void clearGradedComponent() {
        gradedComponents.clear();
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("gradedComponents", gradedComponents)
                .toString();
    }

    @Override
    public ObservableList<GradedComponent> getGradedComponentList() {
        return gradedComponents.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradedComponentBook)) {
            return false;
        }

        GradedComponentBook otherGradedComponentBook = (GradedComponentBook) other;
        return gradedComponents.equals(otherGradedComponentBook.gradedComponents);
    }

    @Override
    public int hashCode() {
        return gradedComponents.hashCode();
    }

    public GradedComponent getGradedComponentByName(GcName gcName) {
        requireNonNull(gcName);
        for (GradedComponent gc : gradedComponents) {
            if (gc.getName().equals(gcName)) {
                return gc;
            }
        }
        return null;
    }
}
