package com.example.controller;

@GetMapping
public String list(Model model, @RequestParam(name = "keyword", required = false) String keyword) {

    List<Video> list;

    if (StringUtils.hasText(keyword)) {
        list = videoRepository.findByTitleContaining(keyword);
    } else {
        list = videoRepository.findAll();
    }

    model.addAttribute("videos", list);
    model.addAttribute("keyword", keyword); // Giữ lại giá trị tìm kiếm

    return "admin/video/list";
}

