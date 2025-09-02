package com.melrock.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;
    private String name;
    private Double x;
    private Double y;
    private String description;
    private Double width;
    private Double height;
    private String status;
    private Long processId; // relación simplificada
}
