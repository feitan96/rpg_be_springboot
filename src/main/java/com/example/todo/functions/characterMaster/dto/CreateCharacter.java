package com.example.todo.functions.characterMaster.dto;

import com.example.todo.functions.characterMaster.enums.CharacterType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCharacter {

    private String name;
    private CharacterType type;
    private String description;
    private String classification;
    private String spritePath;

    private Integer baseHealth = 100;
    private Integer baseAttack = 10;
    private Integer baseMagic = 10;
    private Integer basePhysicalDefense = 5;
    private Integer baseMagicalDefense = 5;
    private Integer baseSpeed = 10;

}
