package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;


public class Solver{

	private boolean solvable;
	private int step;
	private Stack<WorldState> solution = new Stack<>();

	private class Node implements Comparable<Node>{
		private WorldState startState;
		private int moveStep;
		private Node preNode;

		public Node(WorldState w, int m, Node preNode) {
			this.startState = w;
			this.moveStep = m;
			this.preNode = preNode;
		}

		@Override
		public int compareTo(Node that) {
			int diff = (this.moveStep + this.startState.estimatedDistanceToGoal()) - (that.moveStep + that.startState.estimatedDistanceToGoal());
			return diff;
		}
	}

	/** Constructor which solves the puzzle, computing everything necessary for
	 *  moves() and solution() to not have to solve the problem again.
	 *  Solves the puzzle using the A* algorithm.
	 *  Assumes a solution exists.*/
	public Solver(WorldState initial) {

		if (initial == null) {
			throw new IllegalArgumentException();
		}

		solvable = false;

		// Create min PQ and insert the Node of initial world state
		MinPQ<Node> pq = new MinPQ<>();
		pq.insert(new Node(initial, 0, null));

		// Remove the less cost Node as current ptr.
		// If it is not the goal, insert its neighbors to min PQ.
		// Update current ptr to the less cost Node until goal is achieved.
		Node current = pq.delMin();
		while (!current.startState.isGoal()) {
			for (WorldState neighbor : current.startState.neighbors()) {
				if (current.preNode == null) {
					pq.insert(new Node(neighbor, current.moveStep + 1, current));
				} else if (!neighbor.equals(current.preNode.startState)) {
					pq.insert(new Node(neighbor, current.moveStep + 1, current));
				}
			}
			current = pq.delMin();
		}

		// End of while loop, current is goal.
		step = current.moveStep;

		// Store the solution to goal using stack.
		while (current != null) {
			solution.push(current.startState);
			current = current.preNode;
		}

		if (solution.peek() == initial) {
			solvable = true;
		}

		// For reverse stack use
		Stack<WorldState> result = new Stack<>();
		while (!solution.empty()) {
			result.push(solution.pop());
		}
		solution = result;
	}

	private boolean isSolvable() {
		return solvable;
	}

	/** Returns the minimum number of moves to solve the puzzle starting at the initial WorldState. */
	public int moves() {
		if (isSolvable()) {
			return step;
		} else {
			return -1;
		}
	}

	/** Returns a sequence of WorldStates from the initial WorldState to the solution*/
	public Iterable<WorldState> solution() {
		if (isSolvable()) {
			return solution;
		} else {
			return null;
		}
	}
}
