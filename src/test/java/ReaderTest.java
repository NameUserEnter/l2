import org.testng.annotations.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import static org.testng.Assert.*;

public class ReaderTest {

    private static HashSet<String> wordsInFile;

    @BeforeClass(groups={"group1", "group2"})
    public static void setUpClass() {
        wordsInFile = new HashSet<>(Arrays.asList("hello", "world", "goodbye", "cruel"));
    }

    @BeforeMethod()
    public void setUp() {
        Reader.meetRequirement(wordsInFile);
    }

    @Test(groups = "group1")
    public void testPut() {
        HashMap<String, Integer> wordsWithNumber = new HashMap<>();
        Reader.put(wordsWithNumber, wordsInFile);
        assertNotNull(wordsWithNumber);
        assertEquals(wordsWithNumber.size(), 4);
        assertTrue(wordsWithNumber.containsKey("hello"));
        assertTrue(wordsWithNumber.containsKey("world"));
        assertTrue(wordsWithNumber.containsKey("goodbye"));
        assertTrue(wordsWithNumber.containsKey("cruel"));
        assertTrue(Arrays.asList(3, 4, 3, 3).containsAll(wordsWithNumber.values()));
    }

    @Test(groups = "group1")
    public void testPrinter() {
        HashMap<String, Integer> wordsWithNumber = new HashMap<>();
        Reader.put(wordsWithNumber, wordsInFile);
        try {
            Reader.printer(wordsWithNumber);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test(groups = "group1")
    public void testNumberOfLetters() {
        assertEquals(Reader.numberOfLetters("hello"), 3);
        assertEquals(Reader.numberOfLetters("world"), 4);
        assertEquals(Reader.numberOfLetters("goodbye"), 3);
        assertEquals(Reader.numberOfLetters("cruel"), 3);
    }

    @Test(groups = "group1")
    public void testMeetRequirement() {
        assertFalse(wordsInFile.contains("supercalifragilisticexpialidociousrrr"));
        assertFalse(wordsInFile.contains("supercalifragilisticexpialirrr"));
    }

    @Test(groups = "group1")
    public void testMeetRequirementLongWord() {
        wordsInFile.add("supercalifragilisticexpialidocious");
        Reader.meetRequirement(wordsInFile);
        assertTrue(wordsInFile.contains("supercalifragilisticexpialidocious"));
        assertFalse(wordsInFile.contains("supercalifragilisticexpiali"));
        wordsInFile.remove("supercalifragilisticexpialidocious");
    }

    @Test(groups = "group2")
    public void testMeetRequirement2() {
        HashSet<String> words = new HashSet<>(Arrays.asList("this", "is", "a", "test", "string", "with", "longwordslikethisone"));
        Reader.meetRequirement(words);
        assertTrue(words.contains("longwordslikethisone"));
        assertTrue(words.containsAll(Arrays.asList("this", "is", "a", "test", "string")));
        assertFalse(words.contains("longwordslikethisoneinvalid"));
    }

    @Test(groups = "group2")
    public void testMeetRequirementShortWord() {
        wordsInFile.add("hi");
        Reader.meetRequirement(wordsInFile);
        assertTrue(wordsInFile.contains("hi"));
        wordsInFile.remove("hi");
    }

    @Test(expectedExceptions = NullPointerException.class, groups = "group2")
    public void testException() {
        Reader.meetRequirement(null);
    }

    @DataProvider(name = "words")
    public Object[][] getWords() {
        return new Object[][]{{"hello"}, {"world"}, {"goodbye"}, {"cruel"}};
    }
    @Test(dataProvider = "words", groups = "group2")
    public void testPutParameterized(String word) {
        HashMap<String, Integer> wordsWithNumber = new HashMap<>();
        Reader.put(wordsWithNumber, wordsInFile);
        assertEquals(wordsWithNumber.get(word).intValue(), Reader.numberOfLetters(word));
    }

}