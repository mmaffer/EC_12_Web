package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.DetalleCompra;

import java.math.BigDecimal;

public record DetalleCompraResponse(
        Long id,
        Long insumoId,
        String insumoNombre,
        Double cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
    public static DetalleCompraResponse from(DetalleCompra detalle) {
        return new DetalleCompraResponse(
                detalle.getId(),
                detalle.getInsumo().getId(),
                detalle.getInsumo().getNombre(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }
}

