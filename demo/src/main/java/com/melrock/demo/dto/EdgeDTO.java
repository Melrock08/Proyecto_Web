package com.melrock.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdgeDTO {
    private Long id;
    private String description;
    private Long activitySourceId;
    private Long activityDestinyId;
    private String status;
    private Long processId; 
}