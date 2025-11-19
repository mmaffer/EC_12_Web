package com.saborgourmet.gestion.service;

import com.saborgourmet.gestion.model.Bitacora;
import com.saborgourmet.gestion.model.Usuario;
import com.saborgourmet.gestion.repository.BitacoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BitacoraService {

    private final BitacoraRepository bitacoraRepository;

    public List<Bitacora> listar() {
        return bitacoraRepository.findAll();
    }

    public void registrar(String accion, String detalle, Usuario usuario) {
        Bitacora bitacora = Bitacora.builder()
                .accion(accion)
                .detalle(detalle)
                .usuario(usuario)
                .fechaHora(LocalDateTime.now())
                .build();
        bitacoraRepository.save(bitacora);
    }
}

