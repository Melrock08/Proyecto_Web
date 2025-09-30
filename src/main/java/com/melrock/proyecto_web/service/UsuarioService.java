package com.melrock.proyecto_web.service;

import com.melrock.proyecto_web.dto.UsuarioDTO;
import com.melrock.proyecto_web.dto.UsuarioRegistroDTO;
import com.melrock.proyecto_web.model.Usuario;
import com.melrock.proyecto_web.model.Empresa;
import com.melrock.proyecto_web.repository.UsuarioRepository;
import com.melrock.proyecto_web.repository.EmpresaRepository;
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
    private final EmpresaRepository empresaRepository;
    private final ModelMapper modelMapper;

    // Crear usuario dentro de una empresa
    public UsuarioDTO registrarUsuario(UsuarioRegistroDTO dto) {
        if (usuarioRepository.findByCorreo(dto.getCorreo()) != null) {
            throw new RuntimeException("El correo ya está en uso");
        }

        Usuario usuario = modelMapper.map(dto, Usuario.class);

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        usuario.setEmpresa(empresa);

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    // Listar todos los usuarios
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por id
    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(this::convertirADTO);
    }

    // Buscar por correo
    public UsuarioDTO buscarPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        return convertirADTO(usuario);
    }

    // Simulación de login
    public UsuarioDTO login(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return convertirADTO(usuario);
        }
        throw new RuntimeException("Credenciales inválidas o usuario inactivo");
    }

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // Actualizar usuario
    public UsuarioDTO actualizarUsuario(Long id, UsuarioRegistroDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRolSistema(dto.getRolSistema());

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        usuario.setEmpresa(empresa);

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirADTO(actualizado);
    }

    // ===== Método auxiliar =====
    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        if (usuario.getEmpresa() != null) {
            dto.setIdEmpresa(usuario.getEmpresa().getIdEmpresa());
        }
        return dto;
    }
}
