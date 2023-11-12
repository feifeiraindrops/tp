package seedu.modulight.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.modulight.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.modulight.model.student.Student;
import seedu.modulight.model.student.StudentEmail;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.model.studentscore.StudentScore;
import seedu.modulight.model.tag.Tag;

/**
 * The type Edit student descriptor builder.
 */
public class EditStudentDescriptorBuilder {
    private EditStudentDescriptor descriptor;

    /**
     * Instantiates a new Edit student descriptor builder.
     */
    public EditStudentDescriptorBuilder() {
        descriptor = new EditStudentDescriptor();
    }

    /**
     * Instantiates a new Edit student descriptor builder.
     *
     * @param descriptor the descriptor
     */
    public EditStudentDescriptorBuilder(EditStudentDescriptor descriptor) {
        this.descriptor = new EditStudentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStudentDescriptor} with fields containing {@code student}'s details
     *
     * @param student the student
     */
    public EditStudentDescriptorBuilder(Student student) {
        descriptor = new EditStudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setId(student.getStudentId());
        descriptor.setEmail(student.getEmail());
        descriptor.setTutorialGroup(student.getTutorial());
        descriptor.setScores(student.getScores());
        descriptor.setStudentGrade(student.getStudentGrade());
        descriptor.setTags(student.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditStudentDescriptor} that we are building.
     *
     * @param name the name
     * @return the edit student descriptor builder
     */
    public EditStudentDescriptorBuilder withName(String name) {
        descriptor.setName(new StudentName(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditStudentDescriptor} that we are building.
     *
     * @param sid the sid
     * @return the edit student descriptor builder
     */
    public EditStudentDescriptorBuilder withId(String sid) {
        descriptor.setId(new StudentId(sid));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditStudentDescriptor} that we are building.
     *
     * @param email the email
     * @return the edit student descriptor builder
     */
    public EditStudentDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new StudentEmail(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditStudentDescriptor} that we are building.
     *
     * @param tg the tg
     * @return the edit student descriptor builder
     */
    public EditStudentDescriptorBuilder withTg(String tg) {
        descriptor.setTutorialGroup(new TutorialGroup(tg));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditStudentDescriptor}
     * that we are building.
     *
     * @param tags the tags
     * @return the edit student descriptor builder
     */
    public EditStudentDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * With score edit student descriptor builder.
     *
     * @param scores the scores
     * @return the edit student descriptor builder
     */
    public EditStudentDescriptorBuilder withScore(StudentScore ... scores) {
        List<StudentScore> scoreList = new ArrayList<>();
        scoreList.addAll(List.of(scores));
        descriptor.setScores(scoreList);
        return this;
    }


    /**
     * Build edit student descriptor.
     *
     * @return the edit student descriptor
     */
    public EditStudentDescriptor build() {
        return descriptor;
    }
}
