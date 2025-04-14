package com.cinema_prep_pro.Service;

import com.cinema_prep_pro.Entity.ShotDetails;
import com.cinema_prep_pro.Repository.ShotDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShotDetailsService {

    @Autowired
    ShotDetailsRepository shotDetailsRepository;

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

    public ResponseEntity<ShotDetails> createNewShot(ShotDetails shotDetails) {
        try
        {
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
