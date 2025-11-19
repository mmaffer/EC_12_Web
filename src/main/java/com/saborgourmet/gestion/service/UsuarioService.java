package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.model.Usuario;
import com.saborgourmet.gestion.model.enums.Rol;
import com.saborgourmet.gestion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public Usuario buscarPorNombreUsuario(String username) {
        return usuarioRepository.findByNombreUsuario(username).orElse(null);
    }

    @Auditable(modulo = "Seguridad", accion = "Registrar usuario")
    public Usuario crearUsuario(String username, String password, Rol rol) {
        Usuario usuario = Usuario.builder()
                .nombreUsuario(username)
                .contrasena(passwordEncoder.encode(password))
                .rol(rol)
                .activo(true)
                .build();
        return usuarioRepository.save(usuario);
    }
}

