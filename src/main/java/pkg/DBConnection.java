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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * A wrapper class for the jdbc operations.
 *
 */
public class DBConnection {

	/**
	 * The Logback logger of the <code>DBConnection class</code>
	 */
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);

	private Properties prop;
	private Connection conn;
	private Statement stmt;
	private PreparedStatement prepstmt;

	/**
	 * Creates the <code>DBConnection</code> wrapper class.
	 * 
	 * @throws FileNotFoundException
	 *             When the dbconnection.properties file is not found.
	 * @throws IOException
	 *             When something occurs during reading the file.
	 */
	public DBConnection() throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream("src/main/resources/dbconnection.properties"));

	}

	/**
	 * Connects to the Database.
	 * 
	 * @param jdbcUrl
	 *            The JDBC connection URL.
	 * @throws SQLException
	 *             When the driver can't connect to the database.
	 * @return The {@link Connection} object.
	 */
	public Connection connect(String jdbcUrl) throws SQLException {
		conn = DriverManager.getConnection(jdbcUrl, "H_NUAUAW", "blablabla");
		return conn;
	}

	/**
	 * 
	 * @throws SQLException
	 *             When the driver can't connect to the database.
	 */
	public void close() throws SQLException {
		conn.close();
	}

	/**
	 * 
	 * @param statement
	 *            The statement for the query.
	 * @return The <code>ResultSet</code> set.
	 * @throws SQLException
	 *             When the query failed.
	 */
	public ResultSet executeQuery(String statement) throws SQLException {
		stmt = conn.createStatement();
		return stmt.executeQuery(statement);
	}

	/**
	 * 
	 * @param statement
	 *            The statement for the query.
	 * @param username
	 *            The name to be inserted into the table with.
	 * @param length
	 *            The Snake's length.
	 * @param time
	 *            The game's time.
	 * @throws SQLException
	 *             When the execution fails.
	 */
	public void executeUpdate(String statement, String username,
			Integer length, Integer time) throws SQLException {
		prepstmt = conn.prepareStatement(statement);
		prepstmt.setString(1, username);
		prepstmt.setInt(2, length);
		prepstmt.setInt(3, time);
		prepstmt.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));

		prepstmt.executeUpdate();

	}

}
