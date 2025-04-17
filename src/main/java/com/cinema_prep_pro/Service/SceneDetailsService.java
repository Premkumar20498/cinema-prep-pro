package com.cinema_prep_pro.Service;

import com.cinema_prep_pro.Entity.ProjectDetails;
import com.cinema_prep_pro.Entity.SceneDetails;
import com.cinema_prep_pro.Repository.ProjectDetailsRepository;
import com.cinema_prep_pro.Repository.SceneDetailsRepository;
import com.cinema_prep_pro.Requests.SceneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SceneDetailsService {
    @Autowired
    SceneDetailsRepository sceneDetailsRepository;

    @Autowired
    ProjectDetailsRepository projectDetailsRepository;

    public ResponseEntity<List<SceneDetails>> getScenesOfTheProject(Map<String, String> projectId) {
        List<String> sceneIdList;
        List<SceneDetails> sceneDetails = new ArrayList<>();
        try {
//            sceneIdList = projectDetailsRepository.getSceneListOfProject(projectId.get("projectId")).getSceneIds();
//
//            for(String sceneId : sceneIdList)
//            {
//                sceneDetails.add(sceneDetailsRepository.findById(sceneId).get());
//            }
            return new ResponseEntity<>(sceneDetails, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(sceneDetails, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> createNewScene(SceneRequest sceneDetails) {
        SceneDetails sceneToBeAdded = new SceneDetails();
        try {
            ProjectDetails projectDetails = projectDetailsRepository.findById(sceneDetails.getProjectId()).orElseThrow(()-> new NoSuchElementException("Project Id not found"));

            sceneToBeAdded.setSceneNo(sceneDetails.getSceneNo());
            sceneToBeAdded.setSceneDescription(sceneDetails.getSceneDescription());
            sceneToBeAdded.setLocationType(sceneDetails.getLocationType());
            sceneToBeAdded.setLocation(sceneDetails.getLocation());
            sceneToBeAdded.setCreatedBy(sceneDetails.getCreatedBy());
            sceneToBeAdded.setCreatedOn(LocalDateTime.now());
            sceneToBeAdded.setProjectDetails(projectDetails);

            sceneDetailsRepository.save(sceneToBeAdded);

            return new ResponseEntity<>("Scene - "+sceneDetails.getSceneNo()+" is created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to create the Scene - "+sceneDetails.getSceneNo(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateScene(Map<String, Object> updateDetails) {
        String sceneNumber = "";
        try {
            SceneDetails existingScene = sceneDetailsRepository.findById(updateDetails.get("sceneId").toString()).orElseThrow();
            sceneNumber = existingScene.getSceneNo();
            Field field=null;
            for(Map.Entry<String, Object> entrySet : updateDetails.entrySet())
            {
                field = SceneDetails.class.getDeclaredField(entrySet.getKey());
                field.setAccessible(true);
                field.set(existingScene,entrySet.getValue());
            }
            existingScene.setUpdatedOn(LocalDateTime.now());
            sceneDetailsRepository.save(existingScene);
            return new ResponseEntity<>("Scene - "+sceneNumber+" is updated successfully",HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to update the Scene - "+sceneNumber, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteScene(Map<String, String> sceneId) {
        String sceneNumber = "";
        try{
            String sceneIdToBeDeleted = sceneId.get("sceneId");
            sceneNumber = sceneDetailsRepository.findById(sceneIdToBeDeleted).orElseThrow(()->new NoSuchElementException("Scene Id not found")).getSceneNo();
            sceneDetailsRepository.deleteById(sceneIdToBeDeleted);
            return new ResponseEntity<>("Scene - "+sceneNumber+" Deleted", HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to delete the Scene - "+sceneNumber,HttpStatus.BAD_REQUEST);
        }
    }
}
