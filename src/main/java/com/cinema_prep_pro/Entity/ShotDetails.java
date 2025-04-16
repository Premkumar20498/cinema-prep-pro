package com.cinema_prep_pro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShotDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String shotId;

    String shotNo;

    String shotDescription;

    String shotSize;

    String shotType;

    String movement;

    String subject;

    String estTime;

    @ManyToOne
    @JoinColumn(name = "scene_id")
    SceneDetails sceneDetails;
}
