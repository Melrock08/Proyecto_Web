package com.melrock.proyecto_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "actividad")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActividad;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo; // manual, automática, de usuario, etc.

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_proceso", nullable = false)
    private Proceso proceso;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
}
