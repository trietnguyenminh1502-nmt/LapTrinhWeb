package vn.iotstar.config;

import java.io.IOException; // Cần thiết cho signature của getConnection()
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; // Cần thiết để bắt lỗi
import java.util.logging.Level; // Cần thiết cho Logger
import java.util.logging.Logger; // Cần thiết cho Logger

public class DBMySQLConnect {
	private static String DB_URL = "jdbc:mysql://localhost:3306/laptrinhwebct4st6";
    private static String USER_NAME = "root";
    private static String PASSWORD = "Triet1502@";
    private static Connection con;

    public static Connection getConnection() throws IOException {
        con = null;
        try {
            // driver register
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = ((Connection) DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD));
        } catch (SQLException ex) {
            Logger.getLogger(DBMySQLConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (con);
    }

    public static void main(String[] args) {
        try {
            Connection c = getConnection();
            if (c == null) {
                System.out.println("KẾT NỐI KHÔNG THÀNH CÔNG");
            } else {
                System.out.println("KẾT NỐI THÀNH CÔNG");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
