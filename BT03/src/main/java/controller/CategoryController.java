package controller;


import jakarta.servlet.http.HttpSession;
import repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import entity.Category;
import entity.User;


@Controller
public class CategoryController<catRepo, catRepo1, catRepo11, catRepo111> {
    @Autowired CategoryRepository catRepo;

    // --- HIỂN THỊ TRANG HOME ---
    
    // User và Admin: Xem tất cả
    @GetMapping({"/user/home", "/admin/home"})
    public <catRepo> String showAll(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        String roleUrl = (user.getRoleId() == 3) ? "admin" : "user";
        
        model.addAttribute("listCate", catRepo.findAll());
        model.addAttribute("roleUrl", roleUrl); 
        return "home";
    }

    // Manager: Chỉ xem của mình
    @GetMapping("/manager/home")
    public String showMyCategories(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        
        model.addAttribute("listCate", catRepo.findByUserId(user.getId()));
        model.addAttribute("roleUrl", "manager");
        return "home";
    }

    // --- CHỨC NĂNG THÊM MỚI ---
    
    @GetMapping("/{role}/add")
    public String showAddForm(@PathVariable String role, Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("roleUrl", role);
        return "category_form";
    }

    @PostMapping("/{role}/save")
    public <catRepo> String saveCategory(@PathVariable String role, 
                               @ModelAttribute Category category, 
                               HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        category.setUser(user); // Gán người tạo là user đang login
        catRepo.save(category);
        return "redirect:/" + role + "/home";
    }

    // --- CHỨC NĂNG XÓA ---
    
    @GetMapping("/{role}/delete/{id}")
    public <catRepo> String delete(@PathVariable String role, @PathVariable Long id) {
        catRepo.deleteById(id);
        return "redirect:/" + role + "/home";
    }
}