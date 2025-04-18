package com.cinema_prep_pro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
