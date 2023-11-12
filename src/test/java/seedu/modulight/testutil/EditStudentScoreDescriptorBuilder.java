package seedu.modulight.testutil;

import seedu.modulight.logic.commands.EditStudentScoreCommand.EditStudentScoreDescriptor;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.studentscore.StudentScore;

/**
 * A utility class to help with Building EditStudentScoreDescriptor objects.
 */
public class EditStudentScoreDescriptorBuilder {
    private EditStudentScoreDescriptor descriptor;

    /**
     * Default Constructor to create the class.
     */
    public EditStudentScoreDescriptorBuilder() {
        descriptor = new EditStudentScoreDescriptor();
    }

    /**
     * Constructor to create the Builder with existing EditStudentScoreDescriptor.
     * @param descriptor EditStudentScoreDescriptor
     */
    public EditStudentScoreDescriptorBuilder(EditStudentScoreDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Constructor to create the Builder with existing studentScore
     * @param studentScore StudentScore
     */
    public EditStudentScoreDescriptorBuilder(StudentScore studentScore) {
        descriptor = new EditStudentScoreDescriptor();
        descriptor.setStudentId(studentScore.getStudentId());
        descriptor.setGcName(studentScore.getGcName());
        descriptor.setScore(studentScore.getScore());
        descriptor.setComment(studentScore.getComment());
        descriptor.setTags(studentScore.getTags());
    }

    /**
     * Return EditStudentScoreDescriptorBuilder with newly set studentId
     * @param studentId studentId
     * @return EditStudentScoreDescriptorBuilder
     */
    public EditStudentScoreDescriptorBuilder withStudentId(String studentId) {
        descriptor.setStudentId(new StudentId(studentId));
        return this;
    }

    /**
     * Return EditStudentScoreDescriptorBuilder with newly set gcName
     * @param gcName gcName
     * @return EditStudentScoreDescriptorBuilder
     */
    public EditStudentScoreDescriptorBuilder withGcName(String gcName) {
        descriptor.setGcName(new GcName(gcName));
        return this;
    }

    /**
     * Return EditStudentScoreDescriptorBuilder with newly set score
     * @param score score
     * @return EditStudentScoreDescriptorBuilder
     */
    public EditStudentScoreDescriptorBuilder withScore(float score) {
        descriptor.setScore(score);
        return this;
    }

    /**
     * Return EditStudentScoreDescriptorBuilder with newly set comment.
     * @param comment comment
     * @return EditStudentScoreDescriptorBuilder
     */
    public EditStudentScoreDescriptorBuilder withComment(String comment) {
        descriptor.setComment(comment);
        return this;
    }

    /**
     * Return EditStudentScoreDescriptorBuilder with newly set tags.
     * @param tags tags
     * @return EditStudentScoreDescriptorBuilder
     */
    public EditStudentScoreDescriptorBuilder withTags(String ... tags) {
        descriptor.setTags(TestStudentDataUtil.getTagSet(tags));
        return this;
    }

    public EditStudentScoreDescriptor build() {
        return descriptor;
    }
}
