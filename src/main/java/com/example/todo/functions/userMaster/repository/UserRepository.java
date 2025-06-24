package com.example.todo.functions.userMaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.todo.functions.userMaster.entity.User;

public interface  UserRepository extends JpaRepository<User, String> {
    
    //find all active users (not deleted)
    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    List<User> findAllActive();
    
    //find a user by email
    User findByEmail(String email);
    
    //check if a user exists by email
    boolean existsByEmail(String email);

    //soft delete a user by id
    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = ?1")
    void softDeleteUserById(String id);

    //hard delete a user by id
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = ?1")
    void hardDeleteUserById(String id);

    //update a user by id
    @Modifying
    @Query("UPDATE User u SET u.name = ?2, u.email = ?3 WHERE u.id = ?1")
    void updateUserById(String id, String name, String email);
}
