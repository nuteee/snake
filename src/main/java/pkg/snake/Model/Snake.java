package pkg.snake.Model;

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

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pkg.snake.Control.CoreMethods;
import pkg.snake.Control.SnakeException;
import pkg.snake.View.Main;

/**
 * The class of the controlled snake.
 */
public class Snake implements Runnable{

	/**
	 * The Logback logger of the {@code Snake} class.
	 */
	private static Logger logger = LoggerFactory.getLogger(Snake.class);

	/**
	 * The {@code Snake}'s values.
	 */
	public int x, y, len;

	/**
	 * The {@code Snake}'s body.
	 */
	public ArrayList<Integer[]> body;

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

	// public ArrayList<Integer[]> getBody() {
	// return body;
	// }
	//
	// public void setBody(ArrayList<Integer[]> body) {
	// this.body = body;
	// }

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
	 * @param board
	 *            The board to be moved on.
	 * @throws Exception
	 *             When the Snake collides with itself.
	 */
	public void move(Cell[][] board) throws Exception {
		// logger.info("Starting the 'move()' method of the Snake object");

		for (int i = body.size() - 1; i > 0; i--)
			body.set(i, this.body.get(i - 1));

		if (Main.leftDirection) {
			if (board[body.get(0)[0] - 1][body.get(0)[1]].getValue() == 1)
				throw new Exception("The Snake collided with itself.");
			body.set(0, new Integer[] { body.get(0)[0] - 1, body.get(0)[1] });
		} else if (Main.rightDirection) {
			if (board[body.get(0)[0] + 1][body.get(0)[1]].getValue() == 1)
				throw new Exception("The Snake collided with itself.");
			body.set(0, new Integer[] { body.get(0)[0] + 1, body.get(0)[1] });
		} else if (Main.upDirection) {
			if (board[body.get(0)[0]][body.get(0)[1] - 1].getValue() == 1)
				throw new Exception("The Snake collided with itself.");
			body.set(0, new Integer[] { body.get(0)[0], body.get(0)[1] - 1 });
		} else if (Main.downDirection) {
			if (board[body.get(0)[0]][body.get(0)[1] + 1].getValue() == 1)
				throw new Exception("The Snake collided with itself.");
			body.set(0, new Integer[] { body.get(0)[0], body.get(0)[1] + 1 });
		}
		// logger.info("End of the 'move()' method of the Snake object.");
	}

	/**
	 * Makes the snake eat the food.
	 * 
	 * Occurs when the snake's head is on the food.
	 */
	public void eat() {
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

			body.add(new Integer[] { i, j });
			logger.debug("New body added at 'x':{}, 'y':{}", i, j);
		} else {
			if (Main.leftDirection) {
				body.add(new Integer[] { body.get(0)[0] + 1, body.get(0)[1] });
			} else if (Main.rightDirection) {
				body.add(new Integer[] { body.get(0)[0] - 1, body.get(0)[1] });
			} else if (Main.upDirection) {
				body.add(new Integer[] { body.get(0)[0], body.get(0)[1] + 1 });
			} else if (Main.downDirection) {
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
	 * Checks the objects on the board.
	 * 
	 * @param board
	 *            The board to be used.
	 * @throws ArrayIndexOutOfBoundsException
	 *             When the snake collides with the border.
	 */
	public void checkBoard(Cell[][] board)
			throws ArrayIndexOutOfBoundsException {

		// logger.info("'checkBoard()' method started.");
		if (board[this.body.get(0)[0]][this.body.get(0)[1]].getValue() == 2) {
			logger.debug("Eating food at: 'x':{}, 'y':{}", this.body.get(0)[0],
					this.body.get(0)[1]);
			if (CoreMethods.amountOfFood(board) == 1)
				this.eat();
			board[this.body.get(0)[0]][this.body.get(0)[1]].setValue(1);
			CoreMethods.createFood(board);
		}

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getValue() == 2)
					continue;
				board[i][j].setValue(0);
			}

		for (Integer[] i : this.body) {
			board[i[0]][i[1]].setValue(1);
		}

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
		Main.rightDirection = true;
		logger.info("New snake created. Returning to Main..");
	}

	@Override
	public void run(){
//		logger.info("Snake thread started.");
		checkBoard(Main.board);
		try {
			move(Main.board);
		} catch (Exception e) {
			logger.warn("Snake collided with something.");
			throw new SnakeException(e);
		}
	}
}