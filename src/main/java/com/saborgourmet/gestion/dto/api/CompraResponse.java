package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Compra;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CompraResponse(
        Long id,
        LocalDate fechaCompra,
        BigDecimal total,
        ProveedorResponse proveedor,
        List<DetalleCompraResponse> detalles
) {
    public static CompraResponse from(Compra compra) {
        return new CompraResponse(
                compra.getId(),
                compra.getFechaCompra(),
                compra.getTotal(),
                compra.getProveedor() == null ? null : ProveedorResponse.from(compra.getProveedor()),
                compra.getDetalles().stream().map(DetalleCompraResponse::from).toList()
        );
    }
}

