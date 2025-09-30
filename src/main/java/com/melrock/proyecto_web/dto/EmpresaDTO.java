package com.melrock.proyecto_web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpresaDTO {

    private Long idEmpresa;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre de la empresa debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El NIT es obligatorio")
    @Size(min = 5, max = 20, message = "El NIT debe tener entre 5 y 20 caracteres")
    private String nit;

    @NotBlank(message = "El correo de contacto es obligatorio")
    @Size(max = 100, message = "El correo de contacto no debe exceder los 100 caracteres")
    private String correoContacto;
}
