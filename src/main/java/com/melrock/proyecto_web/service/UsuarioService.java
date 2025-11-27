package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.dto.UsuarioRegistroDTO;
import com.melrock.proyecto_web.model.Usuario;
import com.melrock.proyecto_web.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO registrarUsuarioConEmpresaId(UsuarioRegistroDTO dto, Long idEmpresa) {
        if (dto == null) throw new IllegalArgumentException("Datos de registro inválidos");
        if (idEmpresa == null) throw new IllegalArgumentException("idEmpresa es requerido");

        if (dto.getCorreo() == null || dto.getCorreo().isBlank()) {
            throw new IllegalArgumentException("Correo es obligatorio");
        }

        if (usuarioRepository.findByCorreo(dto.getCorreo()) != null) {
            throw new IllegalArgumentException("El correo ya está en uso");
        }

        if (!"ADMIN".equalsIgnoreCase(dto.getRolSistema())) {
            throw new IllegalArgumentException("Solo los usuarios ADMIN pueden registrarse directamente.");
        }

        // Mapear usuario y asignar referencia a Empresa solo con id
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRolSistema(dto.getRolSistema());

        var empresaRef = new com.melrock.proyecto_web.model.Empresa();
        empresaRef.setIdEmpresa(idEmpresa);
        usuario.setEmpresa(empresaRef);

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    // LOGIN y demás métodos (mantener tu implementación)
    public UsuarioDTO login(String correo, String contrasena) {
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("Correo es obligatorio");
        }
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return convertirADTO(usuario);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(this::convertirADTO);
    }

    public UsuarioDTO buscarPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null)
            throw new IllegalArgumentException("Usuario no encontrado");
        return convertirADTO(usuario);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        if (usuario.getEmpresa() != null) {
            dto.setIdEmpresa(usuario.getEmpresa().getIdEmpresa());
        }
        return dto;
    }
}
