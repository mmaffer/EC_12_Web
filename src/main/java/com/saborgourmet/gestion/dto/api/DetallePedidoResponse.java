package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.DetallePedido;

import java.math.BigDecimal;

public record DetallePedidoResponse(
        Long id,
        Long platoId,
        String platoNombre,
        Integer cantidad,
        BigDecimal subtotal
) {
    public static DetallePedidoResponse from(DetallePedido detalle) {
        return new DetallePedidoResponse(
                detalle.getId(),
                detalle.getPlato().getId(),
                detalle.getPlato().getNombre(),
                detalle.getCantidad(),
                detalle.getSubtotal()
        );
    }
}

