package pkg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.GeneralSecurityException;
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
 * @author nute
 *
 */
public class Main extends JComponent {

	/**
	 * The class for the cells Values: 0 - empty, 1 - Contains a part of the
	 * snake, 2 - Contains the food to be eaten, 3 - Contains a part of an
	 * obstacle
	 */
	public static class Cell {
		private int value;

		public Cell(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}

	/**
	 * The class of the controlled snake
	 */
	public static class Snake {
		int x, y, dx, dy;
	}

	public static final Integer window_size = 700;
	public static final Cell[][] table = new Cell[window_size / 10][window_size / 10];
	public static Double timer = 0.0;
	public static Boolean inGame = false;

	/**
	 * Initializes the board
	 * 
	 * @param board
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
	 */
	public void drawCells(Graphics2D g2d) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				switch (table[i][j].getValue()) {
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
	 * Initialize and launch the application
	 * 
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(425, 100, window_size + 3, window_size + 47);

		addMenus(window);

		init(table);

		/* testing window */
		table[0][0].setValue(1);
		table[window_size / 10 - 1][0].setValue(2);
		table[0][window_size / 10 - 1].setValue(2);
		table[window_size / 10 - 1][window_size / 10 - 1].setValue(3);

		while (true) {
			timer += 0.1;
			if (!inGame)
				window.setTitle("Sneak by Gergő Balkus");
			else
				window.setTitle(String.format("Time: %.1fs", timer));
			Thread.sleep(100);
			window.setVisible(true);

			window.repaint();
		}
	}

}
