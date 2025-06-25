package com.example.todo.functions.characterMaster.service.impl;

import com.example.todo.common.service.FileStorageService;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.todo.common.exception.ResourceNotFoundException;
import com.example.todo.common.exception.InvalidRequestException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CharacterService interface that provides operations
 * for managing game characters including creation, retrieval, update, and deletion.
 */
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final FileStorageService fileStorageService;

    /**
     * Constructs a CharacterServiceImpl with required dependencies.
     *
     * @param characterRepository repository for character data operations
     * @param fileStorageService service for handling file storage operations
     */
    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, FileStorageService fileStorageService) {
        this.characterRepository = characterRepository;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Retrieves all non-deleted characters from the database.
     *
     * @return list of characters converted to DTOs
     */
    @Override
    public List<ReadCharacter> getAllCharacters(){
        List<GameCharacter> characters = characterRepository.findByIsDeletedFalse();
        return characters.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all non-deleted characters with pagination support.
     *
     * @param page zero-based page index
     * @param size size of the page to be returned
     * @param sortBy field to sort by
     * @param sortDirection direction of sort ("asc" or "desc")
     * @return paginated list of characters converted to DTOs
     */
    @Override
    public Page<ReadCharacter> getAllCharactersPaginated(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<GameCharacter> characterPage = characterRepository.findByIsDeletedFalse(pageable);

        return characterPage.map(this::convertToDTO);
    }

    /**
     * Searches and filters characters based on provided criteria with pagination.
     *
     * @param searchTerm optional term to search for in character fields
     * @param filter criteria to filter characters
     * @param page zero-based page index
     * @param size size of the page to be returned
     * @param sortBy field to sort by
     * @param sortDirection direction of sort ("asc" or "desc")
     * @return filtered and paginated list of characters converted to DTOs
     */
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


    /**
     * Retrieves a specific character by its ID if it's not deleted.
     *
     * @param id the ID of the character to retrieve
     * @return the character converted to DTO
     * @throws ResourceNotFoundException if character is not found or is deleted
     */
    @Override
    public ReadCharacter getCharacterById(Long id) {
        GameCharacter character = characterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character", id));
        return convertToDTO(character);
    }

    /**
     * Creates a new character from the provided data.
     * Sets default values where necessary.
     *
     * @param createRequest DTO containing character creation data
     * @return the created character converted to DTO
     */
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

    /**
     * Creates a hero character from the provided data.
     * Automatically sets the character type to HERO.
     *
     * @param createRequest DTO containing character creation data
     * @return the created hero character converted to DTO
     */
    @Override
    public ReadCharacter createHero(CreateCharacter createRequest) {
        createRequest.setType(CharacterType.HERO);
        return createCharacter(createRequest);
    }

    /**
     * Creates a villain character from the provided data.
     * Automatically sets the character type to VILLAIN.
     *
     * @param createRequest DTO containing character creation data
     * @return the created villain character converted to DTO
     */
    @Override
    public ReadCharacter createVillain(CreateCharacter createRequest) {
        createRequest.setType(CharacterType.VILLAIN);
        return createCharacter(createRequest);
    }

    /**
     * Updates an existing character with new data.
     * Preserves certain fields like ID and deletion status.
     *
     * @param id the ID of the character to update
     * @param updateRequest DTO containing updated character data
     * @return the updated character converted to DTO
     * @throws ResourceNotFoundException if character is not found or is deleted
     */
    @Override
    public ReadCharacter updateCharacter(Long id, UpdateCharacter updateRequest) {
        GameCharacter existingCharacter = characterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character", id));

        BeanUtils.copyProperties(updateRequest, existingCharacter, "id", "isDeleted");
        GameCharacter updatedCharacter = characterRepository.save(existingCharacter);
        return convertToDTO(updatedCharacter);
    }

    /**
     * Updates a character's sprite image.
     * Deletes the old sprite file if one exists.
     *
     * @param id the ID of the character to update
     * @param file new sprite image file
     * @return the updated character converted to DTO
     * @throws ResourceNotFoundException if character is not found or is deleted
     * @throws InvalidRequestException if the file is null or empty
     */
    @Override
    public ReadCharacter updateCharacterSprite(Long id, MultipartFile file) {
        GameCharacter character = characterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character", id));

        if (file == null || file.isEmpty()) {
            throw new InvalidRequestException("Sprite file cannot be empty");
        }

        // Delete old sprite if exists
        if (character.getSpritePath() != null && !character.getSpritePath().isEmpty()) {
            // Extract just the filename from the full path
            String oldFileName = character.getSpritePath().substring(
                    character.getSpritePath().lastIndexOf("/") + 1);
            fileStorageService.deleteFile(oldFileName);
        }

        // Store the new file
        String fileName = fileStorageService.storeFile(file);

        // Create URL path for the sprite
        String fileUrl = "/uploads/" + fileName;
        character.setSpritePath(fileUrl);

        GameCharacter updatedCharacter = characterRepository.save(character);
        return convertToDTO(updatedCharacter);
    }

    /**
     * Performs a soft delete on a character (marks as deleted without removing from database).
     *
     * @param id the ID of the character to soft delete
     * @throws ResourceNotFoundException if character is not found or is already deleted
     */
    @Override
    public void softDeleteCharacter(Long id) {
        GameCharacter character = characterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character", id));
        character.setIsDeleted(true);
        characterRepository.save(character);
    }


    /**
     * Permanently removes a character from the database.
     *
     * @param id the ID of the character to hard delete
     * @throws ResourceNotFoundException if character does not exist
     */
    @Override
    public void hardDeleteCharacter(Long id) {
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Character", id);
        }
    }

    /**
     * Converts a GameCharacter entity to a ReadCharacter DTO.
     *
     * @param character the entity to convert
     * @return the converted DTO
     */
    public ReadCharacter convertToDTO(GameCharacter character) {
        ReadCharacter dto = new ReadCharacter();
        BeanUtils.copyProperties(character, dto);
        return dto;
    }
}
