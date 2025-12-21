package vn.iotstar.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String email;    // Hoặc usernameOrEmail tùy theo bạn muốn dùng
    private String password; // BẮT BUỘC phải có trường này để sinh ra getPassword()
}