package com.springsecurity.demosecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AppUser {
    @Id
    private String username;
    private String password;
    private String role;
}