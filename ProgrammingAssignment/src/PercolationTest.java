import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PercolationTest {
	Percolation pc;
	@Before
	public void setUp() throws Exception {
		  
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPercolation() {
		fail("Not yet implemented");
	}

	@Test
	public void testOpen() {
		pc = new Percolation (3);
		pc.open(1, 1);
		pc.open(2, 1);
		pc.open(3, 1);
		assert pc.percolates();
	}

	@Test
	public void testIsOpen() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFull() {
		fail("Not yet implemented");
	}

	@Test
	public void testNumberOfOpenSites() {
		fail("Not yet implemented");
	}

	@Test
	public void testPercolates() {
		fail("Not yet implemented");
	}

}
