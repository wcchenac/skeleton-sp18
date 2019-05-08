import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
	/** This method should map characters to their counts. */
	public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
		Map<Character, Integer> table = new HashMap<>();
		for (char c : inputSymbols) {
			if (!table.containsKey(c)) {
				table.put(c, 1);
			} else {
				table.put(c, table.get(c) + 1);
			}
		}
		return table;
	}

	/** Open the file given as the 0th command line argument (args[0]),
	 *  and write a new file with the name args[0] + ".huf"
	 *  that contains a huffman encoded version of the original file.
	 */
	public static void main(String[] args) {
		String fileName = args[0];

		char[] input = FileUtils.readFile(fileName);

		Map<Character, Integer> frequencyTable = buildFrequencyTable(input);

		ObjectWriter ow = new ObjectWriter(fileName + ".huf");

		BinaryTrie trie = new BinaryTrie(frequencyTable);
		ow.writeObject(trie);

		Map<Character, BitSequence> lookupTable = trie.buildLookupTable();
		List<BitSequence> encode = new ArrayList<>();
		for (char c : input) {
			encode.add(lookupTable.get(c));
		}

		BitSequence bs = BitSequence.assemble(encode);
		ow.writeObject(bs);
	}
}
