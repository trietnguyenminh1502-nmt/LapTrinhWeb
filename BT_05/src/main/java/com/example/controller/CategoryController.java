package com.example.controller;

import java.util.List;  // Dùng List của Java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Dùng Model của Spring MVC
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Category;
import com.example.repository.CategoryRepository;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model) {
        List<Category> list = categoryRepository.findAll();  // Không cần cast
        model.addAttribute("categories", list);
        return "admin/category/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("category") Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        model.addAttribute("category", category);
        return "admin/category/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }
}
