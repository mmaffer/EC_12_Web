package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Factura;
import com.saborgourmet.gestion.model.enums.EstadoFactura;
import com.saborgourmet.gestion.model.enums.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FacturaSummaryResponse(
        Long id,
        LocalDateTime fechaEmision,
        BigDecimal total,
        MetodoPago metodoPago,
        EstadoFactura estado
) {
    public static FacturaSummaryResponse from(Factura factura) {
        return new FacturaSummaryResponse(
                factura.getId(),
                factura.getFechaEmision(),
                factura.getTotal(),
                factura.getMetodoPago(),
                factura.getEstado()
        );
    }
}

