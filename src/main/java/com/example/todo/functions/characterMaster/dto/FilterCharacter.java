package com.example.todo.functions.characterMaster.dto;

import com.example.todo.functions.characterMaster.enums.CharacterClassification;
import com.example.todo.functions.characterMaster.enums.CharacterType;
import lombok.Data;

@Data
public class FilterCharacter {

    private String name;

    private CharacterType type;
    private CharacterClassification classification;

    private Integer minBaseHealth;
    private Integer maxBaseHealth;
    private Integer minBaseAttack;
    private Integer maxBaseAttack;
    private Integer minBaseMagic;
    private Integer maxBaseMagic;
    private Integer minBasePhysicalDefense;
    private Integer maxBasePhysicalDefense;
    private Integer minBaseMagicalDefense;
    private Integer maxBaseMagicalDefense;
    private Integer minBaseSpeed;
    private Integer maxBaseSpeed;

}
