package group825.vetapp2.database;
import java.sql.*;

public class DatabaseConnection {
    /**
     * Port address of the database
     */
    private static final String url = "jdbc:mysql://localhost:3306/vetapp";

    /**
     * Username for database
     */
    private static final String username = "dummyRootUser";

    /**
     * Password for database
     */
    private static final String password = "password";

    /**
     * Connection to database
     */
    private static Connection connection;

    /**
     * Initializes the connection to the database
     */
    public static void initialize() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the database
     */
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the database connection
     * @return the database connection
     */
    public static Connection getConnection() {
        return connection;
    }
}