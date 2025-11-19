package com.saborgourmet.gestion.dto.api.request;

import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import com.saborgourmet.gestion.model.enums.TipoPlato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PlatoRequest(
        @NotBlank String nombre,
        @NotNull TipoPlato tipo,
        @NotNull BigDecimal precio,
        String descripcion,
        @NotNull EstadoRegistro estado
) {
}

