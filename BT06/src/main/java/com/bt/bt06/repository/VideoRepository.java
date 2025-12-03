package com.bt.bt06.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bt.bt06.model.Video;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByTitleContainingIgnoreCase(String title);
}
