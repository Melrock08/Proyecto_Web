package com.melrock.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data                      
@NoArgsConstructor          
@AllArgsConstructor         
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double x;
    private Double y;
    private String description;
    private Double width;
    private Double height;
    private String status;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;
}
