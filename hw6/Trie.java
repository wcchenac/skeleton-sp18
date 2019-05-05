import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
	private class Node {
		private char curCh;
		private boolean isKey;
		private Map<Character, Node> map = new HashMap<>();

		private Node(){
		}

		private Node(char c , boolean b) {
			curCh = c;
			isKey = b;
		}
	}

	private Node root;

	public Trie() {
		root = new Node();
	}

	public void add(String key) {
		if (key == null || key.length() < 1) {
			return;
		}
		Node cur = root;
		for (char c : key.toCharArray()) {
			if (!cur.map.containsKey(c)) {
				cur.map.put(c, new Node(c, false));
			}
			cur = cur.map.get(c);
		}
		cur.isKey = true;
	}

	public boolean contains(String key) {
		Node cur = root;
		for (char c : key.toCharArray()) {
			if (!cur.map.containsKey(c)) {
				return false;
			} else {
				cur = cur.map.get(c);
			}
		}
		return (cur != null && cur.isKey);
	}

	public List<String> keysWithPrefix(String prefix) {
		List<String> result = new ArrayList<>();    // Store words
		Node cur = root;

		// Point 1 & 2
		for (char c : prefix.toCharArray()) {
			if (!cur.map.containsKey(c)) {
				return result;      // If there is any prefix char not in trie, directly return result
			} else {
				cur = cur.map.get(c);
			}
		}
		// Point 3
		if (cur.isKey) {
			result.add(prefix);
		}
		// Point 4 & 5
		if (cur.map.isEmpty()) {
			return result;
		} else {
			for (char c : cur.map.keySet()) {
				colHelp(prefix + c, result, cur.map.get(c));
			}
		}
		return result;
	}

	private void colHelp(String s, List<String> l, Node n) {
		if (n.isKey) {
			l.add(s);
		}
		if (n.map.isEmpty()) {
			return;
		} else {
			for (char c : n.map.keySet()) {
				colHelp(s + c, l, n.map.get(c));
			}
		}
	}
}
