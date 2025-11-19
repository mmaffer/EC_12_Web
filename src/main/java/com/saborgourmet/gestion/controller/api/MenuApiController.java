package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.dto.api.PlatoResponse;
import com.saborgourmet.gestion.dto.api.request.PlatoRequest;
import com.saborgourmet.gestion.model.Plato;
import com.saborgourmet.gestion.service.PlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
@RequiredArgsConstructor
public class MenuApiController {

    private final PlatoService platoService;

    @GetMapping
    public List<PlatoResponse> listar() {
        return platoService.listar().stream().map(PlatoResponse::from).toList();
    }

    @PostMapping
    public ResponseEntity<PlatoResponse> registrar(@Valid @RequestBody PlatoRequest request) {
        Plato plato = Plato.builder()
                .nombre(request.nombre())
                .tipo(request.tipo())
                .precio(request.precio())
                .descripcion(request.descripcion())
                .estado(request.estado())
                .build();
        return ResponseEntity.ok(PlatoResponse.from(platoService.guardar(plato)));
    }
}

