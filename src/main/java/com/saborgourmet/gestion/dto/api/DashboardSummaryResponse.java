package com.saborgourmet.gestion.dto.api;

public record DashboardSummaryResponse(
        long clientes,
        long mesasDisponibles,
        long pedidosPendientes,
        long ventas,
        long alertas
) {
}

