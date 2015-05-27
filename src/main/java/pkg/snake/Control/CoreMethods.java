package pkg.snake.Control;

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

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pkg.snake.Model.Cell;
import pkg.snake.View.Main;

/**
 * The core methods used in the program.
 */
public class CoreMethods {

	/**
	 * The Logback logger of the {@code CoreMethods} class.
	 */
	private static Logger logger = LoggerFactory.getLogger(CoreMethods.class);

	/**
	 * Initializes the board.
	 * 
	 * @param board
	 *            The board to initialize.
	 */
	public static void init(Cell[][] board) {

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = new Cell(0);

		logger.info("Board initialized.");

		createFood(board);
	}

	/**
	 * Creates the food.
	 * 
	 * @param board
	 *            The board to be used.
	 * @return True if the food is created.
	 */
	public static Boolean createFood(Cell[][] board) {
		Random r = new Random();

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].getValue() == 2)
					board[i][j].setValue(0);
			}

		do {
			Main.food[0] = r.nextInt(Main.window_size / 10 - 1);
			Main.food[1] = r.nextInt(Main.window_size / 10 - 1);
		} while (board[Main.food[0]][Main.food[1]].getValue() == 1);

		board[Main.food[0]][Main.food[1]].setValue(2);
		logger.debug(
				"The new food is created at this position: 'x':{}, 'y':{}.",
				Main.food[0], Main.food[1]);

		return true;
	}

	/**
	 * Returns the amount of foods on the board.
	 * 
	 * @param board
	 *            The board to be used.
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

}
