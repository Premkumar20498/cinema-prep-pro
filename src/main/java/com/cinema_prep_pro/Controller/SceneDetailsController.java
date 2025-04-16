package com.cinema_prep_pro.Controller;

import com.cinema_prep_pro.Entity.SceneDetails;
import com.cinema_prep_pro.Requests.SceneRequest;
import com.cinema_prep_pro.Service.SceneDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("scene")
public class SceneDetailsController {

    @Autowired
    SceneDetailsService sceneDetailsService;

    @PostMapping("getAll")
    public ResponseEntity<List<SceneDetails>> getScenesOfProject(@RequestBody Map<String, String> projectId)
    {
        return sceneDetailsService.getScenesOfTheProject(projectId);
    }

    @PostMapping("create")
    public ResponseEntity<SceneDetails> createNewScene(@RequestBody SceneRequest sceneDetails)
    {
        return sceneDetailsService.createNewScene(sceneDetails);
    }

    @PatchMapping("update")
    public ResponseEntity<SceneDetails> updateSceneDetail(@RequestBody Map<String, Object> sceneDetail)
    {
        return sceneDetailsService.updateScene(sceneDetail);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteSceneDetail(@RequestBody Map<String, String> sceneId)
    {
        return sceneDetailsService.deleteScene(sceneId);
    }
}
