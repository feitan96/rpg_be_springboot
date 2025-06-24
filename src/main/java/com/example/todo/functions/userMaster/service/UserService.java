package com.example.todo.functions.userMaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo.functions.userMaster.entity.User;
import com.example.todo.functions.userMaster.repository.UserRepository;

@Service
@Transactional
public class UserService {  

    @Autowired
    private UserRepository userRepository;
    
    //retrieve all users (including soft-deleted)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //retrieve all active users (not soft-deleted)
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActive();
    }
    
    //retrieve a user by id
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    //create a new user
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }

        user.setId(null); 
        return userRepository.save(user);
    }

    //update an existing user
    public User updateUser(String id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    //soft delete a user by id (existing user)
    public void softDeleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.softDeleteUserById(id);
        }
    }

    //hard delete a user by id (existing user)
    public void hardDeleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

}
