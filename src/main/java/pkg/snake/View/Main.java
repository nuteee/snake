package pkg.snake.View;

/*
 * #%L
 * Snake
 * %%
 * Copyright (C) 2015 NuTeeE
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pkg.snake.Control.CoreMethods;
import pkg.snake.Control.DBConnection;
import pkg.snake.Control.Tadapter;
import pkg.snake.Model.Cell;
import pkg.snake.Model.Eredmeny;
import pkg.snake.Model.Snake;

/**
 * @author NuTeeE
 */
public class Main extends JComponent {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1337L;
	/**
	 * The Logback logger of the <code>Main</code> class.
	 */
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * The JDBC URL.
	 */
	public static final String jdbcUrl = "jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g";
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
	 * The boolean for saving the game.
	 */
	public static Boolean save = false;
	/**
	 * The boolean for loading the game.
	 */
	public static Boolean load = false;
	/**
	 * The food to be eaten.
	 */
	public static Integer[] food = new Integer[2];
	/**
	 * The string for storing the user's nickname.
	 */
	public static String username;

	// /**
	// * Draws the main board.
	// *
	// * @param g2d
	// * A <code>Graphics2D</code> object.
	// */
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

	// /**
	// * Paints the cells based on their values.
	// *
	// * @param g2d
	// * A <code>Graphics2D</code> object.
	// */
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
					g2d.setColor(new Color(255, 0, 0));
					g2d.fillRect(i * 10 + 1, j * 10 + 1, 9, 9);
					break;
				}
			}
		}
	}

	// /**
	// * Paints the stuffs.
	// *
	// * @param g
	// * A <code>Graphics</code> object.
	// */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		drawBoard(g2d);
		drawCells(g2d);

	}

	/**
	 * Creates the menubar.
	 * 
	 * @param window
	 *            The <code>JFrame</code> object being used.
	 */
	public static void addMenus(JFrame window) {
		logger.info("Adding the menus.");
		JMenuBar menuBar;
		JMenu game, stats;
		JMenuItem new_game, load_menuItem, save_menuItem, quit, hallOfFame;

		menuBar = new JMenuBar();
		game = new JMenu("Game");
		game.setMnemonic(KeyEvent.VK_G);

		stats = new JMenu("Stats");
		stats.setMnemonic(KeyEvent.VK_T);

		game.getAccessibleContext().setAccessibleDescription(
				"The main menu to control the game");

		menuBar.add(game);
		menuBar.add(stats);

		new_game = new JMenuItem("New game..", KeyEvent.VK_N);
		new_game.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));

		save_menuItem = new JMenuItem("Save..", KeyEvent.VK_S);
		save_menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));

		load_menuItem = new JMenuItem("Load..", KeyEvent.VK_L);
		load_menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));

		quit = new JMenuItem("Quit.", KeyEvent.VK_Q);
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.ALT_MASK));

		hallOfFame = new JMenuItem("Hall of Fame", KeyEvent.VK_H);
		hallOfFame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.ALT_MASK));

		new_game.addActionListener(e -> {
			try {
				do {
					logger.info("Asking for username.");
					username = (String) JOptionPane
							.showInputDialog(
									window,
									"Please type your nickname to be showed on the scoreboard later!\n",
									"Type your nickname",
									JOptionPane.QUESTION_MESSAGE);
				} while (username.isEmpty());
				logger.debug("Username is: {}", username);
				window.getContentPane().add(new Main());
				timer = 0.0;
				inGame = true;
			} catch (Exception e1) {
				;
			}
		});

		save_menuItem.addActionListener(e -> {
			if (!save)
				save = true;
		});

		load_menuItem.addActionListener(e -> {
			if (!load)
				load = true;
		});

		quit.addActionListener(e -> {
			logger.warn("Quitting the game.");
			JOptionPane.showMessageDialog(window, "Thanks for playing!",
					"Good bye", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		});

		hallOfFame
				.addActionListener(e -> {
					try {
						List<Eredmeny> li = new ArrayList<Eredmeny>();

						DBConnection dbc = new DBConnection();
						logger.info("DBConnection created.");

						logger.info("Trying to connect to the database...");
						dbc.connect(jdbcUrl);
						logger.info("Connected to the database.");

						logger.info("Executing a query.");
						try (ResultSet rset = dbc
								.executeQuery("SELECT * FROM SNAKE ORDER BY HOSSZ DESC")) {
							while (rset.next()) {
								Eredmeny tmp = new Eredmeny(rset
										.getString("NEV"),
										rset.getInt("HOSSZ"), rset
												.getInt("IDO"), rset
												.getTimestamp("DATUM"));
								li.add(tmp);
							}
						}
						logger.info("Query executed.");
						logger.info("Creating the info panel.");

						StringBuilder sb = new StringBuilder();
						sb.append("<html><body><ol>");
						for (Eredmeny eit : li) {
							sb.append("<li style='font-family:Andalus; font-size: 14px;'>");
							sb.append(eit.toString());
						}
						sb.append("</ol></body></html>");

						JScrollPane sp = new JScrollPane(new JLabel(sb
								.toString()));
						sp.setPreferredSize(new Dimension(600, 500));
						JOptionPane.showMessageDialog(window, sp);

					} catch (Exception e1) {
						logger.error("DBConnection failed.");
						e1.printStackTrace();
					}

				});

		stats.add(hallOfFame);

		game.add(new_game);
		game.addSeparator();
		game.add(save_menuItem);
		game.add(load_menuItem);
		game.addSeparator();
		game.add(quit);

		window.setJMenuBar(menuBar);
		logger.info("Menus added.");
	}

	/**
	 * Initializes and launches the application.
	 * 
	 * @param args
	 *            The arguments provided.
	 * @throws InterruptedException
	 *             If the thread is interrupted.
	 * @throws IOException
	 *             When I/O failure occurs.
	 * @throws FileNotFoundException
	 *             When the dbconnection.properties is not found.
	 * @throws SQLException
	 *             When the SQL connection fails.
	 */
	public static void main(String[] args) throws InterruptedException,
			FileNotFoundException, IOException, SQLException {
		logger.info("Main started.");

		DBConnection dbc = new DBConnection();
		logger.info("DBConnection created.");

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setSize(window_size + 3, window_size + 47);
		window.setLocationRelativeTo(null);
		window.addKeyListener(new Tadapter());
		logger.info("The JFrame window is created.");
		addMenus(window);
		logger.info("Menus added to 'window'.");

		CoreMethods.init(board);
		logger.info("Back in the Main from the init() method.");

		Snake s = new Snake(window_size / 20 + 1, window_size / 20, 1);

		logger.info("Back in the Main from creating the Snake object.");

		Thread snakeThread = new Thread(s);
		snakeThread.setName("Snake");

		logger.info("'Snake' thread created.");

		logger.info("While loop starting...");

		while (true) {
			if (inGame) {
				if (!pause) {
					timer += 0.1;

					window.setTitle(String.format(
							"Time: %.1fs, Size: %d, Name: %s", timer,
							s.getLen(), username));
					try {
						// |--------------|
						// | MAGIC HAPPENS|
						// |--------------|
						snakeThread.run();

					} catch (Exception e) {
						snakeThread.interrupt();
						logger.warn("The Snake collided with something.");
						inGame = false;
						
						logger.info("Trying to connect to the database...");
						dbc.connect(jdbcUrl);
						logger.info("Connected to the database.");
						logger.info("Trying to insert the stats into the database...");
						dbc.executeUpdate(
								"INSERT INTO SNAKE (NEV, HOSSZ, IDO, DATUM) VALUES(?, ?, ?, ?)",
								username, s.getLen(), timer.intValue());
						logger.info("Stats inserted succesfully into the databse.");

						logger.info("Closing the connection...");
						dbc.close();
						logger.info("Connection closed.");

						window.setTitle(String.format("Game Over. Length: %d",
								s.getLen()));
						logger.info("Game over. Title updated.");

						logger.info("Calling the 'clear()' method of the Snake object.");
						s.clear();
					}
				} else {
					if (save) {
						Thread saveThread = new Thread(
								() -> {
									try {
										logger.info("Saving started.");
										DocumentBuilderFactory docFactory = DocumentBuilderFactory
												.newInstance();
										DocumentBuilder docBuilder = docFactory
												.newDocumentBuilder();

										Document doc = docBuilder.newDocument();
										Element rootElement = doc
												.createElement("gamestate");
										doc.appendChild(rootElement);

										rootElement.setAttribute(
												"leftDirection",
												leftDirection.toString());
										rootElement.setAttribute(
												"rightDirection",
												rightDirection.toString());
										rootElement.setAttribute("upDirection",
												upDirection.toString());
										rootElement.setAttribute(
												"downDirection",
												downDirection.toString());
										rootElement.setAttribute("username",
												username);
										rootElement.setAttribute("timer",
												timer.toString());

										Element sfood = doc
												.createElement("food");
										rootElement.appendChild(sfood);
										sfood.setAttribute("id", "food");
										sfood.setAttribute("x",
												food[0].toString());
										sfood.setAttribute("y",
												food[1].toString());

										Element snake = doc
												.createElement("snake");
										rootElement.appendChild(snake);

										snake.setAttribute("id", "snake");
										snake.setAttribute("headX", s.getX()
												.toString());
										snake.setAttribute("headY", s.getY()
												.toString());
										snake.setAttribute("length", s.getLen()
												.toString());

										Element sBody = doc
												.createElement("body");
										snake.appendChild(sBody);

										for (int i = 0; i < s.body.size(); i++) {
											Element tmp = doc
													.createElement("body_" + i);
											sBody.appendChild(tmp);
											tmp.setAttribute("x",
													s.body.get(i)[0].toString());
											tmp.setAttribute("y",
													s.body.get(i)[1].toString());

										}

										logger.info("DOMsource created.");

										TransformerFactory transformerFactory = TransformerFactory
												.newInstance();
										Transformer transformer = transformerFactory
												.newTransformer();
										transformer.setOutputProperty(
												OutputKeys.INDENT, "yes");
										transformer
												.setOutputProperty(
														"{http://xml.apache.org/xslt}indent-amount",
														"4");
										DOMSource source = new DOMSource(doc);
										StreamResult result = new StreamResult(
												new File(
														"src/main/resources/GameState.xml"));

										transformer.transform(source, result);
										logger.info("Game saved.");

										JOptionPane
												.showMessageDialog(window,
														"Game saved as 'GameState.xml'.");

									} catch (Exception e1) {
										logger.error("Saving failed.");
										e1.printStackTrace();
									}
								});

						saveThread.setName("Snake-saving");
						saveThread.start();
						save = false;

					}
					if (load) {
						Thread loadThread = new Thread(
								() -> {
									try {
										logger.info("Loading the game.");

										File XMLFile = new File(
												"src/main/resources/GameState.xml");
										DocumentBuilderFactory dbFactory = DocumentBuilderFactory
												.newInstance();
										DocumentBuilder dBuilder = dbFactory
												.newDocumentBuilder();
										Document doc = dBuilder.parse(XMLFile);
										logger.info("Parsing the XML file.");
										doc.getDocumentElement().normalize();

										Element root = doc.getDocumentElement();

										logger.debug("Root element: {}", doc
												.getDocumentElement()
												.getNodeName());

										downDirection = Boolean.valueOf(root
												.getAttribute("downDirection"));
										leftDirection = Boolean.valueOf(root
												.getAttribute("leftDirection"));
										rightDirection = Boolean.valueOf(root
												.getAttribute("rightDirection"));
										upDirection = Boolean.valueOf(root
												.getAttribute("upDirection"));
										timer = Double.parseDouble(root
												.getAttribute("timer"));
										username = root
												.getAttribute("username");

										logger.debug(
												"dD:{}, lD:{}, rD:{}, uD:{}, timer:{}, username:{}",
												downDirection, leftDirection,
												rightDirection, upDirection,
												timer, username);

										logger.debug("Main variables loaded.");

										Element foodElement = (Element) doc
												.getElementsByTagName("food")
												.item(0);
										food[0] = Integer.parseInt(foodElement
												.getAttribute("x"));
										food[1] = Integer.parseInt(foodElement
												.getAttribute("y"));
										logger.debug("Food: 'x':{}, 'y':{}",
												food[0], food[1]);

										Element snakeElement = (Element) doc
												.getElementsByTagName("snake")
												.item(0);
										s.setX(Integer.parseInt(snakeElement
												.getAttribute("headX")));
										s.setY(Integer.parseInt(snakeElement
												.getAttribute("headY")));
										s.setLen(Integer.parseInt(snakeElement
												.getAttribute("length")));

										logger.debug(
												"Snake: 'x':{}, 'y':{}, 'len':{}",
												s.getX(), s.getY(), s.len);

										ArrayList<Integer[]> tmp_li = new ArrayList<Integer[]>();
										for (int i = 0; i < s.len; i++) {
											Integer[] tmp = new Integer[2];
											String it = "body_" + i;
											Element eElement = (Element) doc
													.getElementsByTagName(it)
													.item(0);

											tmp[0] = Integer.parseInt(eElement
													.getAttribute("x"));
											tmp[1] = Integer.parseInt(eElement
													.getAttribute("y"));

											tmp_li.add(tmp);
										}

										s.body = tmp_li;
										logger.debug("Game loaded.");
									} catch (Exception ex) {
										logger.error("Loading failed.");
										ex.printStackTrace();
									}

									JOptionPane
											.showMessageDialog(window,
													"Game loaded from the 'GameState.xml'.");

								});

						loadThread.setName("Snake-loading");
						loadThread.start();

						load = false;
					}
				}

			}
			Thread.sleep(100);
			window.setVisible(true);
			window.repaint();
		}
	}
}
