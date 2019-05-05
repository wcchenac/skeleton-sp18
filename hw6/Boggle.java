import java.util.*;

public class Boggle {
    // Helper class for store information of char, marked, row, and col
    private static class boardState {
        private char ch;
        private boolean marked;
        private int row, col;

        private boardState(char c, boolean b, int row, int col) {
            ch = c;
            marked = b;
            this.row = row;
            this.col = col;
        }

        private void visited() {
            marked = true;
        }

        private void unvisited() {
            marked = false;
        }
    }

    // Helper class for save the finding String from dictionary of certain length
	private static class stringLengthSet {
        private HashSet<String> bag = new HashSet<>();

    	private stringLengthSet(String s) {
    	    bag.add(s);
	    }

	    private void add(String s) {
    		bag.add(s);
	    }

	    private int size() {
    		return bag.size();
	    }
    }

    // File path of dictionary file
    private static String dictPath = "words.txt";
    private static Trie dictionary = new Trie();
    private static boardState[][] boardArray;
    private static int boardRow;
    private static int boardCol;

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        // YOUR CODE HERE
        /*
        // For runtime test
        long start, now;
        start = System.currentTimeMillis();
        */
        if (k < 0) {
            throw new IllegalArgumentException("The input maximum number is non-positive.");
        }
        initialDict();
        In boardInput = new In(boardFilePath);
        boardTransform(boardInput);

	    List<Character> word = new ArrayList<>();   // Preserve character for comparing with dictionary
	    Map<Integer, HashSet<String>> finding = new HashMap<>();    // Collect any word is founded in dictionary and board

        for (int j = 0; j < boardRow; j += 1) {
            for (int i = 0; i < boardCol; i += 1) {
	            dfs(boardArray[j][i], word, finding);
            }
        }
        /*
        // For runtime test
        List<String> result = findTopKString(k, finding);
        now = System.currentTimeMillis();
        System.out.println("Elapse time: " + ((now - start) / 1000.0) + ".");
		return result;
        */

        return findTopKString(k, finding);
    }

    // Initial: save all words from dictionary file into Trie
    private static void initialDict() {
        In dict = new In(dictPath);
        if (dict.isEmpty()) {
            throw new IllegalArgumentException("The dictionary file is not exist.");
        }
        while (!dict.isEmpty()) {
            dictionary.add(dict.readString());
        }
    }

    // Transform input board format into boardState array
    private static void boardTransform(In board) {
        String[] boardline = board.readAllLines();

        if (isRectangular(boardline)) {
            boardArray = new boardState[boardRow][boardCol];
            for (int j = 0; j < boardRow; j += 1) {
                for (int i = 0; i < boardCol; i += 1) {
                    boardArray[j][i] = new boardState(boardline[j].charAt(i), false, j, i);
                }
            }
        } else {
            throw new IllegalArgumentException("The input board is not rectangular.");
        }
    }

    // examine board is rectangular?
    private static boolean isRectangular(String[] input) {
        boardCol = input[0].length();
        boardRow = input.length;
        int boardLength = 0;
        for (String s : input) {
            boardLength += s.length();
        }
        return (boardLength == boardCol * boardRow);
    }

    // Return a neighbor list around (col, row)
    private static Stack<boardState> findNeighbor(int row, int col) {
        Stack<boardState> neighbor = new Stack<>();
        for (int j = row - 1; j <= row + 1; j += 1) {
            for (int i = col - 1; i <= col + 1; i += 1) {
                if (j < 0 || j >= boardRow || i < 0 || i >= boardCol || boardArray[j][i].marked) {
                    continue;
                } else {
                    neighbor.add(boardArray[j][i]);
                }
            }
        }
        return neighbor;
    }

    // Return a string from a character list
	private static String characterListToString(List<Character> wordInput) {
    	StringBuilder sb = new StringBuilder();
    	for (char c : wordInput) {
    		sb.append(c);
	    }
    	return sb.toString();
	}

    // DFS
    private static void dfs(boardState prefix, List<Character> word, Map<Integer, HashSet<String>> finding) {
    	prefix.visited();
    	word.add(prefix.ch);
    	String current = characterListToString(word);

	    List<String> wordsInDict = dictionary.keysWithPrefix(current);

	    if (wordsInDict.size() != 0) {
		    if (dictionary.contains(current)) {
			    if (!finding.containsKey(current.length())) {
				    finding.put(current.length(), new HashSet<String>());
			    }
			    finding.get(current.length()).add(current);
		    }

		    Stack<boardState> waitToExplore = findNeighbor(prefix.row, prefix.col);

		    if (waitToExplore.size() != 0) {
			    for (boardState bs : waitToExplore) {
				    dfs(bs, word, finding);
			    }
		    }
	    }

	    word.remove(word.size() - 1);
        prefix.unvisited();
    }

    // Getting the top k words from the finding result
	private static List<String> findTopKString(int k, Map<Integer, HashSet<String>> finding) {
    	List<String> result = new ArrayList<>();

		Object[] intArr = finding.keySet().toArray();

		for (int i = intArr.length - 1; k > 0 && i >= 0; i -= 1) {
			int amount = finding.get(intArr[i]).size();
			if (k > amount) {
				result.addAll(Arrays.asList(setToSortedArray(finding.get(intArr[i]))));
				k -= amount;
			} else {
				for (int j = 0; j < k; j += 1) {
					String[] sorted = setToSortedArray(finding.get(intArr[i]));
					result.add(sorted[j]);
				}
				k = 0;
			}
		}
    	return result;
	}

	// Given a set and return to a sorted array with ascending alphabetical order
	private static String[] setToSortedArray(Set<String> set) {
    	String[] result = new String[set.size()];
		int counter = 0;
		for (String s : set) {
			result[counter] = s;
			counter += 1;
		}
		Arrays.sort(result);
    	return result;
	}

    public static void main(String args[]) {

        for (String s : Boggle.solve(20, "smallBoard.txt")) {
        	System.out.println(s);
        }

        /*
    	In board = new In("exampleBoard.txt");
        boardTransform(board);
		*/

        /*
        // Test for transform
        for (int j = 0; j < boardRow ; j += 1) {
            for (int i = 0; i < boardCol; i += 1) {
                System.out.print(boardArray[j][i].ch);
            }
            System.out.println();
        }
		*/
        /*
        // Test for finding neighbor list
        boardArray[1][1].marked = true;
        for (boardState bs : findNeighbor(1, 1)) {
            System.out.println(bs.ch);
        }
        */
    }
}
