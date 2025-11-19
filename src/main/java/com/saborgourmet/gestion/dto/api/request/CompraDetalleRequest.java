package com.saborgourmet.gestion.dto.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CompraDetalleRequest(
        @NotNull Long insumoId,
        @NotNull @DecimalMin("0.1") Double cantidad,
        @NotNull @DecimalMin("0.1") BigDecimal precioUnitario
) {
}

