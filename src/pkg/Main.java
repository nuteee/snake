package pkg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * Main class where everything happens
 * 
 * @author NuTeeE
 *
 */
public class Main extends JComponent {

	/**
	 * Creating the constants
	 */
	public static final Integer window_size = 700;
	public static final Cell[][] board = new Cell[window_size / 10][window_size / 10];
	public static Double timer = 0.0;
	public static Boolean inGame = false;
	public static Boolean leftDirection = false;
	public static Boolean rightDirection = true;
	public static Boolean upDirection = false;
	public static Boolean downDirection = false;

	/**
	 * Class for the cells. Values: 0 - empty, 1 - Contains a part of the snake,
	 * 2 - Contains the food to be eaten, 3 - Contains a part of a obstacle
	 */
	public static class Cell {
		private int value;

		/**
		 * Constructor of this object
		 * 
		 * @param value
		 *            contains the value of that cell
		 */
		public Cell(int value) {
			this.value = value;
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
	 * Initialize and launch the application
	 * 
	 * @throws InterruptedException
	 *             if a thread is interrupted
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
			if (!inGame)
				;
			else {
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
