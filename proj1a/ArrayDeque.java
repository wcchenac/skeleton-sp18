public class ArrayDeque<T> {
	private T[] arr;
	private int size;	    	//ArrayList size
	private int front = 1;          //List front
	private int end = 0;            //List end
	private int nextFirst = 0;
	private int nextLast = 1;

	/** Create an empty array deque. */
	public ArrayDeque() {
		arr = (T[]) new Object[8];
		size = 0;
	}

	/** Adds an item of type T to the front of the deque. */
	public void addFirst(T item) {
		arr[nextFirst] = item;
		size += 1;
		front = nextFirst;
		nextFirst = minusOne(nextFirst);
		checkResize();
	}

	/** Adds an item of type T to the back of the deque. */
	public void addLast(T item) {
		arr[nextLast] = item;
		size += 1;
		end = nextLast;
		nextLast = plusOne(nextLast);
		checkResize();
	}

	/** Check nextFirst value. floorMod(a, b) = a - floorDiv(a,b) */
	private int minusOne(int x) {
		return Math.floorMod(x - 1, arr.length);
	}

	/** Check nextLast value. floorMod(a, b) = a - floorDiv(a,b) */
	private int plusOne(int x) {
		return Math.floorMod(x + 1, arr.length);
	}

	/** Requirement for resize. */
	private void checkResize() {
		double R = (double) size / arr.length;
		if (R >= 0.8) {
			resize(arr.length * 2);     //expansion
		} else if (R < 0.25 && size > 1) {
			resize(arr.length / 2);     //contraction
		}
	}

	/** Resize implementation */
	private void resize(int capacity) {
		T[] newArr = (T[]) new Object[capacity];
		int i, j;
		for (i = front, j = 1; i != nextLast; i = plusOne(i), j++) {
			newArr[j] = arr[i];
		}
		arr = newArr;
		nextFirst = 0;
		nextLast = size + 1;
		front = 1;
		end = size;
	}

	/** Returns true if deque is empty. false otherwise. */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/** Return the number of items in the deque. */
	public int size() {
		return size;
	}

	/** Prints the items in the deque from first to last, separated by a space. */
	public void printDeque() {
		for (int i = front; i != nextLast; i = plusOne(i)) {
			if (arr[i] == null) {
				continue;
			} else {
				System.out.print(arr[i] + " ");
			}
		}
		System.out.println();
	}

	/** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
	public T removeFirst() {
		T toBeRemove = arr[front];
		arr[front] = null;
		size -= 1;
		nextFirst = front;
		front = plusOne(nextFirst);
		checkResize();
		return toBeRemove;
	}

	/** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
	public T removeLast() {
		T toBeRemove = arr[end];
		arr[end] = null;
		size -= 1;
		nextLast = end;
		end = minusOne(end);
		checkResize();
		return toBeRemove;
	}

	/** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
	 *  If no such item exists, returns null. Must not alter the deque!. */
	public T get(int index) {
		if (index >= size || index < 0) {
			return null;
		}
		index = Math.floorMod(index + front, arr.length);
		return arr[index];
	}
}
