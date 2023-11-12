package seedu.modulight.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.modulight.commons.exceptions.IllegalValueException;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.GradedComponent;
import seedu.modulight.model.gradedcomponent.MaxMarks;
import seedu.modulight.model.gradedcomponent.Weightage;



/**
 * Jackson-friendly version of {@link GradedComponent}.
 */
public class JsonAdaptedGradedComponent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "GradedComponent's %s field is missing!";
    private final String gcName;
    private final String gcMaxMarks;
    private final String gcWeightage;

    /**
     * Constructs a {@code JsonAdaptedGradedComponent} with the given component details.
     */
    @JsonCreator
    public JsonAdaptedGradedComponent(@JsonProperty("gcName") String gcName,
        @JsonProperty("gcMaxMarks") String gcMaxMarks,
        @JsonProperty("gcWeightage") String gcWeightage) {
        this.gcName = gcName;
        this.gcMaxMarks = gcMaxMarks;
        this.gcWeightage = gcWeightage;
    }

    /**
     * Converts a given {@code GradedComponent} into this class for Jackson use.
     */
    public JsonAdaptedGradedComponent(GradedComponent source) {
        gcName = source.getName().gcName;
        gcMaxMarks = String.valueOf(source.getMaxMarks().maxMarks);
        gcWeightage = String.valueOf(source.getWeightage().weightage);
    }

    /**
     * Converts this Jackson-friendly adapted gradedComponent object into the model's {@code GradedComponent} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted GradedComponent.
     */
    public GradedComponent toModelType() throws IllegalValueException {
        if (gcName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                GcName.class.getSimpleName()));
        }
        if (!GcName.isValidName(gcName)) {
            throw new IllegalValueException(GcName.MESSAGE_CONSTRAINTS);
        }
        final GcName modelGcName = new GcName(gcName);

        if (gcMaxMarks == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                MaxMarks.class.getSimpleName()));
        }
        if (!MaxMarks.isValidMarks(Float.parseFloat(gcMaxMarks))) {
            throw new IllegalValueException(MaxMarks.MESSAGE_CONSTRAINTS);
        }
        final MaxMarks modelMaxMarks = new MaxMarks(Float.parseFloat(gcMaxMarks));

        if (gcWeightage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Weightage.class.getSimpleName()));
        }
        if (!Weightage.isValidWeightage(Float.parseFloat(gcWeightage))) {
            throw new IllegalValueException(Weightage.MESSAGE_CONSTRAINTS);
        }
        final Weightage modelWeightage = new Weightage(Float.parseFloat(gcWeightage));

        return new GradedComponent(modelGcName, modelMaxMarks, modelWeightage);

    }

}
