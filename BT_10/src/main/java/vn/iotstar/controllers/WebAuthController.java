package vn.iotstar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebAuthController {
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/profile")
    public String profilePage() {
        return "profile";
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Sẽ tìm file register.html trong thư mục templates
    }
}