package com.example.todo.functions.characterMaster.dto;

import lombok.Data;

@Data
public class UpdateCharacter {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String classification;
    private String spritePath;

    private Integer baseHealth;
    private Integer baseAttack;
    private Integer baseMagic;
    private Integer basePhysicalDefense;
    private Integer baseMagicalDefense;
    private Integer baseSpeed;

}
