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
import java.util.*;

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

    public ResponseEntity<String> createNewProject(ProjectRequest projectDetails) {
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
            projectDetailsRepository.save(projectToBeAdded);
            return new ResponseEntity<>("Project \""+projectDetails.getProjectName()+"\" is created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to create the project with name - "+projectDetails.getProjectName(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateProject(Map<String, String> updateDetails) {
        ProjectDetails existingProject = new ProjectDetails();
        try {
            existingProject = projectDetailsRepository.findById(updateDetails.get("projectId")).orElseThrow();
            Field field;
            for (Map.Entry<String, String> entrySet : updateDetails.entrySet()) {
                field = ProjectDetails.class.getDeclaredField(entrySet.getKey());
                field.setAccessible(true);
                field.set(existingProject, entrySet.getValue());
            }
            existingProject.setUpdatedOn(LocalDateTime.now());
            projectDetailsRepository.save(existingProject);
            return new ResponseEntity<>("\""+existingProject.getProjectName()+"\" is updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Unable to update the project \""+existingProject.getProjectName()+"\"", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteProject(Map<String,String> projectId) {
        String projectName="";
        try {
            String projectIdToBeDeleted = projectId.get("projectId");
            projectName = projectDetailsRepository.findById(projectIdToBeDeleted).orElseThrow(()->new NoSuchElementException("Project Not Found")).getProjectName();
            projectDetailsRepository.deleteById(projectIdToBeDeleted);
            return new ResponseEntity<>("\""+projectName+"\" is Deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to delete \""+projectName+"\"", HttpStatus.BAD_REQUEST);
        }
    }
}
