package com.cinema_prep_pro.Service;

import com.cinema_prep_pro.Entity.ProjectDetails;
import com.cinema_prep_pro.Entity.SceneDetails;
import com.cinema_prep_pro.Entity.ShotDetails;
import com.cinema_prep_pro.Repository.ProjectDetailsRepository;
import com.cinema_prep_pro.Requests.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectDetailsService {

    ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    public ProjectDetailsService(ProjectDetailsRepository projectDetailsRepository) {
        this.projectDetailsRepository = projectDetailsRepository;
    }

    public ResponseEntity<List<ProjectDetails>> getProjectsOfCurrentUser(String userName) {
        try {
            return new ResponseEntity<>(projectDetailsRepository.findByCreatedBy(userName), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ProjectDetails> createNewProject(ProjectRequest projectDetails) {
        try {
            int projectNumber = 0;
            ProjectDetails projectToBeAdded = new ProjectDetails();

            ProjectDetails existingProject = projectDetailsRepository.findByProjectName(projectDetails.getProjectName());
            while (existingProject != null) {
                projectNumber++;
                projectDetails.setProjectName(existingProject.getProjectName().split("_")[0] + "_" + projectNumber);
                existingProject = projectDetailsRepository.findByProjectName(projectDetails.getProjectName());
            }

            projectToBeAdded.setProjectName(projectDetails.getProjectName());
            projectToBeAdded.setProjectDescription(projectDetails.getProjectDescription());
            projectToBeAdded.setCreatedOn(LocalDateTime.now());
            projectToBeAdded.setCreatedBy(projectDetails.getCreatedBy());

            return new ResponseEntity<>(projectDetailsRepository.save(projectToBeAdded), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ProjectDetails(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ProjectDetails> updateProject(Map<String, String> updateDetails) {
        try {
            ProjectDetails existingProject = projectDetailsRepository.findById(updateDetails.get("projectId")).orElseThrow();
            Field field;
            for (Map.Entry<String, String> entrySet : updateDetails.entrySet()) {
                field = ProjectDetails.class.getDeclaredField(entrySet.getKey());
                field.setAccessible(true);
                field.set(existingProject, entrySet.getValue());
            }
            existingProject.setUpdatedOn(LocalDateTime.now());
            return new ResponseEntity<>(projectDetailsRepository.save(existingProject), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteProject(String projectId) {
        try {
            projectDetailsRepository.deleteById(projectId);
            return new ResponseEntity<>("Project Deleted", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
