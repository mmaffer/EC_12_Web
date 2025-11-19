package com.saborgourmet.gestion.controller.api;

import com.saborgourmet.gestion.dto.api.DashboardSummaryResponse;
import com.saborgourmet.gestion.model.enums.EstadoMesa;
import com.saborgourmet.gestion.model.enums.EstadoPedido;
import com.saborgourmet.gestion.service.ClienteService;
import com.saborgourmet.gestion.service.FacturaService;
import com.saborgourmet.gestion.service.InsumoService;
import com.saborgourmet.gestion.service.MesaService;
import com.saborgourmet.gestion.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardApiController {

    private final ClienteService clienteService;
    private final MesaService mesaService;
    private final PedidoService pedidoService;
    private final FacturaService facturaService;
    private final InsumoService insumoService;

    @GetMapping("/summary")
    public DashboardSummaryResponse obtenerResumen() {
        long clientes = clienteService.listarClientes().size();
        long mesasDisponibles = mesaService.listarPorEstado(EstadoMesa.DISPONIBLE).size();
        long pedidosPendientes = pedidoService.listarPorEstado(EstadoPedido.PENDIENTE).size();
        long ventas = facturaService.listar().size();
        long alertas = insumoService.alertasStock().size();
        return new DashboardSummaryResponse(clientes, mesasDisponibles, pedidosPendientes, ventas, alertas);
    }
}

