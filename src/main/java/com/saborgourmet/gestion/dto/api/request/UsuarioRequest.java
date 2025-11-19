package com.saborgourmet.gestion.dto.api.request;

import com.saborgourmet.gestion.model.enums.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotNull Rol rol
) {
}

