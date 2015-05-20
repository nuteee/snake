/**
 * 
 */
package pkg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pkg.Main.Tadapter;

/**
 * @author nute
 *
 */
public class MainTest {
	private Cell[][] board;
	private Main.Snake snake;
	private int windowSize;

	@Before
	public void setUp() {
		windowSize = Main.window_size;
		board = new Cell[windowSize/10][windowSize/10];
		for (int i = 0; i < windowSize/10; i++) {
			for (int j = 0; j < windowSize/10; j++) {
				board[i][j] = new Cell(0);
			}
		}

		snake = new Main.Snake(windowSize/20 + 1, windowSize/20 + 1);
	}

	@Test
	public void testBoardAfterInit() {
		int c = 0;
		for(int i = 0; i < windowSize/10; i++) {
			for(int j = 0; j < windowSize/10; j++) {
				c += board[i][j].getValue();
			}
		}
		
		assertEquals(0, c);
	}
	
	@Test
	public void testCellClass() {
		assertEquals(Cell.class, new Cell(0).getClass());
	}
	
	@Test
	public void testSnakeClass() {
		assertEquals(Main.Snake.class, new Main.Snake(0, 0).getClass());
	}
	
	@Test
	public void testCellSetValue() {
		Cell c = new Cell(0);
		c.setValue(1);
		assertEquals(1, c.getValue());
	}
	
	@Test
	public void testAmountOfFoodAfterInit() {
		assertEquals(new Integer(0), Main.amountOfFood(board));
	}

	@Test
	public void testCreateFood() {
		assertEquals(true, Main.createFood(board));
	}
	
	@Test
	public void testAmountOfFoodAfterCreateFood() {
		Main.createFood(board);
		assertEquals(new Integer(1), Main.amountOfFood(board));
	}

}
