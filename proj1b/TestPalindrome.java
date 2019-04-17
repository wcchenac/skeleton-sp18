import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("dog"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        OffByOne offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", offByOne));
    }

    @Test
    public void testIsPalindromeOffByN() {
        OffByN offBy4 = new OffByN(4);
        assertTrue(palindrome.isPalindrome("abcfe", offBy4));
    }
}
