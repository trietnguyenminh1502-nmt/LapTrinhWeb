package com.bt.bt06.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bt.bt06.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
