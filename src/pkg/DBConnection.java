package pkg;

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

/**
 * 
 * A wrapper class for the jdbc operations.
 *
 */
public class DBConnection {
	private Properties prop;
	private Connection conn;
	private Statement stmt;
	private PreparedStatement prepstmt;

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public DBConnection() throws FileNotFoundException, IOException,
			SQLException {
		Properties prop = new Properties();
		prop.load(new FileInputStream("src/resources/dbconnection.properties"));

	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void connect(String jdbcUrl) throws SQLException {
		conn = DriverManager.getConnection(jdbcUrl, "H_NUAUAW", "blablabla");
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		conn.close();
	}

	/**
	 * 
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String statement) throws SQLException {
		stmt = conn.createStatement();
		return stmt.executeQuery(statement);
	}

	/**
	 * 
	 * @param statement
	 * @throws SQLException
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
