package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
	@Test
	public void testCreateNewArrayRingBuffer() {
		ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
		int expectedCapacity = 10;
		int expectedFillCount = 0;
		int expectedFirst = 0;
		int expectedLast = 0;
		assertEquals(expectedCapacity, arb.capacity());
		assertEquals(expectedFillCount, arb.fillCount());
		//assertEquals(expectedFirst, arb.first);
		//assertEquals(expectedLast, arb.last);
	}

	@Test
	public void testEnqueue() {
		ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);    // null null null null null
		arb.enqueue(10);        // 10 null null null null
		arb.enqueue(20);        // 10 20 null null null
		arb.enqueue(30);        // 10 20 30 null null
		arb.enqueue(40);        // 10 20 30 40 null
		arb.enqueue(50);        // 10 20 30 40 50

		int expectedFillCount = 5;
		int expectedFirst = 0;
		int expectedLast = 0;

		assertEquals(expectedFillCount, arb.fillCount());
		//assertEquals(expectedFirst, arb.first);
		//assertEquals(expectedLast, arb.last);
	}

	@Test
	public void testDequeue() {
		ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);    // null null null null null
		arb.enqueue(10);        // 10 null null null null
		arb.enqueue(20);        // 10 20 null null null
		arb.enqueue(30);        // 10 20 30 null null


		int expectedFillCount = 2;
		int expectedFirst = 1;
		int expectedLast = 3;
		int expectedRemove = 10;
		int actualRemove = arb.dequeue();             // null 20 30 null null

		assertEquals(expectedFillCount, arb.fillCount());
		//assertEquals(expectedFirst, arb.first);
		//assertEquals(expectedLast, arb.last);
		assertEquals(expectedRemove, actualRemove);
	}

	/** Calls tests for ArrayRingBuffer. */
	public static void main(String[] args) {
		jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
	}
} 
