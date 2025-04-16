package com.cinema_prep_pro.Requests;


import lombok.Data;

@Data
public class SceneRequest {
    String projectId;

    String sceneNo;

    String sceneDescription;

    String locationType;

    String location;

    String createdBy;
}
