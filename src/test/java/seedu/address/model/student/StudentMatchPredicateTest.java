package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_EMAIL_KEYWORDS;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_ID_KEYWORDS;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_TAG_KEYWORDS;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_TG_KEYWORDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TG_AMY;
import static seedu.address.testutil.TypicalStudents.AMY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;

class StudentMatchPredicateTest {

    @Test
    void test1() {
    }

    @Test
    void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        StudentMatchPredicate firstPredicate = new StudentMatchPredicate(firstPredicateKeywordList,
                firstPredicateKeywordList, firstPredicateKeywordList, firstPredicateKeywordList,
                firstPredicateKeywordList);
        StudentMatchPredicate secondPredicate = new StudentMatchPredicate(secondPredicateKeywordList,
                secondPredicateKeywordList, secondPredicateKeywordList, secondPredicateKeywordList,
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StudentMatchPredicate firstPredicateCopy = new StudentMatchPredicate(firstPredicateKeywordList,
                firstPredicateKeywordList, firstPredicateKeywordList, firstPredicateKeywordList,
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {

        // One keyword
        StudentMatchPredicate predicate = new StudentMatchPredicate(EMPTY_ID_KEYWORDS,
                Collections.singletonList("Alice"), EMPTY_EMAIL_KEYWORDS, EMPTY_TG_KEYWORDS, EMPTY_TAG_KEYWORDS);
        assertTrue(predicate.test(new StudentBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new StudentMatchPredicate(EMPTY_ID_KEYWORDS, Arrays.asList("Alice", "Bob"),
                EMPTY_EMAIL_KEYWORDS, EMPTY_TG_KEYWORDS, EMPTY_TAG_KEYWORDS);
        assertTrue(predicate.test(new StudentBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new StudentMatchPredicate(EMPTY_ID_KEYWORDS, Arrays.asList("Bob", "Carol"),
                EMPTY_EMAIL_KEYWORDS, EMPTY_TG_KEYWORDS, EMPTY_TAG_KEYWORDS);
        assertTrue(predicate.test(new StudentBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new StudentMatchPredicate(EMPTY_ID_KEYWORDS, Arrays.asList("aLIce", "bOB"),
                EMPTY_EMAIL_KEYWORDS, EMPTY_TG_KEYWORDS, EMPTY_TAG_KEYWORDS);
        assertTrue(predicate.test(new StudentBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        StudentMatchPredicate predicate = new StudentMatchPredicate(EMPTY_ID_KEYWORDS, Collections.emptyList(),
                EMPTY_EMAIL_KEYWORDS, EMPTY_TG_KEYWORDS, EMPTY_TAG_KEYWORDS);
        assertTrue(predicate.test(new StudentBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new StudentMatchPredicate(EMPTY_ID_KEYWORDS, Arrays.asList("Carol"),
                EMPTY_EMAIL_KEYWORDS, EMPTY_TG_KEYWORDS, EMPTY_TAG_KEYWORDS);
        assertFalse(predicate.test(new StudentBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new StudentMatchPredicate(EMPTY_ID_KEYWORDS, Arrays.asList(VALID_EMAIL_AMY, VALID_SID_AMY,
                VALID_TG_AMY, VALID_TAG_TA),
                EMPTY_EMAIL_KEYWORDS, EMPTY_TG_KEYWORDS, EMPTY_TAG_KEYWORDS);
        assertFalse(predicate.test(AMY));
    }
}
