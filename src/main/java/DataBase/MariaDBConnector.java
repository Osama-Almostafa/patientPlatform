package DataBase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class MariaDBConnector {

    private static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    private static final String dbUrl = "jdbc:mariadb://su7.eduhost.dk:3306/";
    private static final String dbName = "Gruppe5?";
    private static final String dbUsername = "osama";
    private static final String dbPassword = "8210";
    private static Connection conn;
    private static InputStreamReader isr = new InputStreamReader(System.in);
    private static BufferedReader bf = new BufferedReader(isr);
    private static String cpr;

    public static void main(String[] args) {
        findUser(cpr);
    }

    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(jdbcDriver);
                System.out.println("Connecting to a selected database...");
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

    private static String[] findUser(String cpr) {
        String[] result = new String[2];
        try {
            System.out.println("Type a Cpr: ");
            cpr=bf.readLine();
            String sql = "select * from Gruppe5.loginInfo where cpr=" + "'" + cpr + "'" + "limit 1;";
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);
            while (rs.next()) {
                //rs.getDataType( tal) ; giver os en datatype på den 1, 2 osv. plads -
                // Hvad sker hvis vi forsøger at hente et Int fra String?
                System.out.println("cpr: " + rs.getString("cpr") + "\n" +
                        "pass:" + rs.getString("paswd") + "\n");
                //hvad hvis vi henviser til det ud fra navne fremfor index?
            }
           closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

