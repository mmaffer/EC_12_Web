package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.aop.Monitored;
import com.saborgourmet.gestion.model.Compra;
import com.saborgourmet.gestion.model.DetalleCompra;
import com.saborgourmet.gestion.repository.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final InsumoService insumoService;

    public List<Compra> listar() {
        return compraRepository.findAll();
    }

    public Compra obtenerPorId(Long id) {
        return compraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Compra no encontrada"));
    }

    @Auditable(modulo = "Compras", accion = "Registrar compra")
    @Transactional
    @Monitored("Registro de compras")
    public Compra registrarCompra(Compra compra) {
        compra.setFechaCompra(LocalDate.now());
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleCompra detalle : compra.getDetalles()) {
            detalle.setCompra(compra);
            if (detalle.getPrecioUnitario() != null) {
                BigDecimal subtotal = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
                detalle.setSubtotal(subtotal);
                total = total.add(subtotal);
            }
        }
        compra.setTotal(total);
        Compra guardada = compraRepository.save(compra);
        for (DetalleCompra detalle : guardada.getDetalles()) {
            insumoService.actualizarStock(detalle.getInsumo().getId(), detalle.getCantidad());
        }
        return guardada;
    }
}

