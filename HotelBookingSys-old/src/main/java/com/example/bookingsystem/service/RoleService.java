package com.example.bookingsystem.service;

import com.example.bookingsystem.domain.Role;
import com.example.bookingsystem.domain.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    ResponseEntity<String> saveRole(Role role);
    List<Role> getAllRoles();
    List<User> getAllUsersByRole(String name);
}
