package com.saborgourmet.gestion.dto.api.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequest(
        @NotNull Long mesaId,
        Long clienteId,
        @NotNull List<PedidoItemRequest> items
) {
}

