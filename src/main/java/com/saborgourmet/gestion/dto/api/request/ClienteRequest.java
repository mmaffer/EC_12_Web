package com.saborgourmet.gestion.dto.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotBlank @Size(min = 8, max = 8) String dni,
        @NotBlank String nombres,
        @NotBlank String apellidos,
        String telefono,
        @Email String correo
) {
}

