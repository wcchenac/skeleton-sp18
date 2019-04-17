// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] arb;

    /** Create a new ArrayRingBuffer with the given capacity. */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        arb = (T[]) new Object[capacity];
        this.capacity = capacity;
        first = last = fillCount = 0;
    }

    /** Adds x to the end of the ring buffer.
     * If there is no room, then throw new RuntimeException("Ring buffer overflow"). */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        arb[last] = x;
        last = Math.floorMod(last + 1, this.capacity());
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer.
     * If the buffer is empty, then throw new RuntimeException("Ring buffer underflow"). */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T toBeDelete = arb[first];
        arb[first] = null;
        first = Math.floorMod(first + 1, this.capacity());
        fillCount -= 1;
        return toBeDelete;
    }

    /** Return oldest item, but don't remove it. */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return arb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }

    private class BufferIterator implements Iterable<T> {
        private int ptr;

        public BufferIterator() {
            ptr = 0;
        }

        public boolean hasNext() {
            return (ptr < capacity);
        }

        public T next() {
            T returnValue = arb[ptr];
            ptr += 1;
            return returnValue;
        }
    }
}
