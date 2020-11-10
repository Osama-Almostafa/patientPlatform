package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
    private static Connection conn;

    private Connector() {
    }

    public static Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/random?" +
                       //"user=Brate&password=bror");
                conn = DriverManager.getConnection("jdbc:mysql://10.16.238.59:3306/eri?" +
                        "user=tizi&password=8210");
                System.out.println("Connection established");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void colseConn() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createPuls() {
        try {
            Statement statement = Connector.getConn().createStatement();
            statement.executeUpdate("CREATE TABLE dinfaaaar (Patient_id INT," +
                    "Puls_measurements DOUBLE,Puls_time timestamp(3))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePuls() {
        try {
            Statement statement = Connector.getConn().createStatement();
            statement.executeUpdate("DROP TABLE PULS");
            Connector.getConn().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getConn();
        deletePuls();
        createPuls();
    }
}

