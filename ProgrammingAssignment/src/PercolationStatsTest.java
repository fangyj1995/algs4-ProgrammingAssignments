import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStatsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPercolationStats() {
	   Stopwatch watch = new Stopwatch();
	   PercolationStats pcStats = new PercolationStats(200 , 100);
	   System.out.println(watch.elapsedTime() + " seconds");
	   System.out.println("______________________________");
	   
	   watch = new Stopwatch();
	   pcStats = new PercolationStats(800 , 100);
	   System.out.println(watch.elapsedTime() + " seconds");
	   System.out.println("______________________________");
	   
	   watch = new Stopwatch();
	   pcStats = new PercolationStats(200 , 800);
	   System.out.println(watch.elapsedTime() + " seconds");
	  
	}

	@Test
	public void testMean() {
		fail("Not yet implemented");
	}

	@Test
	public void testStddev() {
		fail("Not yet implemented");
	}

	@Test
	public void testConfidenceLo() {
		fail("Not yet implemented");
	}

	@Test
	public void testConfidenceHi() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
