package pkg.snake.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/**
 * Class for the cells.
 */
public class Cell {
	
	/**
	 * The Logback logger of the {@code Cell} class.
	 */
	private static Logger logger = LoggerFactory.getLogger(Cell.class);
	
	/**
	 * The value of the Cell.
	 * 
	 * If the value is: 0 - the cell is empty; 1 - it has the part of the snake;
	 * 2 - it contains a food.
	 */
	private int value;

	/**
	 * Constructor of this object.
	 * 
	 * @param value
	 *            Contains the value of that cell.
	 */
	public Cell(int value) {
//		logger.info("Cell created.");
		this.value = value;
	}

	/**
	 * Returns the value of this object.
	 * 
	 * @return The value of this object.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value of this object.
	 * 
	 * @param value
	 *            The value of this object.
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
