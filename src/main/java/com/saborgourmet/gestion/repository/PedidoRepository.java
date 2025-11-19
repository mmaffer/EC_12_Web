package com.saborgourmet.gestion.repository;

import com.saborgourmet.gestion.model.Pedido;
import com.saborgourmet.gestion.model.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstado(EstadoPedido estado);
}

