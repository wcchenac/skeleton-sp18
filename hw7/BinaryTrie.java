
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {
	private static final long serialVersionUID = 2468L;
	private Node root;

	// Helper class for building a Trie
	private static class Node implements Comparable<Node>, Serializable {
		private static final long serialVersionUID = 13579L;
		private char ch;
		private int frequency;
		private Node left, right;

		private Node(char c, int f, Node l, Node r) {
			ch = c;
			frequency = f;
			left = l;
			right = r;
		}

		// Is the Node a leaf Node?
		private boolean isLeaf() {
			assert ((left == null) && (right == null)) || ((left != null) && (right != null));
			return (left == null) && (right == null);
		}

		// For PriorityQueue use, compare Nodes based on frequency
		@Override
		public int compareTo(Node that) {
			return (this.frequency - that.frequency);
		}
	}

	/** Given a frequency table which maps symbols of type V to their relative frequencies,
	*   the constructor should build a Huffman decoding trie. */
	public BinaryTrie(Map<Character, Integer> frequencyTable) {
		if (frequencyTable == null || frequencyTable.size() == 0) {
			throw new IllegalArgumentException("Incorrect input source");
		}

		PriorityQueue<Node> pq = new PriorityQueue<>();

		// Add all character in map into pq
		for (char c : frequencyTable.keySet()) {
			pq.add(new Node(c, frequencyTable.get(c), null, null));
		}

		// Add dummy node for special case: there is only one character in input table
		if (pq.size() == 1) {
			pq.add(new Node('\0', 0, null, null));
		}

		// Merge two smallest Node until there is only 1 Node
		while (pq.size() > 1) {
			Node left = pq.poll();
			Node right = pq.poll();
			pq.add(new Node('\0', left.frequency + right.frequency, left, right));
		}

		root = pq.poll();
	}

	/** Finds the longest prefix that matches the given querySequence and returns a Match object for that Match.*/
	public Match longestPrefixMatch(BitSequence querySequence) {
		StringBuilder sb = new StringBuilder();
		Node x = root;

		for (int i = 0; i < querySequence.length(); i += 1) {
			if (!x.isLeaf()) {
				if (querySequence.bitAt(i) == 0) {
					x = x.left;
					sb.append('0');
				} else if (querySequence.bitAt(i) == 1) {
					x = x.right;
					sb.append('1');
				} else {
					throw new IllegalArgumentException("The input BitSequence is not correct");
				}
			} else {
				break;
			}
		}

		return new Match(new BitSequence(sb.toString()), x.ch);
	}

	/** Returns the inverse of the coding trie. */
	public Map<Character, BitSequence> buildLookupTable() {
		Map<Character, BitSequence> lookupTable = new HashMap<>();
		trieTraversal(root, lookupTable, "");
		return lookupTable;
	}

	// Helper method for trieTraversal
	private void trieTraversal(Node x, Map<Character, BitSequence> m, String s) {
		if (!x.isLeaf()) {
			trieTraversal(x.left, m, s + '0');
			trieTraversal(x.right, m , s + '1');
		} else {
			m.put(x.ch, new BitSequence(s));
		}
	}
}