package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Factura;
import com.saborgourmet.gestion.model.enums.EstadoFactura;
import com.saborgourmet.gestion.model.enums.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FacturaResponse(
        Long id,
        Long pedidoId,
        LocalDateTime fechaEmision,
        BigDecimal total,
        MetodoPago metodoPago,
        EstadoFactura estado
) {
    public static FacturaResponse from(Factura factura) {
        return new FacturaResponse(
                factura.getId(),
                factura.getPedido() != null ? factura.getPedido().getId() : null,
                factura.getFechaEmision(),
                factura.getTotal(),
                factura.getMetodoPago(),
                factura.getEstado()
        );
    }
}

