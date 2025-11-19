package com.saborgourmet.gestion.dto.api.request;

import com.saborgourmet.gestion.model.enums.EstadoPedido;
import com.saborgourmet.gestion.model.enums.MetodoPago;
import jakarta.validation.constraints.NotNull;

public record PedidoEstadoRequest(
        @NotNull EstadoPedido estado,
        MetodoPago metodoPago,
        boolean pagado
) {
}

