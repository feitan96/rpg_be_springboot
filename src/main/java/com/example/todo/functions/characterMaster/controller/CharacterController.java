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

@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // Endpoint to retrieve all characters
    @GetMapping
    public ResponseEntity<List<ReadCharacter>> getAllCharacters() {
        try {
            List<ReadCharacter> characters = characterService.getAllCharacters();
            return new  ResponseEntity<>(characters, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to retrieve all characters with pagination
    //try catch to be added later
    @GetMapping("/paginated")
    public ResponseEntity<Page<ReadCharacter>> getAllCharactersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<ReadCharacter> characterPage = characterService.getAllCharactersPaginated(page, size, sortBy, sortDirection);
        return new ResponseEntity<>(characterPage, HttpStatus.OK);
    }

    // Endpoint for advanced search and filtering
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

    // Endpoint to get all character types for filtering
    @GetMapping("/types")
    public ResponseEntity<List<CharacterType>> getAllCharacterTypes() {
        try {
            List<CharacterType> types = Arrays.asList(CharacterType.values());
            return new ResponseEntity<>(types, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get all character classifications for filtering
    @GetMapping("/classifications")
    public ResponseEntity<List<CharacterClassification>> getAllCharacterClassifications() {
        try {
            List<CharacterClassification> classifications = Arrays.asList(CharacterClassification.values());
            return new ResponseEntity<>(classifications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to retrieve a character by ID
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

    // Endpoint to create a new character
    //try catch to be added later
    @PostMapping
    public ResponseEntity<ReadCharacter> createCharacter(@RequestBody CreateCharacter createRequest) {
        ReadCharacter createdCharacter = characterService.createCharacter(createRequest);
        return new ResponseEntity<>(createdCharacter, HttpStatus.CREATED);
    }

    @PostMapping("/hero")
    public ResponseEntity<ReadCharacter> createHero(@RequestBody CreateCharacter createRequest) {
        try {
            ReadCharacter createdCharacter = characterService.createHero(createRequest);
            return new ResponseEntity<>(createdCharacter, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/villain")
    public ResponseEntity<ReadCharacter> createVillain(@RequestBody CreateCharacter createRequest) {
        try {
            ReadCharacter createdCharacter = characterService.createVillain(createRequest);
            return new ResponseEntity<>(createdCharacter, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to update a character's sprite image
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

    // Endpoint to update an existing character
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

    // Endpoint to soft delete a character
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

    // Endpoint to hard delete a character
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
