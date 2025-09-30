package com.melrock.proyecto_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "edges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_edge")
    private Long idEdge;

    @ManyToOne
    @JoinColumn(name = "id_proceso", nullable = false)
    private Proceso proceso;

    // Origen: puede ser Actividad o Gateway (uno u otro en la fila)
    @ManyToOne
    @JoinColumn(name = "id_origen_actividad")
    private Actividad origenActividad;

    @ManyToOne
    @JoinColumn(name = "id_origen_gateway")
    private Gateway origenGateway;

    // Destino
    @ManyToOne
    @JoinColumn(name = "id_destino_actividad")
    private Actividad destinoActividad;

    @ManyToOne
    @JoinColumn(name = "id_destino_gateway")
    private Gateway destinoGateway;
}
