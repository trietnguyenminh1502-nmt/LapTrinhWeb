package vn.iotstar.config;

import java.io.IOException; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level; 
import java.util.logging.Logger;

import vn.iotstar.model.Category; 

public class DBSQLServerConnect {
    
    private static final String SERVER_NAME = "LAPTOP-OTKDTN6P\\SQLSERVER";
    private static final String PORT = "1433";
    private static final String DATABASE_NAME = "laptrinhwebct4st6"; 
    
    private static final String DB_URL = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT + ";databaseName=" + DATABASE_NAME + ";encrypt=false;trustServerCertificate=true;"; 
    
    private static final String USER_NAME = "Triet"; 
    private static final String PASSWORD = "Triet1502@"; 

    public static Connection getConnection() throws IOException { 
        Connection con = null;
        try {
            // Không cần DriverManager.registerDriver() với JDBC hiện đại
            
            // Lấy Connection
            con = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            // Ghi lại lỗi kết nối
            Logger.getLogger(DBSQLServerConnect.class.getName()).log(Level.SEVERE, "LỖI KẾT NỐI SQL SERVER!", ex);
        }
        return con;
    }

    public static void main(String[] args) {
        // Sử dụng try-with-resources để tự động đóng Connection
        try (Connection c = getConnection()) {
            if (c == null) {
                System.out.println("KẾT NỐI SQL SERVER KHÔNG THÀNH CÔNG");
            } else {
                System.out.println("KẾT NỐI SQL SERVER THÀNH CÔNG");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	public void edit1(Category category) {
		// TODO Auto-generated method stub
		
	}

	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insert(Category category) {
		// TODO Auto-generated method stub
		
	}

	public Category get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void edit(Category category) {
		// TODO Auto-generated method stub
		
	}

	public Category get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Category> search(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
}