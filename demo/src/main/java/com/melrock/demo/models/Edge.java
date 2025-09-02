package com.melrock.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data                       
@NoArgsConstructor          
@AllArgsConstructor         
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Activity source;

    @ManyToOne
    @JoinColumn(name = "destiny_id")
    private Activity destiny;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;
}
