package com.example.todo.functions.characterMaster.dto;

import com.example.todo.functions.characterMaster.enums.CharacterClassification;
import com.example.todo.functions.characterMaster.enums.CharacterType;
import lombok.Data;
import java.sql.Timestamp;

/**
 * DTO for reading character details.
 * This class is used to transfer character data from the server to the client.
 */
@Data
public class ReadCharacter {
    private Long id;

    private CharacterType type;
    private CharacterClassification classification;
    
    private String name;
    private String description;
    private String spritePath;

    private Integer baseHealth;
    private Integer baseAttack;
    private Integer baseMagic;
    private Integer basePhysicalDefense;
    private Integer baseMagicalDefense;
    private Integer baseSpeed;

    private Timestamp createdAt;
    private Timestamp updatedAt;
}
