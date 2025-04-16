package com.cinema_prep_pro.Requests;

import lombok.Data;

@Data
public class ShotRequest {
    String sceneId;

    String shotNo;

    String shotDescription;

    String shotSize;

    String shotType;

    String movement;

    String subject;

    String estTime;
}
