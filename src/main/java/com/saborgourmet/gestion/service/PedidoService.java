package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.aop.Monitored;
import com.saborgourmet.gestion.model.DetallePedido;
import com.saborgourmet.gestion.model.Pedido;
import com.saborgourmet.gestion.model.PlatoInsumo;
import com.saborgourmet.gestion.model.enums.EstadoMesa;
import com.saborgourmet.gestion.model.enums.EstadoPedido;
import com.saborgourmet.gestion.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaService mesaService;
    private final InsumoService insumoService;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPorEstado(EstadoPedido estadoPedido) {
        return pedidoRepository.findByEstado(estadoPedido);
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    }

    @Auditable(modulo = "Pedidos", accion = "Registrar pedido")
    @Transactional
    @Monitored("Registro de pedidos")
    public Pedido registrarPedido(Pedido pedido) {
        pedido.setFechaHora(LocalDateTime.now());
        if (pedido.getEstado() == null) {
            pedido.setEstado(EstadoPedido.PENDIENTE);
        }
        if (pedido.getMesa() != null) {
            mesaService.cambiarEstado(pedido.getMesa().getId(), EstadoMesa.OCUPADA);
        }
        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.setPedido(pedido);
            if (detalle.getPlato() != null && detalle.getPlato().getPrecio() != null) {
                BigDecimal subtotal = detalle.getPlato().getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad()));
                detalle.setSubtotal(subtotal);
            }
        }
        return pedidoRepository.save(pedido);
    }

    @Auditable(modulo = "Pedidos", accion = "Actualizar estado de pedido")
    @Transactional
    @Monitored("Cambio de estado de pedido")
    public Pedido actualizarEstado(Long pedidoId, EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPorId(pedidoId);
        pedido.setEstado(nuevoEstado);
        if (nuevoEstado == EstadoPedido.CERRADO) {
            actualizarInventarioPorPedido(pedido);
            if (pedido.getMesa() != null) {
                mesaService.cambiarEstado(pedido.getMesa().getId(), EstadoMesa.DISPONIBLE);
            }
        }
        return pedidoRepository.save(pedido);
    }

    private void actualizarInventarioPorPedido(Pedido pedido) {
        pedido.getDetalles().forEach(detalle -> {
            if (detalle.getPlato() == null) {
                return;
            }
            double cantidadPedido = detalle.getCantidad();
            for (PlatoInsumo platoInsumo : detalle.getPlato().getInsumos()) {
                double cantidadConsumida = (platoInsumo.getCantidadUsada() == null ? 0 : platoInsumo.getCantidadUsada()) * cantidadPedido * -1;
                insumoService.actualizarStock(platoInsumo.getInsumo().getId(), cantidadConsumida);
            }
        });
    }
}

