package com.cinema_prep_pro.Repository;

import com.cinema_prep_pro.Entity.ProjectDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, String> {
    List<ProjectDetails> findByCreatedBy(String userName);

    ProjectDetails findByProjectName(String projectName);
}
