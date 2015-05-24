package pkg;

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

/**
 * 
 * @author NuTeeE
 *
 *         Class for storing the result from the queries.
 */
public class Eredmeny {
	private String name;
	private Integer len;
	private Integer time;
	private Timestamp date;

	public Eredmeny(String name, Integer len, Integer time, Timestamp date) {
		this.name = name;
		this.len = len;
		this.time = time;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public Integer getLen() {
		return len;
	}

	public Integer getTime() {
		return time;
	}

	public Timestamp getDate() {
		return date;
	}

	@Override
	public String toString() {
		return name + "   " + len + "   " + time + "sec   "
				+ date.toString().substring(0, 19);
	}

}
