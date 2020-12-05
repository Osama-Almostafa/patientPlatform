package CommonGatewayInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CGIDbValidate {
    public CGIDbValidate() {
    }

    private static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    private static final String dbUrl = "jdbc:mariadb://su7.eduhost.dk:3306/";
    private static final String dbName = "Gruppe5?";
    private static final String dbUsername = "osama";
    private static final String dbPassword = "8210";
    private static Connection connection = null;
    static String inputfraCGI = null;
    static String[] data;
    static String[] clientResponse;
    static String cprTilDb;
    static String paswdTilDb;
    static String cprSql = null;
    static String paswdSql = null;
    static Time timeSql = null;
    static Date dateSql = null;
    static String hospitalSql = null;
    static String departmentsSql = null;

    public static void main(String[] args) {
        showHead();
        getConnection();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            data = new String[]{in.readLine()};
            inputfraCGI = data[0];
            clientResponse = inputfraCGI.split("&");
            String[] cpr;
            cpr = clientResponse[0].split("=");
            cprTilDb = cpr[1];
            String[] paswd;
            paswd = clientResponse[1].split("=");
            paswdTilDb = paswd[1];
            if (findUser(cprTilDb, paswdTilDb)) {
                getAppointment();
            }
        } catch (IOException ioe) {
            System.out.println("<P>IOException reading POST data: " + ioe + "</P>");
        }
        showTail();
    }


    private static void showHead() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>The CGIpost application</TITLE>");
        System.out.println("    <link rel=\"stylesheet\" type=\"text/css\" href=\"/home.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\">\n");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        System.out.println("</HEAD>");
        System.out.println("<body>\n" +
                "<header>\n" +
                "    <div>\n" +
                "        <p>Min Sundhedsplatform</p>\n" +
                "        <ul>\n" +
                "            <li><a href=\"/Bestil tid.html\">Bestil tid</a></li>\n" +
                "            <li><a href=\"/index.html\">Log ud</a></li>\n" +
                "        </ul>\n" +
                "    </div>\n" +
                "</header>\n" +
                "<table>\n" +
                "    <caption><b>Kommende tider</b></caption>\n" +
                "    <tr>\n" +
                "        <th><b>Hospital</b></th>\n" +
                "        <th><b>Afdeling</b></th>\n" +
                "        <th><b>Tidspunkt</b></th>\n" +
                "        <th><b>Dato</b></th>\n" +
                "    </tr>\n");
    }

    private static void showBody() {
        System.out.println(
                "    <tr>\n" +
                "        <td>" + hospitalSql + "</td>\n" +
                "        <td>" + departmentsSql + "</td>\n" +
                "        <td>" + timeSql + "</td>\n" +
                "        <td>" + dateSql + "</td>\n" +
                "    </tr>\n");
    }

    private static void showTail() {
        System.out.println("</table>\n" + "</body>\n" + "</HTML>");
    }

    private static void getAppointment() {
        try {
            String sql = "select tim,dat,hospital,departments from Gruppe5.aftaler where cpr= '" +
                    cprTilDb + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                timeSql = rs.getTime("tim");
                dateSql = rs.getDate("dat");
                hospitalSql = rs.getString("hospital");
                departmentsSql = rs.getString("departments");
                showBody();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(jdbcDriver);
                connection = DriverManager.getConnection(dbUrl + dbName, dbUsername, dbPassword);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static boolean findUser(String cprTilDb, String paswdTilDb) {
        try {
            String sql = "select * from Gruppe5.loginInfo where cpr= " + "'" + cprTilDb + "'" + "and paswd ="
                    + "'" + paswdTilDb + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            cprSql = rs.getString("cpr");
            paswdSql = rs.getString("paswd");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cprTilDb.equals(cprSql) && paswdTilDb.equals(paswdSql);
    }
}
