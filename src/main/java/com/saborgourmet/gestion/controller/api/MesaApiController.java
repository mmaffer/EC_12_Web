package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.dto.api.MesaResponse;
import com.saborgourmet.gestion.dto.api.request.MesaRequest;
import com.saborgourmet.gestion.model.Mesa;
import com.saborgourmet.gestion.model.enums.EstadoMesa;
import com.saborgourmet.gestion.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaApiController {

    private final MesaService mesaService;

    @GetMapping
    public List<MesaResponse> listar(@RequestParam(required = false) EstadoMesa estado) {
        List<Mesa> mesas = estado == null ? mesaService.listarTodas() : mesaService.listarPorEstado(estado);
        return mesas.stream().map(MesaResponse::from).toList();
    }

    @PostMapping
    public ResponseEntity<MesaResponse> registrar(@Valid @RequestBody MesaRequest request) {
        Mesa mesa = Mesa.builder()
                .numero(request.numero())
                .capacidad(request.capacidad())
                .estado(request.estado() == null ? EstadoMesa.DISPONIBLE : request.estado())
                .build();
        return ResponseEntity.ok(MesaResponse.from(mesaService.guardar(mesa)));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<MesaResponse> actualizarEstado(@PathVariable Long id, @RequestParam EstadoMesa estado) {
        return ResponseEntity.ok(MesaResponse.from(mesaService.cambiarEstado(id, estado)));
    }
}

