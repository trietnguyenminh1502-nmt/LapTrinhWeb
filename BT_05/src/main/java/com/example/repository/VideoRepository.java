package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Video;


public interface VideoRepository extends JpaRepository<Video, String> {
    List<Video> findByTitleContaining(String title);
}// Tìm kiếm theo tiêu đề
