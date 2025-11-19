package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Insumo;
import com.saborgourmet.gestion.model.enums.EstadoRegistro;

import java.math.BigDecimal;

public record InsumoResponse(
        Long id,
        String nombre,
        String unidadMedida,
        Double stock,
        Double stockMinimo,
        BigDecimal precioCompra,
        EstadoRegistro estado
) {
    public static InsumoResponse from(Insumo insumo) {
        return new InsumoResponse(
                insumo.getId(),
                insumo.getNombre(),
                insumo.getUnidadMedida(),
                insumo.getStock(),
                insumo.getStockMinimo(),
                insumo.getPrecioCompra(),
                insumo.getEstado()
        );
    }
}

