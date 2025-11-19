package com.saborgourmet.gestion.controller;

import com.saborgourmet.gestion.service.ClienteService;
import com.saborgourmet.gestion.service.FacturaService;
import com.saborgourmet.gestion.service.InsumoService;
import com.saborgourmet.gestion.service.MesaService;
import com.saborgourmet.gestion.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ClienteService clienteService;
    private final MesaService mesaService;
    private final PedidoService pedidoService;
    private final FacturaService facturaService;
    private final InsumoService insumoService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes().size());
        model.addAttribute("mesasDisponibles", mesaService.listarDisponibles().size());
        model.addAttribute("pedidosPendientes", pedidoService.listarPorEstado(com.saborgourmet.gestion.model.enums.EstadoPedido.PENDIENTE).size());
        model.addAttribute("ventas", facturaService.listar().size());
        model.addAttribute("alertas", insumoService.alertasStock().size());
        return "dashboard";
    }
}

