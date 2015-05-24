/**
 * 
 */
package pkg_test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import pkg.Cell;
import pkg.DBConnection;
import pkg.Eredmeny;
import pkg.Main;
import pkg.Main.Snake;

/**
 * @author nute
 *
 */
public class MainTest {
	private Cell[][] board;
	private Main.Snake snake;
	private int windowSize;
	private DBConnection dbc;
	private Boolean leftDirection, upDirection, rightDirection, downDirection;

	@Before
	public void setUp() throws SQLException, FileNotFoundException, IOException {
		windowSize = Main.window_size;
		leftDirection = Main.leftDirection;
		upDirection = Main.upDirection;
		rightDirection = Main.rightDirection;
		downDirection = Main.downDirection;
		board = new Cell[windowSize / 10][windowSize / 10];
		for (int i = 0; i < windowSize / 10; i++) {
			for (int j = 0; j < windowSize / 10; j++) {
				board[i][j] = new Cell(0);
			}
		}

		snake = new Snake(windowSize / 20 + 1, windowSize / 20 + 1, 4);

		dbc = new DBConnection();
	}

	@Test
	public void testBoardAfterInit() {
		int c = 0;
		for (int i = 0; i < windowSize / 10; i++) {
			for (int j = 0; j < windowSize / 10; j++) {
				c += board[i][j].getValue();
			}
		}

		assertEquals(0, c);
	}

	@Test
	public void testSnakeClass() {
		assertEquals(Snake.class, new Snake(0, 0).getClass());
		assertEquals(Snake.class, new Snake(0, 0, 1).getClass());
		snake.setLen(4);
		assertEquals(snake.getLen(), new Integer(4));
	}
	
	@Test
	public void testCellClass() {
		assertEquals(Cell.class, new Cell(0).getClass());
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
		assertTrue(Main.createFood(board));
	}

	@Test
	public void testAmountOfFoodAfterCreateFood() {
		Main.createFood(board);
		assertEquals(new Integer(1), Main.amountOfFood(board));
	}

	@Test
	public void testDBConnectionClass() {
		assertEquals(dbc.getClass(), DBConnection.class);
	}

	@Test
	public void testDBConnectionConnection() throws SQLException {
		Connection conn = dbc.connect(Main.jdbcUrl);
		assertNotNull(conn);
	}

	@Test
	public void testDBConnectionClose() throws SQLException {
		dbc.connect(Main.jdbcUrl);
		assertTrue(dbc.close());
	}

	@Test
	public void testDBConnectionExecutions() throws SQLException {
		Connection conn = dbc.connect(Main.jdbcUrl);
		dbc.executeUpdate(
				"INSERT INTO SNAKE (NEV, HOSSZ, IDO, DATUM) VALUES(?, ?, ?, ?)",
				"test", 1000, -1);

		ResultSet rset = dbc.executeQuery("SELECT * FROM SNAKE");
		
		boolean contains = false;
		while (rset.next()) {
			Eredmeny tmp = new Eredmeny(rset.getString("NEV"),
					rset.getInt("HOSSZ"), rset.getInt("IDO"),
					rset.getTimestamp("DATUM"));
			if(tmp.getTime() == -1)
				contains = true;
		}
		PreparedStatement p = conn.prepareStatement("DELETE FROM SNAKE WHERE IDO=-1");
		p.executeUpdate();
		p.close();
		
		dbc.close();
		
		assertTrue(contains);
	}
	
	@Test
	public void testEredmeny() {
		Timestamp t = new Timestamp(new java.util.Date().getTime());
		Eredmeny e1 = new Eredmeny("nute", 1, 5, t);
		Eredmeny e2 = new Eredmeny("nute", 1, 5, t);
		Eredmeny e3 = new Eredmeny("asd", 9, 9, new Timestamp(new java.util.Date().getTime()));
		
		assertEquals(e1.getName(), e2.getName());
		assertEquals(e1.getLen(), e2.getLen());
		assertEquals(e1.getDate(), e2.getDate());
		assertEquals(e1.getTime(), e2.getTime());
		assertEquals(e1.toString(), e2.toString());
		assertTrue(e1.equals(e1));
		assertFalse(e1.equals(null));
		assertFalse(e1.equals(new Main.Tadapter()));
		assertFalse(e1.equals(e3));
		assertFalse(e1.equals(new Eredmeny(null, null, null, null)));
		assertTrue(e1.equals(e2));
		
	}

}
