package com.cinema_prep_pro.Repository;

import com.cinema_prep_pro.Entity.SceneDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SceneDetailsRepository extends JpaRepository<SceneDetails, String> {
}
