package com.melrock.demo.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data                       
@NoArgsConstructor          
@AllArgsConstructor         
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String status;

    @ManyToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private List<Activity> activities;

    @ManyToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private List<Edge> edges;
}
