package com.melrock.proyecto_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gateway")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGateway;

    @Column(nullable = false)
    private String tipo; // exclusivo, paralelo, inclusivo

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_proceso", nullable = false)
    private Proceso proceso;
}