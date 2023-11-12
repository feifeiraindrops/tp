package seedu.modulight.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.modulight.commons.core.index.Index;
import seedu.modulight.commons.util.StringUtil;
import seedu.modulight.logic.parser.exceptions.ParseException;
import seedu.modulight.model.gradedcomponent.GcName;
import seedu.modulight.model.gradedcomponent.MaxMarks;
import seedu.modulight.model.gradedcomponent.Weightage;
import seedu.modulight.model.student.StudentEmail;
import seedu.modulight.model.student.StudentGrade;
import seedu.modulight.model.student.StudentId;
import seedu.modulight.model.student.StudentName;
import seedu.modulight.model.student.TutorialGroup;
import seedu.modulight.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static StudentId parseId(String sid) throws ParseException {
        requireNonNull(sid);
        String trimmedId = sid.trim();
        if (!StudentName.isValidName(trimmedId)) {
            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }
        return new StudentId(trimmedId);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static StudentName parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!StudentName.isValidName(trimmedName)) {
            throw new ParseException(StudentName.MESSAGE_CONSTRAINTS);
        }
        return new StudentName(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code GcName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static GcName parseGcName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!StudentName.isValidName(trimmedName)) {
            throw new ParseException(StudentName.MESSAGE_CONSTRAINTS);
        }
        return new GcName(trimmedName);
    }

    /**
     * Parses a {@code String StudentEmail} into an {@code StudentEmail}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code StudentEmail} is invalid.
     */
    public static StudentEmail parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!StudentEmail.isValidEmail(trimmedEmail)) {
            throw new ParseException(StudentEmail.MESSAGE_CONSTRAINTS);
        }
        return new StudentEmail(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String tutorial group} into a {@code TutorialGroup}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static TutorialGroup parseTg(String tg) throws ParseException {
        requireNonNull(tg);
        String trimmedTg = tg.trim();
        if (!Tag.isValidTagName(trimmedTg)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new TutorialGroup(trimmedTg);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String student grade} into a {@code StudentGrade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentGrade} is invalid.
     */
    public static StudentGrade parseStudentGrade(String studentGrade) throws ParseException {
        requireNonNull(studentGrade);
        String trimmedStudentGrade = studentGrade.trim();
        if (!StudentGrade.isValidGrade(trimmedStudentGrade)) {
            throw new ParseException(StudentGrade.MESSAGE_CONSTRAINTS);
        }
        return new StudentGrade(studentGrade);
    }
    /**
     * Parses {@code String s} into a {@code Weightage}, if possible.
     * If not, throws a parse error.
     */
    public static Weightage parseWeightage(String s) throws ParseException {
        checkStringParsableToFloat(s);
        return new Weightage(Float.parseFloat(s));
    }

    /**
     * Parses {@code String s} into a {@code MaxMarks}, if possible.
     * If not, throws an error with {@code varName}
     */
    public static MaxMarks parseMaxMarks(String s) throws ParseException {
        checkStringParsableToFloat(s);
        return new MaxMarks(Float.parseFloat(s));
    }

    /**
     * Parse Score in string to Float
     * @param s the string
     * @return float
     * @throws ParseException if not Parsable
     */
    public static float parseScore(String s) throws ParseException {
        checkStringParsableToFloat(s);
        return Float.parseFloat(s);
    }

    private static void checkStringParsableToDouble(String s) throws ParseException {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new ParseException("A value could not be parsed into a number.");
        }
    }

    private static void checkStringParsableToFloat(String s) throws ParseException {
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException e) {
            throw new ParseException("A value could not be parsed into a number.");
        }
    }

}
