package com.example.todo.functions.characterMaster.repository;

import com.example.todo.functions.characterMaster.entity.GameCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing GameCharacter entities.
 * This interface extends JpaRepository and JpaSpecificationExecutor to provide CRUD operations and query capabilities.
 */
@Repository
public interface CharacterRepository extends JpaRepository<GameCharacter, Long>, JpaSpecificationExecutor<GameCharacter> {

    /*
     * Custom query methods for GameCharacter entity
     */
    List<GameCharacter> findByIsDeletedFalse();

    /* Find all characters that are not deleted with pagination
     * @param pageable Pagination information
     * @return A page of GameCharacter entities that are not deleted
     */
    Page<GameCharacter> findByIsDeletedFalse(Pageable pageable);

    /**
     * Find a character by its ID that is not deleted.
     * @param id The ID of the character
     * @return An Optional containing the GameCharacter if found, or empty if not found or deleted
     */
    Optional<GameCharacter> findByIdAndIsDeletedFalse(Long id);


}
