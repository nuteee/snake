package pkg;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class where everything happens
 * 
 * @author NuTeeE
 *
 */
public class Main extends JComponent {

	/**
	 * The Logback logger
	 */
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1337L;
	/**
	 * The size of the window
	 */
	public static final Integer window_size = 700;
	/**
	 * The main board
	 */
	public static final Cell[][] board = new Cell[window_size / 10][window_size / 10];

	/**
	 * Enumerations for the directions
	 */
	public enum Direction {
		NORTH, EAST, SOUTH, WEST, NULL
	}

	/**
	 * The timer used for measuring the in-game time
	 */
	public static Double timer = 0.0;
	/**
	 * The boolean for deciding if we are in-game
	 */
	public static Boolean inGame = false;
	/**
	 * The boolean for deciding if we are heading left
	 */
	public static Boolean leftDirection = false;
	/**
	 * The boolean for deciding if we are heading right
	 */
	public static Boolean rightDirection = true;
	/**
	 * The boolean for deciding if we are heading up
	 */
	public static Boolean upDirection = false;
	/**
	 * The boolean for deciding if we are heading down
	 */
	public static Boolean downDirection = false;

	/**
	 * Class for the cells.
	 */
	public static class Cell {
		/**
		 * The value of the Cell.
		 * 
		 * If the value is: 0 - the cell is empty; 1 - it has the part of the
		 * snake; 2 - it has a food
		 */
		private int value;
		/**
		 * The direction the cell should send the snake's body to
		 */
		private Direction direction;

		/**
		 * Constructor of this object
		 * 
		 * @param value
		 *            contains the value of that cell
		 */
		public Cell(int value) {
			this.value = value;
			this.direction = Direction.NULL;
		}

		/**
		 * Constructor of this object
		 * 
		 * @param value
		 *            contains the value of that cell
		 * @param d
		 *            contains the direction of that cell
		 */
		public Cell(int value, Direction d) {
			this.value = value;
			this.direction = d;
		}

		/**
		 * Returns the value of this object
		 * 
		 * @return the value of this object
		 */
		public int getValue() {
			return value;
		}

		/**
		 * Sets the value of this object
		 * 
		 * @param value
		 *            the value of this object
		 */
		public void setValue(int value) {
			this.value = value;
		}

		/**
		 * Returns the direction of this object
		 * 
		 * @return the direction of this object
		 */
		public Direction getDirection() {
			return direction;
		}

		/**
		 * Sets the direction of this object
		 * 
		 * @param direction
		 *            the direction of this object
		 */
		public void setDirection(Direction direction) {
			this.direction = direction;
		}
	}

	/**
	 * The class of the controlled snake
	 */
	public static class Snake {
		private int x, y, len;
		ArrayList<int[]> body;

		/**
		 * Constructor of this object
		 * 
		 * @param x
		 *            the x value of the snake
		 * @param y
		 *            the y value of the snake
		 * @param len
		 *            the length of the snake
		 */
		public Snake(int x, int y, int len) {
			this.x = x;
			this.y = y;
			this.len = len;
			body = new ArrayList<int[]>();
			for (int i = 0; i < len; i++) {
				int[] tmp = new int[2];
				tmp[0] = x - i;
				tmp[1] = y;
				body.add(tmp);
			}
		}

		/**
		 * Returns the length of the snake
		 * 
		 * @return the length of the snake
		 */
		public int getLen() {
			return len;
		}

		/**
		 * Sets the length of the snake
		 * 
		 * @param len
		 *            the length of the snake
		 */
		public void setLen(int len) {
			this.len = len;
		}

		/**
		 * Returns the x value of the snake
		 * 
		 * @return the x value of the snake
		 */
		public int getX() {
			return x;
		}

		/**
		 * Sets the x value of the snake
		 * 
		 * @param x
		 *            the x value of the snake
		 */
		public void setX(int x) {
			this.x = x;
		}

		/**
		 * Returns the y value of the snake
		 * 
		 * @return the y value of the snake
		 */
		public int getY() {
			return y;
		}

		/**
		 * Sets the y value of the snake
		 * 
		 * @param y
		 *            the y value of the snake
		 */
		public void setY(int y) {
			this.y = y;
		}

		/**
		 * Moves the snake in the right direction
		 */
		public void move() {
			if (leftDirection)
				for (int[] i : body)
					i[0]--;

			else if (rightDirection)
				for (int[] i : body)
					i[0]++;

			else if (upDirection)
				for (int[] i : body)
					i[1]--;

			else if (downDirection)
				for (int[] i : body)
					i[1]++;
		}
	}

