package com.example.todo.functions.characterMaster.service;

import com.example.todo.functions.characterMaster.dto.CharacterDTO;
import com.example.todo.functions.characterMaster.dto.CreateCharacter;
import com.example.todo.functions.characterMaster.dto.UpdateCharacter;
import com.example.todo.functions.characterMaster.entity.GameCharacter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CharacterService {

    // Find all characters that are not deleted
    List<CharacterDTO> getAllCharacters();

    // Find all characters that are not deleted with pagination
    Page<CharacterDTO> getAllCharactersPaginated(int page, int size, String sortBy, String sortDirection);

    // Convert GameCharacter entity to CharacterDTO
    CharacterDTO convertToDTO(GameCharacter character);

    // Create a new character from CreateCharacter DTO
    CharacterDTO createCharacter(CreateCharacter createRequest);

    // Update an existing character from UpdateCharacter DTO
    CharacterDTO updateCharacter(Long id, UpdateCharacter updateRequest);
}