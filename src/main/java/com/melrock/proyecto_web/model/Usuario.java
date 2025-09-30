package com.melrock.proyecto_web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    // nota: comillas dobles dentro de la cadena para que el nombre en DDL sea "contraseña"
    @Column(name = "\"contraseña\"", nullable = false)
    private String contraseña;

    @Column(nullable = false)
    private String rolSistema;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;
}
