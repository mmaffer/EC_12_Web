package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.aop.Auditable;
import com.saborgourmet.gestion.model.Cliente;
import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import com.saborgourmet.gestion.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    @Auditable(modulo = "Clientes", accion = "Registrar/Actualizar cliente")
    @Transactional
    public Cliente guardarCliente(Cliente cliente) {
        if (cliente.getEstado() == null) {
            cliente.setEstado(EstadoRegistro.ACTIVO);
        }
        return clienteRepository.save(cliente);
    }

    @Auditable(modulo = "Clientes", accion = "Cambiar estado cliente")
    @Transactional
    public void cambiarEstado(Long id, boolean activo) {
        Cliente cliente = obtenerPorId(id);
        cliente.setEstado(activo ? EstadoRegistro.ACTIVO : EstadoRegistro.INACTIVO);
        clienteRepository.save(cliente);
    }
}

