package vn.iotstar.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.Users;
import vn.iotstar.models.LoginDto;
import vn.iotstar.models.LoginResponse;
import vn.iotstar.services.AuthenticationService;
import vn.iotstar.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    // Chức năng Đăng ký: Tự động mã hóa mật khẩu và gán quyền ADMIN
    @PostMapping("/signup")
    public ResponseEntity<Users> register(@RequestBody Users registerUser) {
        Users registeredUser = authenticationService.signup(registerUser);
        return ResponseEntity.ok(registeredUser);
    }

    // Chức năng Đăng nhập: Trả về JWT Token
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUser) {
        // Gọi service xác thực. Kết quả trả về trực tiếp đối tượng Users
        Users authenticatedUser = authenticationService.authenticate(loginUser);

        // Tạo chuỗi Token JWT
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Đóng gói thông tin phản hồi
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}