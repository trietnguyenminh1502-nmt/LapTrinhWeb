package vn.iotstar.model;

import java.io.Serializable;
import java.util.Date; // Thêm import cho kiểu Date

// Lớp User tuân thủ giao diện Serializable để có thể lưu trữ hoặc truyền qua mạng
public class User implements Serializable {

    // Khuyến nghị: serialVersionUID để kiểm soát phiên bản của lớp
	private static final long serialVersionUID = 1L; 

    // 1. Khai báo các thuộc tính (fields)
	private int id;
	private String email;
	private String userName;
	private String passWord;
	private String fullName; // Thêm thuộc tính thường gặp
	private String avatar; // Đã có trong đoạn code DAO
    private int roleid; // Đã có trong đoạn code DAO
    private String phone; // Đã có trong đoạn code DAO
    private Date createdDate; // Đã có trong đoạn code DAO

    // 2. Constructors
    
    // Constructor mặc định (cần thiết cho Java Beans và frameworks)
    public User() {
    }

    // Constructor đầy đủ (tùy chọn, tiện lợi khi khởi tạo)
    public User(int id, String email, String userName, String passWord, String fullName, String avatar, int roleid, String phone, Date createdDate) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.avatar = avatar;
        this.roleid = roleid;
        this.phone = phone;
        this.createdDate = createdDate;
    }

    // 3. Getters và Setters (Phương thức truy cập và thay đổi thuộc tính)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Đã sửa lại kiểu trả về từ Object sang String
    public String getPassWord() { 
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    // 4. Phương thức toString (Tùy chọn, hữu ích cho debugging)
    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", email=" + email + "]";
    }
}