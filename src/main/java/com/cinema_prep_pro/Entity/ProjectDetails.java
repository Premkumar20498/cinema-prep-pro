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
public class ProjectDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String projectId;

    String projectName;

    String projectDescription;

    String createdBy;

    LocalDateTime createdOn;

    String updatedBy;

    LocalDateTime updatedOn;

    @OneToMany(mappedBy = "projectDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<SceneDetails> sceneDetails = new ArrayList<>();
}
