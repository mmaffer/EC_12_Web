package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.dto.api.ClienteResponse;
import com.saborgourmet.gestion.dto.api.request.ClienteRequest;
import com.saborgourmet.gestion.model.Cliente;
import com.saborgourmet.gestion.service.ClienteService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteApiController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listarClientes().stream()
                .map(ClienteResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> registrar(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .dni(request.dni())
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .telefono(request.telefono())
                .correo(request.correo())
                .build();
        Cliente guardado = clienteService.guardarCliente(cliente);
        return ResponseEntity.ok(ClienteResponse.from(guardado));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam boolean activo) {
        clienteService.cambiarEstado(id, activo);
        return ResponseEntity.noContent().build();
    }
}

