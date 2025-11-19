package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Bitacora;

import java.time.LocalDateTime;

public record BitacoraResponse(
        Long id,
        String usuario,
        String accion,
        String detalle,
        LocalDateTime fechaHora
) {
    public static BitacoraResponse from(Bitacora bitacora) {
        return new BitacoraResponse(
                bitacora.getId(),
                bitacora.getUsuario() != null ? bitacora.getUsuario().getNombreUsuario() : "Sistema",
                bitacora.getAccion(),
                bitacora.getDetalle(),
                bitacora.getFechaHora()
        );
    }
}

