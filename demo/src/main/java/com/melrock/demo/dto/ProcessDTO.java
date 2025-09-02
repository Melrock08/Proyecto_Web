package com.melrock.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
}