package com.example.todo.functions.characterMaster.dto;

import lombok.Data;

/**
 * DTO for deleting a character.
 * This class is used to transfer data from the client to the server when deleting a character.
 */
@Data
public class DeleteCharacter {
    private Long id;
}

