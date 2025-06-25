package com.example.todo.functions.characterMaster.controller;


import com.example.todo.functions.characterMaster.dto.ReadCharacter;
import com.example.todo.functions.characterMaster.dto.CreateCharacter;
import com.example.todo.functions.characterMaster.dto.UpdateCharacter;
import com.example.todo.functions.characterMaster.service.CharacterService;
import com.example.todo.functions.characterMaster.dto.FilterCharacter;
import com.example.todo.functions.characterMaster.enums.CharacterType;
import com.example.todo.functions.characterMaster.enums.CharacterClassification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing character operations.
 * Provides endpoints for creating, retrieving, updating, and deleting characters.
 */
@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {

    private final CharacterService characterService;

    /**
     * Constructs a CharacterController with the specified service.
     *
     * @param characterService the service for character operations
     */
    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * Retrieves all characters.
     *
     * @return ResponseEntity containing a list of characters or an error status
     */
    @GetMapping
    public ResponseEntity<List<ReadCharacter>> getAllCharacters() {
        try {
            List<ReadCharacter> characters = characterService.getAllCharacters();
            return new  ResponseEntity<>(characters, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all characters with pagination.
     *
     * @param page page number (zero-based)
     * @param size number of items per page
     * @param sortBy field to sort by
     * @param sortDirection sort direction (asc or desc)
     * @return ResponseEntity containing a page of characters
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<ReadCharacter>> getAllCharactersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<ReadCharacter> characterPage = characterService.getAllCharactersPaginated(page, size, sortBy, sortDirection);
        return new ResponseEntity<>(characterPage, HttpStatus.OK);
    }

    /**
     * Searches and filters characters with pagination.
     *
     * @param searchTerm optional search term
     * @param filter filter criteria
     * @param page page number (zero-based)
     * @param size number of items per page
     * @param sortBy field to sort by
     * @param sortDirection sort direction (asc or desc)
     * @return ResponseEntity containing a filtered page of characters
     */
    @GetMapping("/search")
    public ResponseEntity<Page<ReadCharacter>> searchAndFilterCharacters(
            @RequestParam(required = false) String searchTerm,
            @ModelAttribute FilterCharacter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<ReadCharacter> characterPage = characterService.searchAndFilterCharacters(
                    searchTerm, filter, page, size, sortBy, sortDirection);
            return new ResponseEntity<>(characterPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all available character types.
     *
     * @return ResponseEntity containing a list of character types
     */
    @GetMapping("/types")
    public ResponseEntity<List<CharacterType>> getAllCharacterTypes() {
        try {
            List<CharacterType> types = Arrays.asList(CharacterType.values());
            return new ResponseEntity<>(types, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all available character classifications.
     *
     * @return ResponseEntity containing a list of character classifications
     */
    @GetMapping("/classifications")
    public ResponseEntity<List<CharacterClassification>> getAllCharacterClassifications() {
        try {
            List<CharacterClassification> classifications = Arrays.asList(CharacterClassification.values());
            return new ResponseEntity<>(classifications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a character by ID.
     *
     * @param id the character ID
     * @return ResponseEntity containing the character or an error status
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReadCharacter> getCharacterById(@PathVariable Long id) {
        try {
            ReadCharacter character = characterService.getCharacterById(id);
            return new ResponseEntity<>(character, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new character.
     *
     * @param createRequest the character creation data
     * @return ResponseEntity containing the created character
     */
    @PostMapping
    public ResponseEntity<ReadCharacter> createCharacter(@RequestBody CreateCharacter createRequest) {
        ReadCharacter createdCharacter = characterService.createCharacter(createRequest);
        return new ResponseEntity<>(createdCharacter, HttpStatus.CREATED);
    }

    /**
     * Creates a new hero character.
     *
     * @param createRequest the hero creation data
     * @return ResponseEntity containing the created hero character
     */
    @PostMapping("/hero")
    public ResponseEntity<ReadCharacter> createHero(@RequestBody CreateCharacter createRequest) {
        try {
            ReadCharacter createdCharacter = characterService.createHero(createRequest);
            return new ResponseEntity<>(createdCharacter, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new villain character.
     *
     * @param createRequest the villain creation data
     * @return ResponseEntity containing the created villain character
     */
    @PostMapping("/villain")
    public ResponseEntity<ReadCharacter> createVillain(@RequestBody CreateCharacter createRequest) {
        try {
            ReadCharacter createdCharacter = characterService.createVillain(createRequest);
            return new ResponseEntity<>(createdCharacter, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a character's sprite image.
     *
     * @param id the character ID
     * @param file the new sprite image file
     * @return ResponseEntity containing the updated character
     */
    @PostMapping("/{id}/sprite")
    public ResponseEntity<ReadCharacter> updateCharacterSprite(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            ReadCharacter updatedCharacter = characterService.updateCharacterSprite(id, file);
            return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing character.
     *
     * @param id the character ID
     * @param updateRequest the update data
     * @return ResponseEntity containing the updated character
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReadCharacter> updateCharacter(@PathVariable Long id, @RequestBody UpdateCharacter updateRequest) {
        try {
            ReadCharacter updatedCharacter = characterService.updateCharacter(id, updateRequest);
            return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Softly deletes a character (marks as deleted without removing from database).
     *
     * @param id the character ID
     * @return ResponseEntity with status indicating success or failure
     */
    @PatchMapping("/{id}/soft-delete")
    public ResponseEntity<?> softDeleteCharacter(@PathVariable Long id) {
        try {
            characterService.softDeleteCharacter(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Hard deletes a character (permanently removes from a database).
     *
     * @param id the character ID
     * @return ResponseEntity with status indicating success or failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> hardDeleteCharacter(@PathVariable Long id) {
        try {
            characterService.hardDeleteCharacter(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
