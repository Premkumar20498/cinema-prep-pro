package com.cinema_prep_pro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SceneDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String sceneId;

    String sceneNo;

    String sceneDescription;

    String locationType;

    String location;

    String createdBy;

    LocalDateTime createdOn;

    String updatedBy;

    LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "project_id")
    ProjectDetails projectDetails;

    @OneToMany(mappedBy = "sceneDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany(mappedBy = "sceneDetails")
    List<ShotDetails> shotDetails = new ArrayList<>();
}
