package com.melrock.proyecto_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "edge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEdge;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_proceso", nullable = false)
    private Proceso proceso;

    // Origen del arco (puede ser Actividad o Gateway)
    @ManyToOne
    @JoinColumn(name = "id_origen_actividad")
    private Actividad origenActividad;

    @ManyToOne
    @JoinColumn(name = "id_origen_gateway")
    private Gateway origenGateway;

    // Destino del arco (puede ser Actividad o Gateway)
    @ManyToOne
    @JoinColumn(name = "id_destino_actividad")
    private Actividad destinoActividad;

    @ManyToOne
    @JoinColumn(name = "id_destino_gateway")
    private Gateway destinoGateway;
}
