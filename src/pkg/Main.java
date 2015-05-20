package pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

<<<<<<< HEAD



=======
>>>>>>> 749d24f4586a9debf779d128fbc9494f191baf1d
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

<<<<<<< HEAD



=======
>>>>>>> 749d24f4586a9debf779d128fbc9494f191baf1d
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author NuTeeE
 */
public class Main extends JComponent {

	/**
	 * The Logback logger of the <code>Main</code> class
	 */
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * The JDBC URL
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
	private static final Cell[][] board = new Cell[window_size / 10][window_size / 10];

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

	/**
	 * The class of the controlled snake.
	 */
	public static class Snake {
		private int x, y, len;
		private ArrayList<Integer[]> body;

		/**
		 * Constructor of this object.
		 * 
		 * @param x
		 *            The x value of the snake.
		 * @param y
		 *            The y value of the snake.
		 */
		public Snake(int x, int y) {
			this.x = x;
			this.y = y;
			body = new ArrayList<Integer[]>();
			body.add(new Integer[] { this.x, this.y });
			logger.debug(
					"Snake object created with these parameters: 'x':{}, 'y':{}",
					x, y);
		}

		/**
		 * Constructor of this object.
		 * 
		 * @param x
		 *            The x value of the snake.
		 * @param y
		 *            The y value of the snake.
		 * @param len
		 *            The length of the snake.
		 */
		public Snake(int x, int y, int len) {
			this.x = x;
			this.y = y;
			this.len = len;
			body = new ArrayList<Integer[]>();
			for (int i = 0; i < len; i++)
				body.add(new Integer[] { x - i, y });

			logger.debug(
					"Snake object created with these parameters: 'x':{}, 'y':{}, 'len':{}",
					x, y, len);
		}

		public ArrayList<Integer[]> getBody() {
			return body;
		}

		public void setBody(ArrayList<Integer[]> body) {
			this.body = body;
		}

		/**
		 * Returns the length of the snake.
		 * 
		 * @return The length of the snake.
		 */
		public Integer getLen() {
			return len;
		}

		/**
		 * Sets the length of the snake.
		 * 
		 * @param len
		 *            The length of the snake.
		 */
		public void setLen(int len) {
			this.len = len;
		}

		/**
		 * Returns the x value of the snake.
		 * 
		 * @return The x value of the snake.
		 */
		public Integer getX() {
			return x;
		}

		/**
		 * Sets the x value of the snake.
		 * 
		 * @param x
		 *            The x value of the snake.
		 */
		public void setX(int x) {
			this.x = x;
		}

		/**
		 * Returns the y value of the snake.
		 * 
		 * @return The y value of the snake.
		 */
		public Integer getY() {
			return y;
		}

		/**
		 * Sets the y value of the snake.
		 * 
		 * @param y
		 *            The y value of the snake.
		 */
		public void setY(int y) {
			this.y = y;
		}

		/**
		 * Moves the snake in a direction.
		 * 
		 * @throws Exception
		 *             When the Snake collides with itself.
		 */
		void move() throws Exception {
			// logger.info("Starting the 'move()' method of the Snake object");

			for (int i = body.size() - 1; i > 0; i--)
				body.set(i, this.body.get(i - 1));

			if (leftDirection) {
				if (board[body.get(0)[0] - 1][body.get(0)[1]].getValue() == 1)
					throw new Exception("The Snake collided with itself.");
				body.set(0,
						new Integer[] { body.get(0)[0] - 1, body.get(0)[1] });
			} else if (rightDirection) {
				if (board[body.get(0)[0] + 1][body.get(0)[1]].getValue() == 1)
					throw new Exception("The Snake collided with itself.");
				body.set(0,
						new Integer[] { body.get(0)[0] + 1, body.get(0)[1] });
			} else if (upDirection) {
				if (board[body.get(0)[0]][body.get(0)[1] - 1].getValue() == 1)
					throw new Exception("The Snake collided with itself.");
				body.set(0,
						new Integer[] { body.get(0)[0], body.get(0)[1] - 1 });
			} else if (downDirection) {
				if (board[body.get(0)[0]][body.get(0)[1] + 1].getValue() == 1)
					throw new Exception("The Snake collided with itself.");
				body.set(0,
						new Integer[] { body.get(0)[0], body.get(0)[1] + 1 });
			}
			// logger.info("End of the 'move()' method of the Snake object.");
		}

