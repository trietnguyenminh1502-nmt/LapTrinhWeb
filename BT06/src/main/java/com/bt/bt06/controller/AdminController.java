package com.bt.bt06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bt.bt06.model.Category;
import com.bt.bt06.model.User;
import com.bt.bt06.model.Video;
import com.bt.bt06.repository.CategoryRepository;
import com.bt.bt06.repository.UserRepository;
import com.bt.bt06.repository.VideoRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final VideoRepository videoRepo;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepo, CategoryRepository categoryRepo, VideoRepository videoRepo,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.videoRepo = videoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Users list
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user) {
        // encode password if not already encoded (BCrypt encoded passwords start with
        // $2a$ or $2b$)
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")
                && !user.getPassword().startsWith("$2b$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepo.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{username}")
    public String editUser(@PathVariable String username, Model model) {
        userRepo.findById(username).ifPresent(u -> model.addAttribute("user", u));
        return "user-form";
    }

    @GetMapping("/users/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        userRepo.deleteById(username);
        return "redirect:/admin/users";
    }

    // Categories list
    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        return "categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("users", userRepo.findAll());
        return "category-form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category, @RequestParam(required = false) String username) {
        if (username != null && !username.isEmpty()) {
            userRepo.findById(username).ifPresent(category::setUser);
        }
        categoryRepo.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        categoryRepo.findById(id).ifPresent(c -> model.addAttribute("category", c));
        model.addAttribute("users", userRepo.findAll());
        return "category-form";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepo.deleteById(id);
        return "redirect:/admin/categories";
    }

    // Videos
    @GetMapping("/videos")
    public String videos(Model model, @RequestParam(required = false) String q) {
        List<Video> list;
        if (q != null && !q.isEmpty())
            list = videoRepo.findByTitleContainingIgnoreCase(q);
        else
            list = videoRepo.findAll();
        System.out.println("DEBUG: AdminController.videos() called - found videos=" + list.size());
        model.addAttribute("videos", list);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("q", q == null ? "" : q);
        return "videos";
    }

    @GetMapping("/videos/new")
    public String newVideo(Model model) {
        model.addAttribute("video", new Video());
        model.addAttribute("categories", categoryRepo.findAll());
        return "video-form";
    }

    @PostMapping("/videos/save")
    public String saveVideo(@ModelAttribute Video video, @RequestParam(required = false) Long categoryId) {
        if (categoryId != null)
            categoryRepo.findById(categoryId).ifPresent(video::setCategory);
        videoRepo.save(video);
        return "redirect:/admin/videos";
    }

    @GetMapping("/videos/edit/{id}")
    public String editVideo(@PathVariable Long id, Model model) {
        videoRepo.findById(id).ifPresent(v -> model.addAttribute("video", v));
        model.addAttribute("categories", categoryRepo.findAll());
        return "video-form";
    }

    @GetMapping("/videos/delete/{id}")
    public String deleteVideo(@PathVariable Long id) {
        videoRepo.deleteById(id);
        return "redirect:/admin/videos";
    }
}
