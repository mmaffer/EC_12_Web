package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Plato;
import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import com.saborgourmet.gestion.model.enums.TipoPlato;

import java.math.BigDecimal;

public record PlatoResponse(
        Long id,
        String nombre,
        TipoPlato tipo,
        BigDecimal precio,
        String descripcion,
        EstadoRegistro estado
) {
    public static PlatoResponse from(Plato plato) {
        return new PlatoResponse(
                plato.getId(),
                plato.getNombre(),
                plato.getTipo(),
                plato.getPrecio(),
                plato.getDescripcion(),
                plato.getEstado()
        );
    }
}

