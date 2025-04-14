package com.cinema_prep_pro.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShotDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String shotId;

    String shotNumber;

    String shotDescription;

    String shotSize;

    String shotType;

    String movement;

    String subject;

    String estTime;
}
