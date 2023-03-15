package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.Role;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.repository.RoleRepository;
import com.example.bookingsystem.repository.UserRepository;
import com.example.bookingsystem.service.RoleService;
import com.example.bookingsystem.validation.RoleValidation;
import com.example.bookingsystem.validation.StringValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> saveRole(Role role) {
        log.debug("Function saveRole - role: " + role);

        if (RoleValidation.checkRole(role)) {
            log.error("Role details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Role details incorrect");
        }

        log.info("Saving new role " + role.getName() + " to db");
        roleRepository.save(role);
        return ResponseEntity.ok().body("Role saved in db");
    }

    @Override
    public List<Role> getAllRoles() {

        log.info("fetching all roles from db");
        List<Role> allRoles = roleRepository.findAll();

        log.debug("Function getAllRoles - allRoles: " + allRoles);
        if (allRoles.isEmpty()) {
            log.error("No role found in db");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No role found in db");
        }

        log.info("All roles fetched");
        return allRoles;
    }

    @Override
    public List<User> getAllUsersByRole(String name) {
        log.debug("Function getAllUsersByRole - name: " + name);

        if (StringValidation.checkNullEmptyString(name)) {
            log.error("role name can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "role name can't be null or empty");
        }

        log.info("fetching all user by roles");

        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().contains(name))
                .toList();

        log.debug("Function getAllUsersByRole - users: " + users);

        if (users.isEmpty()) {
            log.error("no user found of this role");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no user found of this role");
        }

        log.info("All users fetched");
        return users;
    }
}
