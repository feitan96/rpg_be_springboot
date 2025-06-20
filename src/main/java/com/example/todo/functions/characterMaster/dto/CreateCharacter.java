package com.example.todo.functions.characterMaster.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCharacter {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;

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
