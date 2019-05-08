import java.util.LinkedList;

public class HuffmanDecoder {
	/** Open the file given as the 0th command line argument (args[0]), decode it,
	 *  and and write a new file with the name given as args[1]. */
	public static void main(String[] args) {
		String inputFileName = args[0];
		String outputFileName = args[1];

		ObjectReader or = new ObjectReader(inputFileName);

		BinaryTrie trie = (BinaryTrie) or.readObject();
		BitSequence bs = (BitSequence) or.readObject();

		LinkedList<Character> charList = new LinkedList<>();

		while (bs.length() != 0) {
			Match match = trie.longestPrefixMatch(bs);
			charList.addLast(match.getSymbol());
			bs = bs.allButFirstNBits(match.getSequence().length());
		}

		char[] charArray = new char[charList.size()];

		for (int i = 0; i < charArray.length; i += 1) {
			charArray[i] = charList.removeFirst();
		}

		FileUtils.writeCharArray(outputFileName, charArray);
	}
}
