package com.example.todo.functions.characterMaster.entity;

import com.example.todo.functions.characterMaster.enums.CharacterType;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "character_classes")
public class GameCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private CharacterType type;

    @Column(length = 50)
    private String classification;

    @Column(name = "sprite_path", length = 255)
    private String spritePath;


    //base stats
    @Column(name = "base_health", columnDefinition = "INT DEFAULT 100")
    private Integer baseHealth = 100;

    @Column(name = "base_attack", columnDefinition = "INT DEFAULT 10")
    private Integer baseAttack = 10;

    @Column(name = "base_magic", columnDefinition = "INT DEFAULT 10")
    private Integer baseMagic = 10;

    @Column(name = "base_physical_defense", columnDefinition = "INT DEFAULT 5")
    private Integer basePhysicalDefense = 5;

    @Column(name = "base_magical_defense", columnDefinition = "INT DEFAULT 5")
    private Integer baseMagicalDefense = 5;

    @Column(name = "base_speed", columnDefinition = "INT DEFAULT 10")
    private Integer baseSpeed = 10;


    //utils
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;


}
