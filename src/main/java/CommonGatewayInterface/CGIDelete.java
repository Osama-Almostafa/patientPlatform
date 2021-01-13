package CommonGatewayInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CGIDelete {
    public CGIDelete() {
    }

    private static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    private static final String dbUrl = "jdbc:mariadb://su7.eduhost.dk:3306/";
    private static final String dbName = "Gruppe5?";
    private static final String dbUsername = "osama";
    private static final String dbPassword = "8210";
    private static Connection connection = null;
    static String[] inputfraCGI = null;
    private static String[] data;
    static String cprSql = null;
    static Time timeSql = null;
    static Date dateSql = null;
    static int aftaleId;
    static String hospitalSql = null;
    static String departmentsSql = null;

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            data = new String[]{in.readLine()};
            inputfraCGI = data[0].split("=");
            aftaleId = Integer.parseInt(inputfraCGI[1]);
            getConnection();
            getCpr();
            showHead();
            deleteAftale();
            getAppointment();
            showTail();
        } catch (IOException e) {
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

    private static void getCpr() {
        try {
            String sql = "select cpr from Gruppe5.aftaler where aftaleId =" + aftaleId + "";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            rs.next();
            cprSql = rs.getString("cpr");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAftale() {
        try {
            String sql = "delete from Gruppe5.aftaler where aftaleId=\"" + aftaleId + "\";";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getAppointment() {
        try {
            String sql = "select aftaleId,tim,dat,hospital,departments from Gruppe5.aftaler where cpr= '" +
                    cprSql + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                timeSql = rs.getTime("tim");
                dateSql = rs.getDate("dat");
                hospitalSql = rs.getString("hospital");
                departmentsSql = rs.getString("departments");
                aftaleId = rs.getInt("aftaleId");
                showBody();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                "    <thead>\n" +
                "    <tr>\n" +
                "        <th id=\"hospital\"><b>Hospital</b></th>\n" +
                "        <th id=\"afdeling\"><b>Afdeling</b></th>\n" +
                "        <th id=\"tid\"><b>Tid</b></th>\n" +
                "        <th id=\"dato\"><b>Dato</b></th>\n" +
                "    </tr>\n" +
                "    </thead>\n" +
                "    <tbody>");
    }

    private static void showBody() {
        System.out.println(
                "    <tr>\n" +
                        "        <td>" + hospitalSql + "</td>\n" +
                        "        <td>" + departmentsSql + "</td>\n" +
                        "        <td>" + timeSql + "</td>\n" +
                        "        <td>" + dateSql + "</td>\n" +
                        "        <td>" + aftaleId + "</td>\n" +
                        "        <td>\n" +
                        "            <form action=\"/cgi-bin/CGIDelete\" method=\"post\">\n" +
                        "                <input type=\"text\" name=\"aftaleId\"style=\"width: 35px\">\n" +
                        "                <input type=\"submit\" value=\"slet\"/>\n" +
                        "            </form>\n" +
                        "        </td> "+
                        "    </tr>\n");
    }

    private static void showTail() {
        System.out.println("</tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</HTML>\n");
    }
}