		/**
		 * Makes the snake eat the food.
		 * 
		 * Occurs when the snake's head is on the food.
		 */
		void eat() {
			logger.info("'eat()' started.");

			int i, j;

			// StringBuilder sb = new StringBuilder();
			// sb.append("[");
			// for (int[] tmp : body) {
			// sb.append("[" + tmp[0] + ", " + tmp[1] + "], ");
			// }
			// sb.append("]");
			//
			// logger.debug("'Body' before: {}", sb);
			if (body.size() > 1) {
				i = body.get(len - 1)[0];
				j = body.get(len - 1)[1];

				i += i - body.get(len - 2)[0];
				j += j - body.get(len - 2)[1];
				// TODO try-catch mert hibát okozhat
				body.add(new Integer[] { i, j });
				logger.debug("New body added at 'x':{}, 'y':{}", i, j);
			} else {
				if (leftDirection) {
					body.add(new Integer[] { body.get(0)[0] + 1, body.get(0)[1] });
				} else if (rightDirection) {
					body.add(new Integer[] { body.get(0)[0] - 1, body.get(0)[1] });
				} else if (upDirection) {
					body.add(new Integer[] { body.get(0)[0], body.get(0)[1] + 1 });
				} else if (downDirection) {
					body.add(new Integer[] { body.get(0)[0], body.get(0)[1] - 1 });
				}
			}

			// sb = new StringBuilder();
			// sb.append("[");
			// for (int[] tmp : body) {
			// sb.append("[" + tmp[0] + ", " + tmp[1] + "], ");
			// }
			// sb.append("]");
			// logger.debug("'Body' after: {}", sb);

			// logger.debug("'len' before eating: {}", len);
			len = body.size();
			logger.debug("'len' value: {}", len);
		}

		/**
		 * Deletes the Snake, and creates a new one.
		 * 
		 * Occurs when the actual game is over.
		 */
		public void clear() {
			logger.info("Getting rid of the old snake, and creating a new one.");
			body.clear();
			body.add(new Integer[] { x, y });
			len = 1;
			rightDirection = true;
			logger.info("New snake created. Returning to Main..");
		}
	}

	/**
	 * Extended <code>KeyAdapter</code> class for listening to pressed keys
	 * (directions).
	 */
	public static class Tadapter extends KeyAdapter {

		/**
		 * Method for listening to pressed keys (directions).
		 * 
		 * @param e
		 *            <code>KeyEvent</code> object storing information about the
		 *            event.
		 */
		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_P) {
				pause = !(pause);
				logger.debug("'pause' value: {}", pause);
			}

