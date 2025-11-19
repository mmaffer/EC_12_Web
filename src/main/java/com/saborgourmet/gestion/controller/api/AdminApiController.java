package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.dto.api.BitacoraResponse;
import com.saborgourmet.gestion.dto.api.UsuarioResponse;
import com.saborgourmet.gestion.dto.api.request.UsuarioRequest;
import com.saborgourmet.gestion.service.BitacoraService;
import com.saborgourmet.gestion.service.UsuarioService;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final UsuarioService usuarioService;
    private final BitacoraService bitacoraService;

    @GetMapping("/usuarios")
    public List<UsuarioResponse> usuarios() {
        return usuarioService.listar().stream().map(UsuarioResponse::from).toList();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(UsuarioResponse.from(
                usuarioService.crearUsuario(request.username(), request.password(), request.rol())
        ));
    }

    @GetMapping("/bitacora")
    public List<BitacoraResponse> bitacora() {
        return bitacoraService.listar().stream().map(BitacoraResponse::from).toList();
    }
}

