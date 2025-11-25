package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}