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

    public ResponseEntity<String> createNewShot(ShotRequest shotDetailsFromRequest) {
        ShotDetails shotDetails = new ShotDetails();
        try
        {
            SceneDetails sceneDetail = sceneDetailsRepository.findById(shotDetailsFromRequest.getSceneId()).orElseThrow(()-> new NoSuchElementException("Scene Id Not Found"));

            shotDetails.setShotNo(shotDetailsFromRequest.getShotNo());
            shotDetails.setShotDescription(shotDetailsFromRequest.getShotDescription());
            shotDetails.setShotSize(shotDetailsFromRequest.getShotSize());
            shotDetails.setShotType(shotDetailsFromRequest.getShotType());
            shotDetails.setMovement(shotDetailsFromRequest.getMovement());
            shotDetails.setSubject(shotDetailsFromRequest.getSubject());
            shotDetails.setEstTime(shotDetailsFromRequest.getEstTime());
            shotDetails.setSceneDetails(sceneDetail);
            shotDetailsRepository.save(shotDetails);
            return new ResponseEntity<>("Shot - "+shotDetailsFromRequest.getShotNo()+" is created successfully", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to create the Shot - "+shotDetailsFromRequest.getShotNo(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateShot(Map<String, String> updateDetails) {
        String shotNumber = "";
        try {
            ShotDetails existingShot = shotDetailsRepository.findById(updateDetails.get("shotId")).orElseThrow();
            shotNumber = existingShot.getShotNo();
            Field field;
            for(Map.Entry<String, String> entrySet : updateDetails.entrySet())
            {
                field = ShotDetails.class.getDeclaredField(entrySet.getKey());
                field.setAccessible(true);
                field.set(existingShot,entrySet.getValue());
            }
            shotDetailsRepository.save(existingShot);
            return new ResponseEntity<>("Shot - "+shotNumber+" is updated successfully", HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to update the Shot - "+shotNumber, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteShot(Map<String, String> deleteDetail) {

        String shotNumber = "";
        try
        {
            String shotIdToBeDeleted = deleteDetail.get("shotId");
            shotNumber = shotDetailsRepository.findById(shotIdToBeDeleted).orElseThrow(()->new NoSuchElementException("Shot not found")).getShotNo();
            shotDetailsRepository.deleteById(shotIdToBeDeleted);
            return new ResponseEntity<>("Shot "+shotNumber+" is deleted successfully", HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("Shot "+shotNumber+" is not deleted", HttpStatus.BAD_REQUEST);
        }
    }
}
