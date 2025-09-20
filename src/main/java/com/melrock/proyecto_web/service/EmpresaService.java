package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.repository.EmpresaRepository;
import com.melrock.proyecto_web.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.model.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;

    public EmpresaService(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Crear empresa con usuario administrador inicial
    public Empresa registrarEmpresa(Empresa empresa, String correoAdmin, String contraseñaAdmin) {
        Empresa nuevaEmpresa = empresaRepository.save(empresa);

        // Crear usuario administrador inicial
        Usuario admin = Usuario.builder()
                .nombre("Administrador")
                .correo(correoAdmin)
                .contraseña(contraseñaAdmin)
                .rolSistema("ADMIN")
                .empresa(nuevaEmpresa)
                .build();

        usuarioRepository.save(admin);

        return nuevaEmpresa;
    }
    
    // Listar todas las empresas
    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    // Buscar empresa por id
    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepository.findById(id);
    }

    // Buscar empresa por NIT
    public Empresa buscarPorNit(String nit) {
        return empresaRepository.findByNit(nit);
    }

    // Eliminar empresa
    public void eliminarEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
}
