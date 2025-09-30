package com.melrock.proyecto_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gateways")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gateway")
    private Long idGateway;

    @Column(nullable = false)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "id_proceso", nullable = false)
    private Proceso proceso;
}
