package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.Role;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.domain.jwt.JwtAuthRequest;
import com.example.bookingsystem.domain.jwt.JwtAuthResponse;
import com.example.bookingsystem.repository.RoleRepository;
import com.example.bookingsystem.repository.UserRepository;
import com.example.bookingsystem.service.UserService;
import com.example.bookingsystem.util.JwtUtil;
import com.example.bookingsystem.validation.RoleValidation;
import com.example.bookingsystem.validation.StringValidation;
import com.example.bookingsystem.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Override
    public ResponseEntity<String> saveUser(User user, String roleName) {
        log.debug("Function saveUser - user: " + user + " | role name: " + roleName);
        if (UserValidation.checkUserDetails(user)) {
            log.error("User details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User details incorrect");
        }

        log.info("Saving new user " + user.getUsername() + " to db");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        addRoleToUser(user.getUsername(), roleName);
        return ResponseEntity.ok().body("User saved");
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.debug("Function addRoleToUser - username: " + username + " | rolename: " + roleName);

        if (StringValidation.checkNullEmptyString(username) || StringValidation.checkNullEmptyString(roleName)) {
            log.error("username & rolename can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "username & rolename can't be null or empty");
        }

        log.info("Adding role " + roleName + " to user " + username);

        User user = userRepository.findByUsername(username);
        log.debug("Function addRoleToUser - user: " + user);

        Role role = roleRepository.findByName(roleName);
        log.debug("Function addRoleToUser - role: " + role);

        if (UserValidation.checkUserDetails(user) && RoleValidation.checkRole(role)) {
            log.error("user & role can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "user & role can't be null or empty");
        }

        user.getRoles().add(role);

        log.info("Role " + role.getName() + " added to user " + user.getUsername());
    }

    @Override
    public ResponseEntity<JwtAuthResponse> signInUser(JwtAuthRequest jwtAuthRequest) {
        log.debug("username: " + jwtAuthRequest.getUsername());
        String username = jwtAuthRequest.getUsername();

        log.debug("password: " + jwtAuthRequest.getPassword());
        String password = jwtAuthRequest.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            throw new UsernameNotFoundException("Bad Credential");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        log.debug("token: " + jwtUtil.generateToken(userDetails));
        String token = jwtUtil.generateToken(userDetails);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
        return ResponseEntity.ok().body(jwtAuthResponse);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all user");
        List<User> allUsers = userRepository.findAll();

        log.debug("Function getUsers - allUsers: " + allUsers.toString());

        if (allUsers.isEmpty()) {
            log.error("no user found in db");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no user found in db");
        }

        log.info("Users: " + allUsers);
        return allUsers;
    }

    @Override
    public User getUser(String username) {
        log.debug("Function getUser - username: " + username);

        if (StringValidation.checkNullEmptyString(username)) {
            log.error("username can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "username can't be null or empty");
        }

        log.info("Fetching user " + username);

        User userFound = userRepository.findByUsername(username);

        log.debug("Function getUser - user: " + userFound);

        if (userFound == null) {
            log.error("no user found with this username: " + username);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no user found with this username: " + username);
        }

        log.info("Fetched user: " + userFound);
        return userFound;
    }

    @Override
    public User updateUser(String username, User user) {
        log.debug("Function updateUser - username: " + username);
        log.debug("Function updateUser - user: " + user);

        if (StringValidation.checkNullEmptyString(username) || UserValidation.checkUserDetails(user)) {
            log.error("entered details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "entered details incorrect");
        }

        log.info("Updating user");

        User userFound = getUser(username);
        log.debug("Function updateUser - user found: " + userFound.toString());

        if (UserValidation.checkUserDetails(userFound)) {
            log.error("user details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "user details incorrect");
        }

        log.debug("first name: " + user.getFirstName());
        userFound.setFirstName(user.getFirstName());

        log.debug("email: " + user.getEmail());
        userFound.setEmail(user.getEmail());

        log.debug("password: " + user.getPassword());
        userFound.setPassword(passwordEncoder.encode(user.getPassword()));

        log.debug("phone: " + user.getPhone());
        userFound.setPhone(user.getPhone());

        log.debug("last name: " + user.getLastName());
        userFound.setLastName(user.getLastName());

        log.debug("username: " + user.getUsername());
        userFound.setUsername(user.getUsername());

        log.debug("Function updatedUser - userFound: " + userFound);
        return userRepository.save(userFound);
    }

    @Override
    public ResponseEntity<String> deleteUser(String username) {
        log.debug("Function deleteUser - username: " + username);

        if (StringValidation.checkNullEmptyString(username)) {
            log.error("username can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "username can't be null or empty");
        }

        log.info("Deleting user with username: " + username);
        userRepository.deleteByUsername(username);
        return ResponseEntity.ok().body("username: " + username + " deleted successfully");
    }
}
