package com.bt.bt06.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bt.bt06.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
