package com.example.todo.functions.characterMaster.service;

import com.example.todo.functions.characterMaster.dto.CharacterDTO;
import com.example.todo.functions.characterMaster.entity.GameCharacter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CharacterService {

    List<CharacterDTO> getAllCharacters();

    Page<CharacterDTO> getAllCharactersPaginated(int page, int size, String sortBy, String sortDirection);

    CharacterDTO convertToDTO(GameCharacter character);
}