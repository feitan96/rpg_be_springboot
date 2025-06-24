package com.example.todo.functions.userMaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.functions.userMaster.entity.User;
import com.example.todo.functions.userMaster.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //CREATE
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating user: " + e.getMessage());
        }       
    }

    //READ all active (not deleted)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    //READ all
    @GetMapping("/active")
    public ResponseEntity<List<User>> getAllActiveUsers() {
        try {
            List<User> users = userService.getAllActiveUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    //READ by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }    
    
    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating user: " + e.getMessage());
        }
    }

    //SOFT DELETE
    @PutMapping("/{id}/soft")
    public ResponseEntity<?> softDeleteUser(@PathVariable String id) {
        try {
            userService.softDeleteUser(id);
            return ResponseEntity.ok("User soft deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error soft deleting user: " + e.getMessage());
        }
    }    
    
    //HARD DELETE
    @DeleteMapping("/{id}/hard")
    public ResponseEntity<?> hardDeleteUser(@PathVariable String id) {
        try {
            userService.hardDeleteUser(id);
            return ResponseEntity.ok("User hard deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error hard deleting user: " + e.getMessage());
        }
    }
    
    
    
    
}
