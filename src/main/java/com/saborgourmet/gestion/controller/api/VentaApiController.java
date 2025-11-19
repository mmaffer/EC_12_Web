package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.dto.api.FacturaResponse;
import com.saborgourmet.gestion.dto.api.request.FacturaEstadoRequest;
import com.saborgourmet.gestion.model.enums.EstadoFactura;
import com.saborgourmet.gestion.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class VentaApiController {

    private final FacturaService facturaService;

    @GetMapping
    public List<FacturaResponse> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        if (inicio != null && fin != null) {
            return facturaService.listarPorRango(inicio, fin).stream().map(FacturaResponse::from).toList();
        }
        return facturaService.listar().stream().map(FacturaResponse::from).toList();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<FacturaResponse> actualizarEstado(@PathVariable Long id, @Valid @RequestBody FacturaEstadoRequest request) {
        if (request.estado() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(FacturaResponse.from(facturaService.actualizarEstado(id, request.estado())));
    }
}

