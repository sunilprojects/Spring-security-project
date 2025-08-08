package com.springsecurity.demosecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.demosecurity.entity.AppUser;

public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);
}
