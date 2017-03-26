import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	Board b;
	@Before
	public void setUp() throws Exception {
		 int [][] blocks=new int[][]
		 {
			 {0,1,3},
			 {4,2,5},
			 {7,8,6}
		 };
		 b=new Board(blocks);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testDimension() {
		fail("Not yet implemented");
	}

	@Test
	public void testHamming() {
		System.out.println("hamming:" +b.hamming());
	}

	@Test
	public void testManhattan() {
		System.out.println("manhattan:" +b.manhattan());
	}

	@Test
	public void testIsGoal() {
		fail("Not yet implemented");
	}

	@Test
	public void testTwin() {
		System.out.println(b.twin());
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testNeighbors() {
		for(Board n:b.neighbors()){
			System.out.println(n);
		}
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