	/**
	 * Extended KeyAdapter class for listening to pressed keys (directions)
	 * 
	 * @author NuTeeE
	 */
	public static class Tadapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_UP) && (!downDirection)) {
				upDirection = true;
				leftDirection = false;
				rightDirection = false;
			}

			if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
				downDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
		}
	}

	/**
	 * Initializes the board
	 * 
	 * @param board
	 *            the board being used
	 */
	public static void init(Cell[][] board) {
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = new Cell(0);
	}

	/**
	 * Draws the main board
	 * 
	 * @param g2d
	 *            our Graphics2D object
	 */
	public void drawBoard(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		Dimension size = getSize();
		Insets insets = getInsets();

		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.bottom;

		for (int i = 0; i <= w; i += 10) {
			g2d.drawLine(i, 0, i, h);
			g2d.drawLine(0, i, w, i);
		}
	}

	/**
	 * Paints the cells based on their values
	 * 
	 * @param g2d
	 *            our Graphics2D object
	 */
	public void drawCells(Graphics2D g2d) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				switch (board[i][j].getValue()) {
				case 1:
					g2d.setColor(new Color(0, 0, 0));
					g2d.fillRect(i * 10 + 1, j * 10 + 1, 9, 9);
					break;
				case 2:
					g2d.setColor(new Color(0, 150, 200));
					g2d.fillRect(i * 10 + 1, j * 10 + 1, 9, 9);
					break;
				case 3:
					g2d.setColor(new Color(125, 60, 20));
					g2d.fillRect(i * 10 + 1, j * 10 + 1, 9, 9);
					break;
				}
			}
		}
	}

	/**
	 * Paints the stuffs
	 * 
	 * @param g
	 *            our Graphics object
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		drawBoard(g2d);
		drawCells(g2d);

	}

	/**
	 * Creates the menubar
	 * 
	 * @param window
	 *            the JFrame object being used
	 */
	public static void addMenus(JFrame window) {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem new_game_menuItem, quit_menuItem;

		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		menu.getAccessibleContext().setAccessibleDescription(
				"The main menu to control the game");
		menuBar.add(menu);
		new_game_menuItem = new JMenuItem("New game..", KeyEvent.VK_N);
		new_game_menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));

		quit_menuItem = new JMenuItem("Quit.", KeyEvent.VK_Q);
		quit_menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));

		new_game_menuItem.addActionListener(e -> {
			window.getContentPane().add(new Main());
			timer = 0.0;
			inGame = true;
		});

		quit_menuItem.addActionListener(e -> {
			System.exit(0);
		});

		menu.add(new_game_menuItem);
		menu.addSeparator();
		menu.addSeparator();
		menu.addSeparator();
		menu.addSeparator();
		menu.add(quit_menuItem);

		window.setJMenuBar(menuBar);
	}

	/**
	 * Checks the objects on the board
	 * 
	 * @param s
	 *            the snake object
	 * @throws ArrayIndexOutOfBoundsException
	 *             when the snake collides with the border
	 */
	public static void checkBoard(Snake s)
			throws ArrayIndexOutOfBoundsException {

		for (int i = 0; i < window_size / 10; i++)
			for (int j = 0; j < window_size / 10; j++)
				board[i][j].setValue(0);

		for (int[] i : s.body) {
			board[i[0]][i[1]].setValue(1);
		}
	}

	/**
	 * Initializes and launch the application
	 * 
	 * @throws InterruptedException
	 *             if the thread is interrupted
	 * @param args
	 *            basic stuff.
	 */
	public static void main(String[] args) throws InterruptedException {

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setBounds(425, 0, window_size + 3, window_size + 47);
		window.addKeyListener(new Tadapter());
		addMenus(window);

		init(board);
		/* testing window */
		// board[0][0].setValue(1);
		// board[window_size / 10 - 1][0].setValue(2);
		// board[0][window_size / 10 - 1].setValue(2);
		// board[window_size / 10 - 1][window_size / 10 - 1].setValue(3);

		timer += 0.1;
		Snake s = new Snake(window_size / 20 + 1, window_size / 20, 25);

		window.setTitle("Sneak by Gergő Balkus");
		while (true) {
			if (inGame) {
				timer += 0.1;
				window.setTitle(String.format("Time: %.1fs", timer));
				try {
					checkBoard(s);
				} catch (ArrayIndexOutOfBoundsException e) {
					inGame = false;
					window.setTitle("Game Over");
				}
				s.move();
			}
			Thread.sleep(100);
			window.setVisible(true);
			window.repaint();
		}
	}
}
