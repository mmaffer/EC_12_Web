package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Mesa;
import com.saborgourmet.gestion.model.enums.EstadoMesa;

public record MesaResponse(
        Long id,
        Integer numero,
        Integer capacidad,
        EstadoMesa estado
) {
    public static MesaResponse from(Mesa mesa) {
        return new MesaResponse(
                mesa.getId(),
                mesa.getNumero(),
                mesa.getCapacidad(),
                mesa.getEstado()
        );
    }
}

