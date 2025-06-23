package com.example.todo.functions.characterMaster.dto;

import com.example.todo.functions.characterMaster.enums.CharacterType;
import lombok.Data;

@Data
public class UpdateCharacter {
    private Long id;
    private String name;
    private String description;
    private CharacterType type;
    private String classification;
    private String spritePath;

    private Integer baseHealth;
    private Integer baseAttack;
    private Integer baseMagic;
    private Integer basePhysicalDefense;
    private Integer baseMagicalDefense;
    private Integer baseSpeed;

}
