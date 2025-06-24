package com.example.todo.functions.characterMaster.repository;

import com.example.todo.functions.characterMaster.entity.GameCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<GameCharacter, Long>, JpaSpecificationExecutor<GameCharacter> {

    // Find all characters that are not deleted
    List<GameCharacter> findByIsDeletedFalse();

    // Find all characters that are not deleted with pagination
    Page<GameCharacter> findByIsDeletedFalse(Pageable pageable);

    // Find a character by ID that is not deleted
    Optional<GameCharacter> findByIdAndIsDeletedFalse(Long id);


}
