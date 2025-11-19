package com.saborgourmet.gestion.repository;

import com.saborgourmet.gestion.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    List<Insumo> findByStockLessThanEqual(Double stockMinimo);
}

