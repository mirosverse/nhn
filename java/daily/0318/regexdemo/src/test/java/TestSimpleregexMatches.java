import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class TestSimpleregexMatches {

    public static int runTest(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;

        while (matcher.find()) {
            matches++;
        }
        return matches;
    }

    @Test
    void givenText_whenSimpleRegexMatches_thenCorrect() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo");
        int matches = 0;
        assertTrue(matcher.find());
    }

    @Test
    void givenText_whenSimpleRegexMatchesTwice_thenCorrect() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foofoo");
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }

        assertEquals(matches, 2);
    }

    @Test
    void givenText_whenSimpleRegexMatchesWithDot_thenCorrect() {
        int matches = runTest("foo.", "foofoo");
        assertEquals(matches, 1);

    }

    @Test
    void givenORSet_whenMatchesAny_thenCorrect() {
        int matches = runTest("[abc]", "ba");
        assertEquals(matches, 2);
    }

    @Test
    void givenORSet_whenMatchesAllCombinations_thenCorrect() {
        int matches = runTest("[bcr]at", "bat cat rat");
        assertEquals(matches, 3);
    }

    @Test
    void givenNORSet_whenMatchesNon_thenCorrect() {
        int matches = runTest("[^abc]", "ag");
        assertTrue(matches > 0);
    }

    @Test
    void givenUpperCaseRange_whenmatchesUpperCase_thenCorrect() {
        int matches = runTest("[A-Z]", "Two Uppercase alphabets 34 overall");
        assertEquals(2, matches);
    }

    @Test
    void givenLowerCaseRange_whenMatchesLowerCase_thenCorrect() {
        int matches = runTest("[a-z]", "Two Uppercase alphabets 34 overall");
        assertEquals(26, matches);
    }

    @Test
    void givenBothLowerAndUpperCaseRange_whenMatchesAllLetters_thenCorrect() {
        int matches = runTest("[A-Za-z]", "Two Uppercase alphabets 34 overall");
        assertEquals(28, matches);
    }

    @Test
    public void givenTwoSets_whenMatchesUnion_thenCorrect() {
        int matches = runTest("[1-6[7-9]]", "123456789");
        assertEquals(matches, 9);
    }

    @Test
    public void givenCapturingGroup_whenMatches_thenCorrect() {
        int matches = runTest("(\\d\\d)", "12");

        assertEquals(matches, 1);
    }

    @Test
    void testName() {
        int matches = runTest("\\b[+-]?(0|[1-9][0-9]{0,9})\\b", "12");
        assertTrue(matches > 0);
    }


    

}
