package pkg;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

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
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1337L;
	/**
	 * The main frame.
	 */
	private static final JFrame window = new JFrame("Sneak by Gergő Balkus");
	/**
	 * The size of the window.
	 */
	public static final Integer window_size = 500;
	/**
	 * The main board.
	 */
	public static final Cell[][] board = new Cell[window_size / 10][window_size / 10];

	/**
	 * The timer used for measuring the in-game time.
	 */
	public static Double timer = 0.0;
	/**
	 * The boolean for deciding if we are in-game.
	 */
	public static Boolean inGame = false;
	/**
	 * The boolean for deciding if we are heading left.
	 */
	public static Boolean leftDirection = false;
	/**
	 * The boolean for deciding if we are heading right.
	 */
	public static Boolean rightDirection = true;
	/**
	 * The boolean for deciding if we are heading up.
	 */
	public static Boolean upDirection = false;
	/**
	 * The boolean for deciding if we are heading down.
	 */
	public static Boolean downDirection = false;
	/**
	 * The boolean for pausing the game.
	 */
	public static Boolean pause = false;
	/**
	 * The food to be eaten.
	 */
	public static int[] food = new int[2];
	/**
	 * The string for storing the user's nickname.
	 */
	public static String username;

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
		private ArrayList<int[]> body;

		/**
		 * Constructor of this object
		 * 
		 * @param x
		 *            the x value of the snake
		 * @param y
		 *            the y value of the snake
		 */
		public Snake(int x, int y) {
			this.x = x;
			this.y = y;
			body = new ArrayList<int[]>();
			body.add(new int[] { this.x, this.y });
		}

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
			for (int i = 0; i < len; i++)
				body.add(new int[] { x - i, y });

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
		 * Moves the snake in a direction
		 * 
		 * @throws Exception
		 *             When the Snake collides with itself
		 */
		void move() throws Exception {

			for (int i = body.size() - 1; i > 0; i--)
				body.set(i, this.body.get(i - 1));

			if (leftDirection) {
				if (board[body.get(0)[0] - 1][body.get(0)[1]].getValue() == 1)
					throw new Exception("The Snake collided with itself.");
				body.set(0, new int[] { --body.get(0)[0], body.get(0)[1] });
			} else if (rightDirection) {
				if (board[body.get(0)[0] + 1][body.get(0)[1]].getValue() == 1)
					throw new Exception("The Snake collided with itself.");
				body.set(0, new int[] { ++body.get(0)[0], body.get(0)[1] });
			} else if (upDirection) {
				if (board[body.get(0)[0]][body.get(0)[1] - 1].getValue() == 1)
					throw new Exception("The Snake collided with itself.");
				body.set(0, new int[] { body.get(0)[0], --body.get(0)[1] });
			} else if (downDirection) {
				if (board[body.get(0)[0]][body.get(0)[1] + 1].getValue() == 1)
					throw new Exception("The Snake collided with itself.");
				body.set(0, new int[] { body.get(0)[0], ++body.get(0)[1] });
			}

		}

		/**
		 * Makes the snake eat the food.
		 * 
		 * Occurs when the snake's head is on the food.
		 */
		void eat() {

			int i, j;

			if (body.size() > 1) {
				i = body.get(len - 1)[0];
				j = body.get(len - 1)[1];

				i += i - body.get(len - 2)[0];
				j += j - body.get(len - 2)[1];

				body.add(new int[] { i, j });
			} else {
				if (leftDirection) {
					body.add(new int[] {
							body.get(len - 1)[0] < board.length ? ++body
									.get(len - 1)[0] : body.get(len - 1)[0],
							body.get(len - 1)[1] });
				} else if (rightDirection) {
					body.add(new int[] {
							body.get(len - 1)[0] > 0 ? --body.get(len - 1)[0]
									: body.get(len - 1)[0],
							body.get(len - 1)[1] });
				} else if (upDirection) {
					body.add(new int[] {
							body.get(len - 1)[0],
							body.get(len - 1)[1] < board[0].length ? --body
									.get(len - 1)[1] : body.get(len - 1)[1] });
				} else if (downDirection) {
					body.add(new int[] {
							body.get(len - 1)[0],
							body.get(len - 1)[1] > 0 ? ++body.get(len - 1)[1]
									: body.get(len - 1)[1] });
				}
			}

			len++;
		}

		public void clear() {
			body.clear();
			body.add(new int[] { x, y });
			len = 1;
			rightDirection = true;

		}
	}

	/**
	 * Extended KeyAdapter class for listening to pressed keys (directions)
	 * 
	 * @author NuTeeE
	 */
	public static class Tadapter extends KeyAdapter {

		/**
		 * Method for listening to pressed keys (directions)
		 * 
		 * @param e
		 *            KeyEvent object storing information about the event
		 */
		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_P)
				pause = !(pause);

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
		JMenu game;
		JMenuItem new_game_menuItem, quit_menuItem;

		menuBar = new JMenuBar();
		game = new JMenu("Game");
		game.getAccessibleContext().setAccessibleDescription(
				"The main menu to control the game");
		menuBar.add(game);
		new_game_menuItem = new JMenuItem("New game..", KeyEvent.VK_N);
		new_game_menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));

		quit_menuItem = new JMenuItem("Quit.", KeyEvent.VK_Q);
		quit_menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));

		new_game_menuItem
				.addActionListener(e -> {
					username = (String) JOptionPane
							.showInputDialog(
									window,
									"Please type your nickname to be showed on the scoreboard later!\n",
									"Type your nickname",
									JOptionPane.INFORMATION_MESSAGE);
					window.getContentPane().add(new Main());
					timer = 0.0;
					inGame = true;
				});

		quit_menuItem.addActionListener(e -> {
			JOptionPane.showMessageDialog(window, "Thanks for playing!", "Good bye", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		});

		game.add(new_game_menuItem);
		game.addSeparator();
		game.addSeparator();
		game.add(quit_menuItem);

		window.setJMenuBar(menuBar);
	}

	/**
	 * Initializes the board
	 * 
	 * @param board
	 *            the board being used
	 */
	public static void init() {

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = new Cell(0);

		createFood();
	}

	/**
	 * Creates the food.
	 */
	public static void createFood() {
		Random r = new Random();

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getValue() == 2)
					board[i][j].setValue(0);
			}

		do {
			food[0] = r.nextInt(window_size / 10 - 1);
			food[1] = r.nextInt(window_size / 10 - 1);
		} while (board[food[0]][food[1]].getValue() == 1);
		board[food[0]][food[1]].setValue(2);
	}

	/**
	 * Returns the amount of foods on the board
	 * 
	 * @return the amount of foods on the board
	 */
	public static Integer amountOfFood() {
		int c = 0;
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				if (board[i][j].getValue() == 2)
					c++;

		return c;
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

		if (board[s.body.get(0)[0]][s.body.get(0)[1]].getValue() == 2) {
			s.eat();
			board[s.body.get(0)[0]][s.body.get(0)[1]].setValue(1);
			if (amountOfFood() == 0)
				createFood();
		}

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getValue() == 2)
					continue;
				board[i][j].setValue(0);
			}

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

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setSize(window_size + 3, window_size + 47);
		window.setLocationRelativeTo(null);
		// window.setBounds(425, 0, );
		window.addKeyListener(new Tadapter());
		addMenus(window);

		init();

		Snake s = new Snake(window_size / 20 + 1, window_size / 20, 1);

		while (true) {
			if (inGame) {
				if (!pause) {
					timer += 0.1;
					window.setTitle(String.format(
							"Time: %.1fs, Size: %d, Name: %s", timer,
							s.getLen() - 1, username));
					try {
						checkBoard(s);
						s.move();
					} catch (Exception e) {
						e.printStackTrace();
						inGame = false;
						s.clear();
						window.setTitle(String.format("Game Over. Length: %d",
								s.getLen() - 1));
					}
				}

			}
			Thread.sleep(100);
			window.setVisible(true);
			window.repaint();
		}
	}
}
