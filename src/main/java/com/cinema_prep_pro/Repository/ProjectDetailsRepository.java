package com.cinema_prep_pro.Repository;

import com.cinema_prep_pro.Entity.ProjectDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, String> {
    List<ProjectDetails> findByCreatedBy(String userName);

    ProjectDetails findByProjectName(String projectName);

//    @Modifying
//    @Query(value = "select * from project_details where project_id:projectId", nativeQuery = true)
//    ProjectDetails getSceneListOfProject(@Param("projectId") String projectId);
}
