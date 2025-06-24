package com.example.todo.functions.characterMaster.service.impl;

import com.example.todo.functions.characterMaster.dto.FilterCharacter;
import com.example.todo.functions.characterMaster.dto.ReadCharacter;
import com.example.todo.functions.characterMaster.dto.CreateCharacter;
import com.example.todo.functions.characterMaster.dto.UpdateCharacter;
import com.example.todo.functions.characterMaster.entity.GameCharacter;
import com.example.todo.functions.characterMaster.enums.CharacterType;
import com.example.todo.functions.characterMaster.service.CharacterService;
import com.example.todo.functions.characterMaster.specification.CharacterSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public List<ReadCharacter> getAllCharacters(){
        List<GameCharacter> characters = characterRepository.findByIsDeletedFalse();
        return characters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Find all characters that are not deleted with pagination
    @Override
    public Page<ReadCharacter> getAllCharactersPaginated(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<GameCharacter> characterPage = characterRepository.findByIsDeletedFalse(pageable);

        return characterPage.map(this::convertToDTO);
    }

    // Find all characters with search, filter and pagination
    @Override
    public Page<ReadCharacter> searchAndFilterCharacters(String searchTerm, FilterCharacter filter, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (filter == null) {
            filter = new FilterCharacter();
        }

        Specification<GameCharacter> spec = CharacterSpecification.getFilteredCharacters(filter, searchTerm);
        Page<GameCharacter> characterPage = characterRepository.findAll(spec, pageable);

        return characterPage.map(this::convertToDTO);
    }


    // Find a character by ID that is not deleted
    @Override
    public ReadCharacter getCharacterById(Long id) {
        GameCharacter character = characterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + id));
        return convertToDTO(character);
    }

    // Create a new character from CreateCharacter DTO
    @Override
    public ReadCharacter createCharacter(CreateCharacter createRequest) {
        GameCharacter character = new GameCharacter();
        BeanUtils.copyProperties(createRequest, character);

        //set to type == "NPC" if type is not specified
        if (character.getType() == null) {
            character.setType(CharacterType.NPC);
        }

        character.setIsDeleted(false);

        GameCharacter savedCharacter = characterRepository.save(character);
        return convertToDTO(savedCharacter);
    }

    // Create a hero character from CreateCharacter DTO
    @Override
    public ReadCharacter createHero(CreateCharacter createRequest) {
        createRequest.setType(CharacterType.HERO);
        return createCharacter(createRequest);
    }

    // Create a villain character from CreateCharacter DTO
    @Override
    public ReadCharacter createVillain(CreateCharacter createRequest) {
        createRequest.setType(CharacterType.VILLAIN);
        return createCharacter(createRequest);
    }

    // Update an existing character from UpdateCharacter DTO
    @Override
    public ReadCharacter updateCharacter(Long id, UpdateCharacter updateRequest) {
        GameCharacter existingCharacter = characterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + id));

        BeanUtils.copyProperties(updateRequest, existingCharacter, "id", "isDeleted");
        GameCharacter updatedCharacter = characterRepository.save(existingCharacter);
        return convertToDTO(updatedCharacter);
    }

    // Soft delete a character by ID
    @Override
    public void softDeleteCharacter(Long id) {
        GameCharacter character = characterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + id));
        character.setIsDeleted(true);
        characterRepository.save(character);
    }


    // Hard delete a character by ID
    @Override
    public void hardDeleteCharacter(Long id) {
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
        } else {
            throw new RuntimeException("Character not found with id: " + id);
        }
    }

    // Convert Character entity to ReadCharacter DTO
    public ReadCharacter convertToDTO(GameCharacter character) {
        ReadCharacter dto = new ReadCharacter();
        BeanUtils.copyProperties(character, dto);
        return dto;
    }
}
