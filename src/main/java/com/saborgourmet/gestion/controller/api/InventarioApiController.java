package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.dto.api.CompraResponse;
import com.saborgourmet.gestion.dto.api.InsumoResponse;
import com.saborgourmet.gestion.dto.api.ProveedorResponse;
import com.saborgourmet.gestion.dto.api.request.CompraRequest;
import com.saborgourmet.gestion.model.Compra;
import com.saborgourmet.gestion.model.DetalleCompra;
import com.saborgourmet.gestion.service.CompraService;
import com.saborgourmet.gestion.service.InsumoService;
import com.saborgourmet.gestion.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioApiController {

    private final InsumoService insumoService;
    private final ProveedorService proveedorService;
    private final CompraService compraService;

    @GetMapping("/insumos")
    public List<InsumoResponse> listarInsumos() {
        return insumoService.listar().stream().map(InsumoResponse::from).toList();
    }

    @GetMapping("/insumos/alertas")
    public List<InsumoResponse> alertas() {
        return insumoService.alertasStock().stream().map(InsumoResponse::from).toList();
    }

    @GetMapping("/proveedores")
    public List<ProveedorResponse> proveedores() {
        return proveedorService.listar().stream().map(ProveedorResponse::from).toList();
    }

    @GetMapping("/compras")
    @Transactional(readOnly = true)
    public List<CompraResponse> compras() {
        return compraService.listar().stream().map(CompraResponse::from).toList();
    }

    @PostMapping("/compras")
    public ResponseEntity<CompraResponse> registrar(@Valid @RequestBody CompraRequest request) {
        Compra compra = new Compra();
        compra.setProveedor(proveedorService.obtenerPorId(request.proveedorId()));
        List<DetalleCompra> detalles = request.detalles().stream()
                .map(item -> DetalleCompra.builder()
                        .insumo(insumoService.obtenerPorId(item.insumoId()))
                        .cantidad(item.cantidad())
                        .precioUnitario(item.precioUnitario())
                        .build())
                .toList();
        compra.setDetalles(detalles);
        Compra guardada = compraService.registrarCompra(compra);
        return ResponseEntity.ok(CompraResponse.from(guardada));
    }
}

