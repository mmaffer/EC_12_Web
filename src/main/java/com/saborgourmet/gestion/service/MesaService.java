package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.model.Mesa;
import com.saborgourmet.gestion.model.enums.EstadoMesa;
import com.saborgourmet.gestion.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository mesaRepository;

    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    public List<Mesa> listarDisponibles() {
        return mesaRepository.findByEstado(EstadoMesa.DISPONIBLE);
    }

    public List<Mesa> listarPorEstado(EstadoMesa estadoMesa) {
        return mesaRepository.findByEstado(estadoMesa);
    }

    public Mesa obtenerPorId(Long id) {
        return mesaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
    }

    @Auditable(modulo = "Mesas", accion = "Registrar/Actualizar mesa")
    public Mesa guardar(Mesa mesa) {
        if (mesa.getEstado() == null) {
            mesa.setEstado(EstadoMesa.DISPONIBLE);
        }
        return mesaRepository.save(mesa);
    }

    @Auditable(modulo = "Mesas", accion = "Cambiar estado de mesa")
    public Mesa cambiarEstado(Long id, EstadoMesa estadoMesa) {
        Mesa mesa = obtenerPorId(id);
        mesa.setEstado(estadoMesa);
        return mesaRepository.save(mesa);
    }
}

