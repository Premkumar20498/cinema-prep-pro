package com.cinema_prep_pro.Repository;

import com.cinema_prep_pro.Entity.ShotDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShotDetailsRepository extends JpaRepository<ShotDetails, String> {
}
