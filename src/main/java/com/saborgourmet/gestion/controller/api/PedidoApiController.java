package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.dto.api.PedidoResponse;
import com.saborgourmet.gestion.dto.api.request.PedidoEstadoRequest;
import com.saborgourmet.gestion.dto.api.request.PedidoRequest;
import com.saborgourmet.gestion.model.DetallePedido;
import com.saborgourmet.gestion.model.Pedido;
import com.saborgourmet.gestion.model.enums.EstadoPedido;
import com.saborgourmet.gestion.model.enums.MetodoPago;
import com.saborgourmet.gestion.service.ClienteService;
import com.saborgourmet.gestion.service.FacturaService;
import com.saborgourmet.gestion.service.MesaService;
import com.saborgourmet.gestion.service.PedidoService;
import com.saborgourmet.gestion.service.PlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoApiController {

    private final PedidoService pedidoService;
    private final MesaService mesaService;
    private final ClienteService clienteService;
    private final PlatoService platoService;
    private final FacturaService facturaService;

    @GetMapping
    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return pedidoService.listar().stream()
                .map(PedidoResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> registrar(@Valid @RequestBody PedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setMesa(mesaService.obtenerPorId(request.mesaId()));
        if (request.clienteId() != null) {
            pedido.setCliente(clienteService.obtenerPorId(request.clienteId()));
        }
        List<DetallePedido> detalles = request.items().stream()
                .map(item -> DetallePedido.builder()
                        .plato(platoService.obtenerPorId(item.platoId()))
                        .cantidad(item.cantidad())
                        .build())
                .toList();
        pedido.setDetalles(detalles);
        Pedido guardado = pedidoService.registrarPedido(pedido);
        Pedido completo = pedidoService.obtenerPorId(guardado.getId());
        return ResponseEntity.ok(PedidoResponse.from(completo));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponse> actualizarEstado(@PathVariable Long id, @Valid @RequestBody PedidoEstadoRequest request) {
        Pedido pedido = pedidoService.actualizarEstado(id, request.estado());
        if (request.estado() == EstadoPedido.CERRADO && pedido.getFactura() == null) {
            MetodoPago metodoPago = request.metodoPago() == null ? MetodoPago.EFECTIVO : request.metodoPago();
            facturaService.generarFactura(pedido, metodoPago, request.pagado());
            pedido = pedidoService.obtenerPorId(id);
        }
        return ResponseEntity.ok(PedidoResponse.from(pedido));
    }
}

