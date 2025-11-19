package com.saborgourmet.gestion.dto.api.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CompraRequest(
        @NotNull Long proveedorId,
        @NotNull List<CompraDetalleRequest> detalles
) {
}

