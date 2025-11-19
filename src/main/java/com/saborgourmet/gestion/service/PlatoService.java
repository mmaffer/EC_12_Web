package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.model.Plato;
import com.saborgourmet.gestion.repository.PlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatoService {

    private final PlatoRepository platoRepository;

    public List<Plato> listar() {
        return platoRepository.findAll();
    }

    public Plato obtenerPorId(Long id) {
        return platoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Plato no encontrado"));
    }

    @Auditable(modulo = "Menu", accion = "Registrar/Actualizar plato")
    public Plato guardar(Plato plato) {
        return platoRepository.save(plato);
    }
}

