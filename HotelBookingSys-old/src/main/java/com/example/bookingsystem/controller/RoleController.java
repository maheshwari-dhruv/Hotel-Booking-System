package com.example.bookingsystem.controller;

import com.example.bookingsystem.domain.Role;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @GetMapping("/view/all")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @GetMapping("/view/users/{roleName}")
    public ResponseEntity<List<User>> getAllUsersByRole(@PathVariable String roleName) {
        return ResponseEntity.ok().body(roleService.getAllUsersByRole(roleName));
    }
}
