package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Usuario;
import com.saborgourmet.gestion.model.enums.Rol;

public record UsuarioResponse(
        Long id,
        String nombreUsuario,
        Rol rol,
        boolean activo
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getRol(),
                usuario.isActivo()
        );
    }
}

