public class LinkedListDeque<T> {
    private class ListNode {
        public ListNode prev;
        public T item;
        public ListNode next;

        public ListNode(ListNode p, T i, ListNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private ListNode sentinel;
    private ListNode last;
    private ListNode first;
    private int size;

    /** Create a sentinel circularly. */
    public ListNode createSentinel() {
        sentinel = new ListNode(null, (T) (Integer) 33, null);
        first = last = sentinel.prev = sentinel.next = sentinel;
        return sentinel;
    }

    /** Create an empty linked list deque. */
    public LinkedListDeque() {
        createSentinel();
        size = 0;
    }

    /** Create a linked list deque with T item. */
    public LinkedListDeque(T item) {
        createSentinel();
        sentinel.next = new ListNode(sentinel, item, sentinel.next);
        sentinel.prev = first = last = sentinel.next;
        /** Sentinel.prev always point to last and Sentinel.next always points to first. */
        size = 1;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        sentinel.next = new ListNode(sentinel, item, first);
        first = first.prev = sentinel.next;
        last = sentinel.prev;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        sentinel.prev = new ListNode(last, item, sentinel);
        last = last.next = sentinel.prev;
        first = sentinel.next;
        size += 1;
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
        ListNode p = first;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            sentinel.next = first = first.next;
            first.prev = sentinel;
            size -= 1;
            return (T) this;
        }
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            sentinel.prev = last = last.prev;
            last.next = sentinel;
            size -= 1;
            return (T) this;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. Must not alter the deque!. (Iterative) */
    public T get(int index) {
        ListNode m = first;
        ListNode n = last;
        if ((index + 1) > size) {
            return null;
        }
        if (2 * (index + 1) < size) {      // start from first
            for (int j = 0; j < index; j++) {
                m = m.next;
            }
            return m.item;
        } else {                        // start from last
            for (int j = 0; j < (size - index - 1); j++) {      //reverse index
                n = n.prev;
            }
            return n.item;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *  If no such item exists, returns null. Must not alter the deque!. (Recursive) */
    private T getRecursive(ListNode p, int index) {
        if (index ==0) {
            return p.next.item;
        } else {
            p = p.next;
            return getRecursive(p, index-1);
        }
    }

    public T getRecursive(int index) {
        if ((index + 1) > size) {
            return null;
        } else {
            return getRecursive(sentinel, index);
        }
    }
}
