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

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author NuTeeE
 *
 *         Class for storing the result from the queries.
 */
public class Eredmeny {
	
	/**
	 * The Logback logger of the {@code Eredmeny} class.
	 */
	private static Logger logger = LoggerFactory.getLogger(Eredmeny.class);
	
	/**
	 * The username.
	 */
	private String name;
	/**
	 * The length of the snake.
	 */
	private Integer len;
	/**
	 * The game's time.
	 */
	private Integer time;
	/**
	 * The Date of the upload.
	 */
	private Timestamp date;

	/**
	 * Constructor of the {@code Eredmeny} class.
	 * 
	 * @param name
	 *            The username.
	 * @param len
	 *            The length of the snake.
	 * @param time
	 *            The game's time.
	 * @param date
	 *            The Date of the upload.
	 */
	public Eredmeny(String name, Integer len, Integer time, Timestamp date) {
		logger.info("Creating 'Eredmeny' object.");
		this.name = name;
		this.len = len;
		this.time = time;
		this.date = date;
	}

	/**
	 * Returns the username.
	 * 
	 * @return The username.
	 */
	public String getName() {
		return name;
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
	 * Returns the game's time.
	 * 
	 * @return The game's time.
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * Returns the Date of the upload.
	 * 
	 * @return The Date of the upload.
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Returns the {@code Eredmeny} object in a formatted way.
	 * 
	 * @return the {@code Eredmeny} object in a formatted way.
	 */
	@Override
	public String toString() {
		return name + "   " + len + "   " + time + "sec   "
				+ date.toString().substring(0, 19);
	}

	/**
	 * Checks if the object as a parameter is the same as {@code this} object.
	 * 
	 * @param obj
	 *            The object to compare.
	 * @return True if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Eredmeny other = (Eredmeny) obj;

		if (this.name == other.name && this.len == other.len
				&& this.time == other.time)
			return true;
		else
			return false;
	}

}
