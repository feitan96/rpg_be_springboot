package com.example.todo.functions.characterMaster.service;

import com.example.todo.functions.characterMaster.dto.FilterCharacter;
import com.example.todo.functions.characterMaster.dto.ReadCharacter;
import com.example.todo.functions.characterMaster.dto.CreateCharacter;
import com.example.todo.functions.characterMaster.dto.UpdateCharacter;
import com.example.todo.functions.characterMaster.entity.GameCharacter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public interface CharacterService {

    // Convert GameCharacter entity to ReadCharacter DTO
    ReadCharacter convertToDTO(GameCharacter character);

    // Find all characters that are not deleted
    List<ReadCharacter> getAllCharacters();

    // Find a character by ID that is not deleted
    ReadCharacter getCharacterById(Long id);

    // Find all characters that are not deleted with pagination
    Page<ReadCharacter> getAllCharactersPaginated(int page, int size, String sortBy, String sortDirection);

    // Find all characters with search, filter and pagination
    Page<ReadCharacter> searchAndFilterCharacters(String searchTerm, FilterCharacter filter, int page, int size, String sortBy, String sortDirection);

    // Create a new character from CreateCharacter DTO
    ReadCharacter createCharacter(CreateCharacter createRequest);

    // Create a hero or villain character from CreateCharacter DTO
    ReadCharacter createHero(CreateCharacter createRequest);

    // Create a villain character from CreateCharacter DTO
    ReadCharacter createVillain(CreateCharacter createRequest);

    // Update an existing character from UpdateCharacter DTO
    ReadCharacter updateCharacter(Long id, UpdateCharacter updateRequest);

    // Update a character's sprite image
    ReadCharacter updateCharacterSprite(Long id, MultipartFile file);

    // Soft delete a character by ID
    void softDeleteCharacter(Long id);

    // Hard delete a character by ID
    void hardDeleteCharacter(Long id);
}