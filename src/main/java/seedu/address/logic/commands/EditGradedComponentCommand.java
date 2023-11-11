package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.ModelUtil.MESSAGE_WEIGHTAGES_MORE_THAN_100;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPONENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_MARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHTAGE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ModelUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditStudentScoreCommand.EditStudentScoreDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradedcomponent.GcName;
import seedu.address.model.gradedcomponent.GradedComponent;
import seedu.address.model.gradedcomponent.MaxMarks;
import seedu.address.model.gradedcomponent.Weightage;
import seedu.address.model.gradedcomponent.model.GradedComponentBook;
import seedu.address.model.studentscore.StudentScore;
import seedu.address.model.studentscore.model.StudentScoreBook;


/**
 * Edits the details of an existing gradedComponent in the address book.
 */
public class EditGradedComponentCommand extends Command {

    public static final String COMMAND_WORD = "editComp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the gradedComponent identified "
            + "by the index number used in the displayed gradedComponent list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COMPONENT_NAME + "COMPONENT_NAME] "
            + "[" + PREFIX_MAX_MARKS + "MAX_MARKS] "
            + "[" + PREFIX_WEIGHTAGE + "WEIGHTAGE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COMPONENT_NAME + "Midterm Project "
            + PREFIX_WEIGHTAGE + "20";

    public static final String MESSAGE_EDIT_COMPONENT_SUCCESS = "Edited graded component: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GRADED_COMPONENT = "This graded component already "
            + "exists in the address book.";
    public static final String MESSAGE_ASSOCIATED_SCORE_EXCEEDS = "Graded component could not be edited."
            + " An associated student score has marks that exceeds the maximum marks provided.";


    private final Index index;
    private final EditGradedComponentDescriptor editGradedComponentDescriptor;

    /**
     * @param index of the gradedComponent in the filtered gradedComponent list to edit
     * @param editGradedComponentDescriptor details to edit the gradedComponent with
     */
    public EditGradedComponentCommand(Index index, EditGradedComponentDescriptor editGradedComponentDescriptor) {
        requireNonNull(index);
        requireNonNull(editGradedComponentDescriptor);

        this.index = index;
        this.editGradedComponentDescriptor = new EditGradedComponentDescriptor(editGradedComponentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<GradedComponent> lastShownList = model.getFilteredGradedComponentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        GradedComponent gradedComponentToEdit = lastShownList.get(index.getZeroBased());
        GradedComponent editedGradedComponent = createEditedGradedComponent(gradedComponentToEdit,
                editGradedComponentDescriptor);

        GradedComponentBook gradedComponentBook = model.getGradedComponentBook();
        checkEditedComponentValidity(gradedComponentToEdit, editedGradedComponent, gradedComponentBook);

        gradedComponentBook.setGradedComponent(gradedComponentToEdit, editedGradedComponent);
        StudentScoreBook studentScoreBook = model.getStudentScoreBook();
        List<StudentScore> studentScoreList = studentScoreBook.getStudentScoreList();

        for (int i = 0; i < studentScoreList.size(); i++) {
            StudentScore sc = studentScoreList.get(i);
            if (sc.getGcName().equals(gradedComponentToEdit.getName())) {
                sc.setGradedComponent(editedGradedComponent);
                EditStudentScoreDescriptor descriptor =
                        createStudentScoreDescriptor(sc, editedGradedComponent);
                EditStudentScoreCommand command = new EditStudentScoreCommand(
                        Index.fromZeroBased(i), descriptor, false);
                command.execute(model);
            }
        }

        return new CommandResult(String.format(MESSAGE_EDIT_COMPONENT_SUCCESS,
                Messages.formatGradedComponent(editedGradedComponent)));
    }

    private void checkEditedComponentValidity(GradedComponent gradedComponentToEdit,
                                       GradedComponent editedGradedComponent,
                                       GradedComponentBook gradedComponentBook) throws CommandException {
        if (!gradedComponentToEdit.isSameGc(editedGradedComponent)
                && gradedComponentBook.hasGradedComponent(editedGradedComponent)) {
            throw new CommandException(MESSAGE_DUPLICATE_GRADED_COMPONENT);
        }
        float weightageToEdit = gradedComponentToEdit.getWeightage().weightage;
        float weightageEdited = editedGradedComponent.getWeightage().weightage;
        if (ModelUtil.weightageSum(gradedComponentBook) - weightageToEdit + weightageEdited > 100) {
            throw new CommandException(MESSAGE_WEIGHTAGES_MORE_THAN_100);
        }

        for (StudentScore sc : gradedComponentToEdit.getScores()) {
            if (sc.getScore() > editedGradedComponent.getMaxMarks().maxMarks) {
                throw new CommandException(MESSAGE_ASSOCIATED_SCORE_EXCEEDS);
            }
        }
    }

    private EditStudentScoreDescriptor createStudentScoreDescriptor(StudentScore sc,
                                                                    GradedComponent editedGradedComponent) {
        EditStudentScoreCommand.EditStudentScoreDescriptor newDescriptor =
                new EditStudentScoreCommand.EditStudentScoreDescriptor();
        newDescriptor.setGcName(editedGradedComponent.getName());
        float maxMarks = editedGradedComponent.getMaxMarks().maxMarks;
        if (sc.getScore() > maxMarks) {
            newDescriptor.setScore(maxMarks);
        }
        return newDescriptor;
    }

    /**
     * Creates and returns a {@code GradedComponent} with the details of {@code gradedComponentToEdit}
     * edited with {@code editGradedComponentDescriptor}.
     */
    private static GradedComponent createEditedGradedComponent(GradedComponent gradedComponentToEdit,
                   EditGradedComponentDescriptor editGradedComponentDescriptor) {
        assert gradedComponentToEdit != null;

        GcName updatedName = editGradedComponentDescriptor.getGcName()
                .orElse(gradedComponentToEdit.getName());
        MaxMarks updatedMaxMarks = editGradedComponentDescriptor.getMaxMarks()
                .orElse(gradedComponentToEdit.getMaxMarks());
        Weightage updatedWeightage = editGradedComponentDescriptor.getWeightage()
                .orElse(gradedComponentToEdit.getWeightage());

        return new GradedComponent(updatedName, updatedMaxMarks, updatedWeightage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGradedComponentCommand)) {
            return false;
        }

        EditGradedComponentCommand otherEditCommand = (EditGradedComponentCommand) other;
        return index.equals(otherEditCommand.index)
                && editGradedComponentDescriptor.equals(otherEditCommand.editGradedComponentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editGradedComponentDescriptor", editGradedComponentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the gradedComponent with. Each non-empty field value will replace the
     * corresponding field value of the gradedComponent.
     */
    public static class EditGradedComponentDescriptor {
        private GcName name;
        private MaxMarks maxMarks;
        private Weightage weightage;

        public EditGradedComponentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGradedComponentDescriptor(EditGradedComponentDescriptor toCopy) {
            setGcName(toCopy.name);
            setMaxMarks(toCopy.maxMarks);
            setWeightage(toCopy.weightage);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, maxMarks, weightage);
        }

        public void setGcName(GcName name) {
            this.name = name;
        }

        public Optional<GcName> getGcName() {
            return Optional.ofNullable(name);
        }

        public void setMaxMarks(MaxMarks maxMarks) {
            this.maxMarks = maxMarks;
        }

        public Optional<MaxMarks> getMaxMarks() {
            return Optional.ofNullable(maxMarks);
        }

        public void setWeightage(Weightage weightage) {
            this.weightage = weightage;
        }

        public Optional<Weightage> getWeightage() {
            return Optional.ofNullable(weightage);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGradedComponentDescriptor)) {
                return false;
            }

            EditGradedComponentDescriptor otherEditGradedComponentDescriptor = (EditGradedComponentDescriptor) other;
            return Objects.equals(name, otherEditGradedComponentDescriptor.name)
                    && Objects.equals(weightage, otherEditGradedComponentDescriptor.weightage)
                    && Objects.equals(maxMarks, otherEditGradedComponentDescriptor.maxMarks);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("max marks", maxMarks)
                    .add("weightage", weightage)
                    .toString();
        }
    }
}
