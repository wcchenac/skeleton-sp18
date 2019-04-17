public class Palindrome {
	public Deque<Character> wordToDeque(String word) {
		Deque<Character> result = new ArrayDeque<>();
		for (int i = 0; i < word.length(); i++) {
			result.addLast(word.charAt(i));
		}
		return result;
	}

	/** Return true if the given word is a palindrome, e.g. "racecar" and "noon".
	 * Any word of length is 0 or 1 is a palindrome. */
	public boolean isPalindrome(String word) {
		Deque d1 = wordToDeque(word);
		return isPalindromeHelper(d1);
	}

	private boolean isPalindromeHelper(Deque d) {
		if (d.size() <= 1) {
			return true;
		} else if (d.removeFirst() != d.removeLast()) {
			return false;
		} else {
			return isPalindromeHelper(d);
		}
	}

	/** Return true if the word is a palindrome  according to the character comparison test
	 *  provided by the CharacterComparator passed in as argument cc. */
	public boolean isPalindrome(String word, CharacterComparator cc) {
		Deque d1 = wordToDeque(word);
		return isPalindromeHelper(d1, cc);
	}

	private boolean isPalindromeHelper(Deque d, CharacterComparator cc) {
		if (d.size() <= 1) {
			return true;
		} else if (!(cc.equalChars((char)d.removeFirst(), (char)d.removeLast()))) {
			return false;
		} else {
			return isPalindromeHelper(d, cc);
		}
	}
}
