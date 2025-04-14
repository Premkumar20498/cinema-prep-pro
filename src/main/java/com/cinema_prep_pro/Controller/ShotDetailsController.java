package com.cinema_prep_pro.Controller;

import com.cinema_prep_pro.Entity.ShotDetails;
import com.cinema_prep_pro.Service.ShotDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("shot")
public class ShotDetailsController {

    @Autowired
    ShotDetailsService shotDetailsService;

    @GetMapping("allShots")
    ResponseEntity<List<ShotDetails>> getAllShots()
    {
        return shotDetailsService.getAllShots();
    }

    @PostMapping("create")
    ResponseEntity<ShotDetails> createNewShot(@RequestBody ShotDetails shotDetails)
    {
        return shotDetailsService.createNewShot(shotDetails);
    }

    @PatchMapping("update")
    ResponseEntity<ShotDetails> updateShot(@RequestBody Map<String, String> updateDetails)
    {
        return shotDetailsService.updateShot(updateDetails);
    }

    @DeleteMapping("delete")
    ResponseEntity<String> deleteShot(@RequestBody Map<String, String > deleteDetail)
    {
        return shotDetailsService.deleteShot(deleteDetail);
    }
}
