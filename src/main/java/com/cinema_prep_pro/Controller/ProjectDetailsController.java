package com.cinema_prep_pro.Controller;

import com.cinema_prep_pro.Entity.ProjectDetails;
import com.cinema_prep_pro.Service.ProjectDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("project")
public class ProjectDetailsController {

    @Autowired
    ProjectDetailsService projectDetailsService;

    @GetMapping("user_projects")
    public ResponseEntity<List<ProjectDetails>> getProjectsOfCurrentUser(@RequestParam("user_name") String userName)
    {
        return projectDetailsService.getProjectsOfCurrentUser(userName);
    }

    @PostMapping("create")
    public ResponseEntity<ProjectDetails> createNewProject(@RequestBody ProjectDetails projectDetails)
    {
        return projectDetailsService.createNewProject(projectDetails);
    }

    @PatchMapping("update")
    public ResponseEntity<ProjectDetails> updateProject(@RequestBody Map<String, String> updateDetails)
    {
        return projectDetailsService.updateProject(updateDetails);
    }

    @DeleteMapping("{project_id}")
    public ResponseEntity<String> deleteProject(@PathVariable("project_id") String projectId)
    {
        return projectDetailsService.deleteProject(projectId);
    }
}
