package com.saborgourmet.gestion.dto.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PedidoItemRequest(
        @NotNull Long platoId,
        @NotNull @Min(1) Integer cantidad
) {
}

