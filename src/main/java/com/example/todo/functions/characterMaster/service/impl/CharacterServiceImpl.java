package com.example.todo.functions.characterMaster.service.impl;

import com.example.todo.functions.characterMaster.dto.CharacterDTO;
import com.example.todo.functions.characterMaster.dto.CreateCharacter;
import com.example.todo.functions.characterMaster.dto.UpdateCharacter;
import com.example.todo.functions.characterMaster.entity.GameCharacter;
import com.example.todo.functions.characterMaster.service.CharacterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.todo.functions.characterMaster.repository.CharacterRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    // Find all characters that are not deleted
    @Override
    public List<CharacterDTO> getAllCharacters(){
        List<GameCharacter> characters = characterRepository.findByIsDeletedFalse();
        return characters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Find all characters that are not deleted with pagination
    @Override
    public Page<CharacterDTO> getAllCharactersPaginated(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<GameCharacter> characterPage = characterRepository.findByIsDeletedFalse(pageable);

        return characterPage.map(this::convertToDTO);
    }

    // Create a new character from CreateCharacter DTO
    @Override
    public CharacterDTO createCharacter(CreateCharacter createRequest) {
        GameCharacter character = new GameCharacter();
        BeanUtils.copyProperties(createRequest, character);
        character.setIsDeleted(false);

        GameCharacter savedCharacter = characterRepository.save(character);
        return convertToDTO(savedCharacter);
    }

    // Update an existing character from UpdateCharacter DTO
    @Override
    public CharacterDTO updateCharacter(Long id, UpdateCharacter updateRequest) {
        GameCharacter existingCharacter = characterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + id));

        BeanUtils.copyProperties(updateRequest, existingCharacter, "id", "isDeleted");
        GameCharacter updatedCharacter = characterRepository.save(existingCharacter);
        return convertToDTO(updatedCharacter);
    }

    // Convert Character entity to CharacterDTO
    public CharacterDTO convertToDTO(GameCharacter character) {
        CharacterDTO dto = new CharacterDTO();
        BeanUtils.copyProperties(character, dto);
        return dto;
    }
}
