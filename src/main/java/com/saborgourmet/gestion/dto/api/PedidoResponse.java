package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Pedido;
import com.saborgourmet.gestion.model.enums.EstadoPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        Long mesaId,
        Integer mesaNumero,
        Long clienteId,
        String clienteNombre,
        LocalDateTime fechaHora,
        EstadoPedido estado,
        BigDecimal total,
        List<DetallePedidoResponse> detalles,
        FacturaSummaryResponse factura
) {
    public static PedidoResponse from(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getMesa() != null ? pedido.getMesa().getId() : null,
                pedido.getMesa() != null ? pedido.getMesa().getNumero() : null,
                pedido.getCliente() != null ? pedido.getCliente().getId() : null,
                pedido.getCliente() != null ? pedido.getCliente().getNombres() + " " + pedido.getCliente().getApellidos() : null,
                pedido.getFechaHora(),
                pedido.getEstado(),
                pedido.getTotal(),
                pedido.getDetalles().stream().map(DetallePedidoResponse::from).toList(),
                pedido.getFactura() != null ? FacturaSummaryResponse.from(pedido.getFactura()) : null
        );
    }
}

