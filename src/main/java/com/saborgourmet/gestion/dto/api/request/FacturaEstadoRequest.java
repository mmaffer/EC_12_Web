package com.saborgourmet.gestion.dto.api.request;

import com.saborgourmet.gestion.model.enums.EstadoFactura;
import jakarta.validation.constraints.NotNull;

public record FacturaEstadoRequest(
        @NotNull EstadoFactura estado
) {
}

