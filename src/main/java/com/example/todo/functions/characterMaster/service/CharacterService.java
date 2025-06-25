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

    /* Convert a GameCharacter entity to a ReadCharacter DTO
     * @param character The GameCharacter entity to convert
     * @return A ReadCharacter DTO containing the character's details
     */
    ReadCharacter convertToDTO(GameCharacter character);

    /* Find all characters that are not deleted
     * @return A list of ReadCharacter DTOs containing the details of all characters
     */
    List<ReadCharacter> getAllCharacters();

    /* Find a character by its ID that is not deleted
     * @param id The ID of the character
     * @return A ReadCharacter DTO containing the character's details if found, or null if not found or deleted
     */
    ReadCharacter getCharacterById(Long id);

    /*
        * Find all characters that are not deleted with pagination
        * @param page The page number to retrieve
        * @param size The number of characters per page
        * @return A Page of ReadCharacter DTOs containing the details of characters
     */
    Page<ReadCharacter> getAllCharactersPaginated(int page, int size, String sortBy, String sortDirection);

    /*     * Search and filter characters based on a search term and filter criteria
     * @param searchTerm The term to search for in character names or descriptions
     * @param filter The filter criteria to apply (e.g., type, classification)
     * @param page The page number to retrieve
     * @param size The number of characters per page
     * @param sortBy The field to sort by
     * @param sortDirection The direction of sorting (asc or desc)
     * @return A Page of ReadCharacter DTOs containing the filtered and sorted characters
     */
    Page<ReadCharacter> searchAndFilterCharacters(String searchTerm, FilterCharacter filter, int page, int size, String sortBy, String sortDirection);

    /*     * Create a new character from CreateCharacter DTO
     * @param createRequest The CreateCharacter DTO containing the character's details
     * @return A ReadCharacter DTO containing the created character's details
     */
    ReadCharacter createCharacter(CreateCharacter createRequest);

    /*     * Create a hero character from CreateCharacter DTO
     * @param createRequest The CreateCharacter DTO containing the hero's details
     * @return A ReadCharacter DTO containing the created hero's details
     */
    ReadCharacter createHero(CreateCharacter createRequest);

    /*     * Create a villain character from CreateCharacter DTO
     * @param createRequest The CreateCharacter DTO containing the villain's details
     * @return A ReadCharacter DTO containing the created villain's details
     */
    ReadCharacter createVillain(CreateCharacter createRequest);

    /*     * Update an existing character by ID with new details from UpdateCharacter DTO
     * @param id The ID of the character to update
     * @param updateRequest The UpdateCharacter DTO containing the new character details
     * @return A ReadCharacter DTO containing the updated character's details
     */
    ReadCharacter updateCharacter(Long id, UpdateCharacter updateRequest);

    /*     * Update the sprite of an existing character by ID
     * @param id The ID of the character to update
     * @param file The new sprite file to upload
     * @return A ReadCharacter DTO containing the updated character's details with the new sprite path
     */
    ReadCharacter updateCharacterSprite(Long id, MultipartFile file);

    /*     * Soft delete a character by ID (mark as deleted without removing from the database)
     * @param id The ID of the character to soft delete
     */
    void softDeleteCharacter(Long id);

    /*     * Hard delete a character by ID (remove from the database permanently)
     * @param id The ID of the character to hard delete
     */
    void hardDeleteCharacter(Long id);
}