package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.aop.Monitored;
import com.saborgourmet.gestion.model.DetalleFactura;
import com.saborgourmet.gestion.model.Factura;
import com.saborgourmet.gestion.model.Pedido;
import com.saborgourmet.gestion.model.enums.EstadoFactura;
import com.saborgourmet.gestion.model.enums.MetodoPago;
import com.saborgourmet.gestion.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public List<Factura> listar() {
        return facturaRepository.findAll();
    }

    public List<Factura> listarPorRango(LocalDate inicio, LocalDate fin) {
        return facturaRepository.findByFechaEmisionBetween(inicio.atStartOfDay(), fin.plusDays(1).atStartOfDay());
    }

    public Factura obtenerPorId(Long id) {
        return facturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
    }

    @Auditable(modulo = "Ventas", accion = "Generar factura")
    @Monitored("Generación de facturas")
    public Factura generarFactura(Pedido pedido, MetodoPago metodoPago, boolean pagado) {
        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setFechaEmision(LocalDateTime.now());
        factura.setMetodoPago(metodoPago);
        factura.setEstado(pagado ? EstadoFactura.PAGADO : EstadoFactura.PENDIENTE);

        BigDecimal total = pedido.getDetalles().stream()
                .map(det -> det.getSubtotal() == null ? BigDecimal.ZERO : det.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        factura.setTotal(total);

        pedido.getDetalles().forEach(det -> {
            DetalleFactura detalleFactura = DetalleFactura.builder()
                    .factura(factura)
                    .concepto(det.getPlato().getNombre())
                    .monto(det.getSubtotal())
                    .build();
            factura.getDetalles().add(detalleFactura);
        });

        return facturaRepository.save(factura);
    }

    @Auditable(modulo = "Ventas", accion = "Actualizar estado de factura")
    public Factura actualizarEstado(Long facturaId, EstadoFactura nuevoEstado) {
        Factura factura = obtenerPorId(facturaId);
        factura.setEstado(nuevoEstado);
        return facturaRepository.save(factura);
    }
}