			if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
				logger.debug("Switching direction to left");
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
				logger.debug("Switching direction to right");
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_UP) && (!downDirection)) {
				logger.debug("Switching direction to up");
				upDirection = true;
				leftDirection = false;
				rightDirection = false;
			}

			if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
				logger.debug("Switching direction to down");
				downDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
		}
	}

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
					g2d.setColor(new Color(125, 60, 20));
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
	 * Initializes the board.
	 */
	public static void init() {

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = new Cell(0);

		logger.info("Board initialized.");

		createFood(board);
	}

	/**
	 * Creates the food.
	 */
	public static Boolean createFood(Cell[][] board) {
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
		logger.debug(
				"The new food is created at this position: 'x':{}, 'y':{}.",
				food[0], food[1]);
		
		return true;
	}

	/**
	 * Returns the amount of foods on the board.
	 * 
	 * @return The amount of foods on the board.
	 */
	public static Integer amountOfFood(Cell[][] board) {
		int c = 0;
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				if (board[i][j].getValue() == 2)
					c++;
		logger.debug("The amountOfFood method returns: {}", c);
		return c;
	}

	/**
	 * Checks the objects on the board.
	 * 
	 * @param s
	 *            The <code>Snake</code> object.
	 * @throws ArrayIndexOutOfBoundsException
	 *             When the snake collides with the border.
	 */
	public static void checkBoard(Cell[][] board, Snake s)
			throws ArrayIndexOutOfBoundsException {

		// logger.info("'checkBoard()' method started.");
		if (board[s.body.get(0)[0]][s.body.get(0)[1]].getValue() == 2) {
			logger.debug("Eating food at: 'x':{}, 'y':{}", s.body.get(0)[0],
					s.body.get(0)[1]);
			if (amountOfFood(board) == 1)
				s.eat();
			board[s.body.get(0)[0]][s.body.get(0)[1]].setValue(1);
			createFood(board);
		}

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getValue() == 2)
					continue;
				board[i][j].setValue(0);
			}

		for (Integer[] i : s.body) {
			board[i[0]][i[1]].setValue(1);
		}

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

		init();
		logger.info("Back in the Main from the init() method.");

		Snake s = new Snake(window_size / 20 + 1, window_size / 20, 1);
		logger.info("Back in the Main from creating the Snake object.");

		logger.info("While loop starting...");

		while (true) {
			// logger.info("'inGame' value: {}.", inGame);
			if (inGame) {
				// logger.info("'pause' value: {}.", pause);
				if (!pause) {
					timer += 0.1;

					window.setTitle(String.format(
							"Time: %.1fs, Size: %d, Name: %s", timer,
							s.getLen(), username));
					// logger.info("Title updated.");
					try {
						// logger.info("Calling 'checkBoard' method with the Snake object.");
						checkBoard(board, s);
						s.move();
					} catch (Exception e) {
						logger.warn("The Snake collided with something.");
						inGame = false;
						// e.printStackTrace();
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

							rootElement.setAttribute("leftDirection",
									leftDirection.toString());
							rootElement.setAttribute("rightDirection",
									rightDirection.toString());
							rootElement.setAttribute("upDirection",
									upDirection.toString());
							rootElement.setAttribute("downDirection",
									downDirection.toString());
							rootElement.setAttribute("username", username);
							rootElement.setAttribute("timer", timer.toString());

							Element sfood = doc.createElement("food");
							rootElement.appendChild(sfood);
							sfood.setAttribute("id", "food");
							sfood.setAttribute("x", food[0].toString());
							sfood.setAttribute("y", food[1].toString());

							Element snake = doc.createElement("snake");
							rootElement.appendChild(snake);

							snake.setAttribute("id", "snake");
							snake.setAttribute("headX", s.getX().toString());
							snake.setAttribute("headY", s.getY().toString());
							snake.setAttribute("length", s.getLen().toString());

							Element sBody = doc.createElement("body");
							snake.appendChild(sBody);

							for (int i = 0; i < s.body.size(); i++) {
								Element tmp = doc.createElement("body_" + i);
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
							transformer.setOutputProperty(OutputKeys.INDENT,
									"yes");
							transformer
									.setOutputProperty(
											"{http://xml.apache.org/xslt}indent-amount",
											"4");
							DOMSource source = new DOMSource(doc);
							StreamResult result = new StreamResult(new File(
									"src/resources/GameState.xml"));

							transformer.transform(source, result);
							logger.info("Game saved.");

							JOptionPane.showMessageDialog(window,
									"Game saved as 'GameState.xml'.");

							save = false;
						} catch (Exception e1) {
							logger.error("Saving failed.");
							e1.printStackTrace();
						}
					}
					if (load) {
						try {
							logger.info("Loading the game.");

							File XMLFile = new File(
									"src/resources/GameState.xml");
							DocumentBuilderFactory dbFactory = DocumentBuilderFactory
									.newInstance();
							DocumentBuilder dBuilder = dbFactory
									.newDocumentBuilder();
							Document doc = dBuilder.parse(XMLFile);
							logger.info("Parsing the XML file.");
							doc.getDocumentElement().normalize();

							Element root = doc.getDocumentElement();

							logger.debug("Root element: {}", doc
									.getDocumentElement().getNodeName());

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
							username = root.getAttribute("username");

							logger.debug(
									"dD:{}, lD:{}, rD:{}, uD:{}, timer:{}, username:{}",
									downDirection, leftDirection,
									rightDirection, upDirection, timer,
									username);

							logger.debug("Main variables loaded.");

							Element foodElement = (Element) doc
									.getElementsByTagName("food").item(0);
							food[0] = Integer.parseInt(foodElement
									.getAttribute("x"));
							food[1] = Integer.parseInt(foodElement
									.getAttribute("y"));
							logger.debug("Food: 'x':{}, 'y':{}", food[0],
									food[1]);

							Element snakeElement = (Element) doc
									.getElementsByTagName("snake").item(0);
							s.setX(Integer.parseInt(snakeElement
									.getAttribute("headX")));
							s.setY(Integer.parseInt(snakeElement
									.getAttribute("headY")));
							s.setLen(Integer.parseInt(snakeElement
									.getAttribute("length")));

							logger.debug("Snake: 'x':{}, 'y':{}, 'len':{}",
									s.getX(), s.getY(), s.len);

							ArrayList<Integer[]> tmp_li = new ArrayList<Integer[]>();
							for (int i = 0; i < s.len; i++) {
								Integer[] tmp = new Integer[2];
								String it = "body_" + i;
								Element eElement = (Element) doc
										.getElementsByTagName(it).item(0);

								tmp[0] = Integer.parseInt(eElement
										.getAttribute("x"));
								tmp[1] = Integer.parseInt(eElement
										.getAttribute("y"));

								tmp_li.add(tmp);
							}

							s.setBody(tmp_li);
							logger.debug("Game loaded.");
						} catch (Exception ex) {
							logger.error("Loading failed.");
							ex.printStackTrace();
						}

						JOptionPane.showMessageDialog(window,
								"Game loaded from the 'GameState.xml'.");
						load = false;
					}
				}

			}
			// logger.info("Sending the thread to sleep 100 msecs.");
			Thread.sleep(100);
			window.setVisible(true);
			window.repaint();
		}
	}
}
