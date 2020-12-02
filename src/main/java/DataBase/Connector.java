package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {

    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/";
    private static final String dbName = "random";
    private static final String dbUsername = "root";
    private static final String dbPassword = "Osama123";
    private static Connection conn;

    public static void main(String[] args) {
        getConnection();
        closeConnection();
    }

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(jdbcDriver);
                conn = DriverManager.getConnection(dbUrl + dbName, dbUsername, dbPassword);
                System.out.println("Connected database successfully...");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        System.out.println("Goodbye!");
    }

    public static void createPuls() {
        try {
            Statement statement = Connector.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE dinfaaaar (Patient_id INT," +
                    "Puls_measurements DOUBLE,Puls_time timestamp(3))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePuls() {
        try {
            Statement statement = Connector.getConnection().createStatement();
            statement.executeUpdate("DROP TABLE dinfaaaar");
            Connector.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

