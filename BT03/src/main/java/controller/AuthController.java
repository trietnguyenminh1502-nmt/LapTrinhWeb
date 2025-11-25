package controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repository.UserRepository;

import entity.User;

@Controller
public class AuthController {
    @Autowired UserRepository userRepo;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                        @RequestParam String password, 
                        HttpSession session, Model model) {
        User user = userRepo.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            
            // Chuyển hướng theo Role
            if (user.getRoleId() == 1) return "redirect:/user/home";
            if (user.getRoleId() == 2) return "redirect:/manager/home";
            if (user.getRoleId() == 3) return "redirect:/admin/home";
        }
        
        model.addAttribute("mess", "Sai tài khoản hoặc mật khẩu!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
