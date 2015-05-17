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
		prop.load(new FileInputStream("src/resources/dbconnection.properties"));

	}

	/**
	 * Connects to the Database.
	 * 
	 * @throws SQLException
	 *             When the driver can't connect to the database.
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
