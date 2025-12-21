package vn.iotstar.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Users;
import vn.iotstar.models.LoginDto;
import vn.iotstar.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, 
                                 AuthenticationManager authenticationManager, 
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder; 
    }

    /**
     * Chức năng đăng ký người dùng mới.
     * Mật khẩu sẽ được mã hóa BCrypt trước khi lưu vào Database.
     */
    public Users signup(Users input) {
        // Mã hóa mật khẩu
        input.setPassword(passwordEncoder.encode(input.getPassword()));
        // Kích hoạt tài khoản mặc định là true
        input.setEnabled(true);
        
        return userRepository.save(input);
    }

    /**
     * Chức năng xác thực người dùng để đăng nhập JWT.
     */
    public Users authenticate(LoginDto input) {
        // Sử dụng AuthenticationManager để kiểm tra email và mật khẩu
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
        );

        // Nếu xác thực thành công, trả về thông tin User
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}