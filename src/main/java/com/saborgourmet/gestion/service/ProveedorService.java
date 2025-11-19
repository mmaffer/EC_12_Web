package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.model.Proveedor;
import com.saborgourmet.gestion.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    public Proveedor obtenerPorId(Long id) {
        return proveedorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
    }

    @Auditable(modulo = "Compras", accion = "Registrar/Actualizar proveedor")
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
}

