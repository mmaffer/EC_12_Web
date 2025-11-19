package com.saborgourmet.gestion.repository;

import com.saborgourmet.gestion.model.Mesa;
import com.saborgourmet.gestion.model.enums.EstadoMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByEstado(EstadoMesa estado);
}

