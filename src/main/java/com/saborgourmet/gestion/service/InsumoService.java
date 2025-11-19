package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.model.Insumo;
import com.saborgourmet.gestion.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }

    public List<Insumo> alertasStock() {
        return insumoRepository.findAll().stream()
                .filter(insumo -> insumo.getStock() != null && insumo.getStockMinimo() != null && insumo.getStock() <= insumo.getStockMinimo())
                .toList();
    }

    public Insumo obtenerPorId(Long id) {
        return insumoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Insumo no encontrado"));
    }

    @Auditable(modulo = "Inventario", accion = "Registrar/Actualizar insumo")
    public Insumo guardar(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    @Auditable(modulo = "Inventario", accion = "Actualizar stock insumo")
    public void actualizarStock(Long insumoId, double cantidad) {
        Insumo insumo = obtenerPorId(insumoId);
        insumo.setStock(insumo.getStock() + cantidad);
        insumoRepository.save(insumo);
    }
}

