package com.cinema_prep_pro.Service;

import com.cinema_prep_pro.Entity.SceneDetails;
import com.cinema_prep_pro.Entity.ShotDetails;
import com.cinema_prep_pro.Repository.SceneDetailsRepository;
import com.cinema_prep_pro.Repository.ShotDetailsRepository;
import com.cinema_prep_pro.Requests.ShotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ShotDetailsService {

    @Autowired
    ShotDetailsRepository shotDetailsRepository;

    @Autowired
    SceneDetailsRepository sceneDetailsRepository;

    public ResponseEntity<List<ShotDetails>> getAllShots() {
        try
        {
            return new ResponseEntity<>(shotDetailsRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ShotDetails> createNewShot(ShotRequest shotDetailsFromRequest) {
        try
        {
            ShotDetails shotDetails = new ShotDetails();
            SceneDetails sceneDetail = sceneDetailsRepository.findById(shotDetailsFromRequest.getSceneId()).orElseThrow(()-> new NoSuchElementException("Scene Id Not Found"));

            shotDetails.setShotNo(shotDetailsFromRequest.getShotNo());
            shotDetails.setShotDescription(shotDetailsFromRequest.getShotDescription());
            shotDetails.setShotSize(shotDetailsFromRequest.getShotSize());
            shotDetails.setShotType(shotDetailsFromRequest.getShotType());
            shotDetails.setMovement(shotDetailsFromRequest.getMovement());
            shotDetails.setSubject(shotDetailsFromRequest.getSubject());
            shotDetails.setEstTime(shotDetailsFromRequest.getEstTime());
            shotDetails.setSceneDetails(sceneDetail);

            return new ResponseEntity<>(shotDetailsRepository.save(shotDetails), HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ShotDetails> updateShot(Map<String, String> updateDetails) {

        try {
            ShotDetails existingShot = shotDetailsRepository.findById(updateDetails.get("shotId")).orElseThrow();
            Field field;
            for(Map.Entry<String, String> entrySet : updateDetails.entrySet())
            {
                field = ShotDetails.class.getDeclaredField(entrySet.getKey());
                field.setAccessible(true);
                field.set(existingShot,entrySet.getValue());
            }
            return new ResponseEntity<>(shotDetailsRepository.save(existingShot), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteShot(Map<String, String> deleteDetail) {

        try
        {
            shotDetailsRepository.deleteById(deleteDetail.get("shotId"));
            return new ResponseEntity<>("Shot Deleted", HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Shot not Deleted", HttpStatus.BAD_REQUEST);
        }
    }
}
