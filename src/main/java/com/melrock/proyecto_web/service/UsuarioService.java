package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.dto.UsuarioRegistroDTO;
import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.model.Usuario;
import com.melrock.proyecto_web.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    // 🔹 ID de la empresa existente (por ejemplo la primera en la base de datos)
    private final Long idEmpresaPorDefecto = 1L;

    public UsuarioDTO registrarUsuario(UsuarioRegistroDTO dto) {
        // ✅ Validar correo único
        if (usuarioRepository.findByCorreo(dto.getCorreo()) != null) {
            throw new RuntimeException("El correo ya está en uso");
        }

        // ✅ Solo los usuarios ADMIN pueden registrarse
        if (!"ADMIN".equalsIgnoreCase(dto.getRolSistema())) {
            throw new RuntimeException("Solo los usuarios ADMIN pueden registrarse directamente.");
        }

        // ✅ Crear usuario vinculado a la empresa existente
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(idEmpresaPorDefecto); // Usamos siempre la empresa existente
        usuario.setEmpresa(empresa);

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
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
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        return convertirADTO(usuario);
    }

    public UsuarioDTO login(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        if (!usuario.getContrasena().equals(contrasena))
            throw new RuntimeException("Contraseña incorrecta");

        return convertirADTO(usuario);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
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
