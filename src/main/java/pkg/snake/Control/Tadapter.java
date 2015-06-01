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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pkg.snake.View.Main;

/**
 * Extended {@link KeyAdapter} class for listening to pressed keys (directions).
 */
public class Tadapter extends KeyAdapter {

	/**
	 * The Logback logger of the {@code Tadapter} class.
	 */
	private static Logger logger = LoggerFactory.getLogger(Tadapter.class);

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
			Main.pause = !(Main.pause);
			logger.debug("'pause' value: {}", Main.pause);
		}

		if ((key == KeyEvent.VK_LEFT) && (!Main.rightDirection)) {
			logger.debug("Switching direction to left");
			Main.leftDirection = true;
			Main.upDirection = false;
			Main.downDirection = false;
		}

		if ((key == KeyEvent.VK_RIGHT) && (!Main.leftDirection)) {
			logger.debug("Switching direction to right");
			Main.rightDirection = true;
			Main.upDirection = false;
			Main.downDirection = false;
		}

		if ((key == KeyEvent.VK_UP) && (!Main.downDirection)) {
			logger.debug("Switching direction to up");
			Main.upDirection = true;
			Main.leftDirection = false;
			Main.rightDirection = false;
		}

		if ((key == KeyEvent.VK_DOWN) && (!Main.upDirection)) {
			logger.debug("Switching direction to down");
			Main.downDirection = true;
			Main.leftDirection = false;
			Main.rightDirection = false;
		}
	}
}