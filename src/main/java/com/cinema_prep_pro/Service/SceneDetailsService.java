package com.cinema_prep_pro.Service;

import com.cinema_prep_pro.Entity.ProjectDetails;
import com.cinema_prep_pro.Entity.SceneDetails;
import com.cinema_prep_pro.Repository.ProjectDetailsRepository;
import com.cinema_prep_pro.Repository.SceneDetailsRepository;
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

    public ResponseEntity<SceneDetails> createNewScene(SceneDetails sceneDetails) {
        try {
            sceneDetails.setCreatedOn(LocalDateTime.now());
            return new ResponseEntity<>(sceneDetailsRepository.save(sceneDetails), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<SceneDetails> updateScene(Map<String, Object> updateDetails) {
        try {
            SceneDetails existingScene = sceneDetailsRepository.findById(updateDetails.get("sceneId").toString()).orElseThrow();
            Field field=null;
            for(Map.Entry<String, Object> entrySet : updateDetails.entrySet())
            {
                field = SceneDetails.class.getDeclaredField(entrySet.getKey());
                field.setAccessible(true);
                field.set(existingScene,entrySet.getValue());
            }
            existingScene.setUpdatedOn(LocalDateTime.now());
            return new ResponseEntity<>(sceneDetailsRepository.save(existingScene),HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteScene(Map<String, String> sceneId) {
        try{
            sceneDetailsRepository.deleteById(sceneId.get("sceneId"));
            return new ResponseEntity<>("Scene Deleted", HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Couldn't delete the Scene - "+sceneId.get("sceneId"),HttpStatus.BAD_REQUEST);
        }
    }
}
