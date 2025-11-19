package com.saborgourmet.gestion.repository;

import com.saborgourmet.gestion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByFechaEmisionBetween(LocalDateTime inicio, LocalDateTime fin);
}

