package DataBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class appointments {
    private static InputStreamReader isr = new InputStreamReader(System.in);
    private static BufferedReader br = new BufferedReader(isr);
    private static MariaDBConnector mariaDBConnector = new MariaDBConnector();
    private static appointmentDTO appointmentDTO = new appointmentDTO();
    private static String cpr;
    private static String hospital;
    private static String department;
    private static String address;

    public static void main(String[] args) {
        inserValues();
        mariaDBConnector.getConnection();
        createAppointement();
    }

    public static void inserValues() {
        try {
            System.out.println("Type a Cpr: ");
            cpr = br.readLine();
            System.out.println("Type a hospital: ");
            hospital = br.readLine();
            System.out.println("Type a department: ");
            department = br.readLine();
            System.out.println("Type an address: ");
            address = br.readLine();
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
            appointmentDTO.setCpr(cpr);
            appointmentDTO.setTimestamp(sqlTime);
            appointmentDTO.setDate(sqlDate);
            appointmentDTO.setHospital(hospital);
            appointmentDTO.setDepartment(department);
            appointmentDTO.setAddress(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createAppointement() {
        try {
            String sql = "INSERT INTO Gruppe5.aftaler (cpr,time,date,hospital,departments,adress) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = mariaDBConnector.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, appointmentDTO.getCpr());
            preparedStatement.setTimestamp(2, appointmentDTO.getTimestamp());
            preparedStatement.setDate(3, appointmentDTO.getDate());
            preparedStatement.setString(4, appointmentDTO.getHospital());
            preparedStatement.setString(5, appointmentDTO.getDepartment());
            preparedStatement.setString(6, appointmentDTO.getAddress());
            preparedStatement.execute();
            System.out.println("User created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
