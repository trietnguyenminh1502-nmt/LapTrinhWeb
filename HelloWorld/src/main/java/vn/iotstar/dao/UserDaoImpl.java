package vn.iotstar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import vn.iotstar.model.User;
import vn.iotstar.config.DBSQLServerConnect; // Giả sử đây là lớp kết nối của bạn (hoặc DBMySQLConnect)

public class UserDaoImpl implements userDao {

    // Không cần khai báo các biến conn, ps, rs là thành viên của lớp
    // Chúng nên được khai báo cục bộ trong phương thức và dùng try-with-resources

    public User get(String username) {
        // Sử dụng tên bảng chuẩn, thay vì [User] có thể gây nhầm lẫn nếu không phải SQL Server
        // Tuy nhiên, tôi sẽ giữ lại [User] vì nó có thể là tên bảng của bạn
        String sql = "SELECT * FROM [User] WHERE username = ?";
        
        // Sử dụng try-with-resources để tự động đóng Connection, PreparedStatement, và ResultSet
        try (Connection conn = DBSQLServerConnect.getConnection(); // Sử dụng lớp kết nối của bạn
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Thiết lập tham số
            ps.setString(1, username);
            
            // Thực thi truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setUserName(rs.getString("username"));
            
                    user.setPassWord(rs.getString("password"));
               
                    
                    // Kiểm tra và chuyển đổi an toàn hơn
                    String roleIdStr = rs.getString("roleid");
                    if (roleIdStr != null) {
                        user.setRoleid(Integer.parseInt(roleIdStr));
                    }
                    
                    user.setPhone(rs.getString("phone"));
                    user.setCreatedDate(rs.getDate("createdDate"));
                    
                    return user;
                }
            } // rs tự động đóng
        } catch (Exception e) {
            // Nên sử dụng Logger hoặc log cho ứng dụng thực tế
            e.printStackTrace(); 
        }
        return null;
    }
    
    // Thêm các phương thức khác của UserDao tại đây
    
}