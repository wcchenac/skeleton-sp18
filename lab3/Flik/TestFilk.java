import static org.junit.Assert.*;

import org.junit.Test;

public class TestFilk {
	@Test
	public void testFilk() {
		Integer a = 1;
		a = 127;
		Integer b = a;
		a = 128;

		assertTrue(a == b);
	}
}
