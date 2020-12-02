package DataBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class userCreator {
    private static InputStreamReader isr = new InputStreamReader(System.in);
    private static BufferedReader br = new BufferedReader(isr);
    private static MariaDBConnector mariaDBConnector = new MariaDBConnector();
    private static userDTO userDTO = new userDTO();
    private static String cpr;
    private static String paswd;

    public static void main(String[] args) {
        inserValues();
        mariaDBConnector.getConnection();
        createUser();
    }

    public static void inserValues() {
        try {
            System.out.println("Type a Cpr: ");
            cpr = br.readLine();
            System.out.println("Type a password: ");
            paswd = br.readLine();
            userDTO.setCpr(cpr);
            userDTO.setPaswd(paswd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createUser() {
        try {
            String sql = "INSERT INTO Gruppe5.loginInfo (cpr,paswd) VALUES (?,?)";
            PreparedStatement preparedStatement = mariaDBConnector.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, userDTO.getCpr());
            preparedStatement.setString(2, userDTO.getPaswd());
            preparedStatement.execute();
            System.out.println("User created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
