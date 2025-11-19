package com.saborgourmet.gestion.controller;

import com.saborgourmet.gestion.dto.PedidoForm;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MOZO','COCINERO')")
public class PedidoController {

    private final PedidoService pedidoService;
    private final MesaService mesaService;
    private final ClienteService clienteService;
    private final PlatoService platoService;
    private final FacturaService facturaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.listar());
        model.addAttribute("pedidoForm", new PedidoForm());
        model.addAttribute("mesas", mesaService.listarDisponibles());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("platos", platoService.listar());
        model.addAttribute("estados", EstadoPedido.values());
        return "pedidos/lista";
    }

    @PostMapping
    public String registrar(@Valid @ModelAttribute("pedidoForm") PedidoForm pedidoForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pedidos", pedidoService.listar());
            model.addAttribute("mesas", mesaService.listarDisponibles());
            model.addAttribute("clientes", clienteService.listarClientes());
            model.addAttribute("platos", platoService.listar());
            model.addAttribute("estados", EstadoPedido.values());
            return "pedidos/lista";
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mesaService.obtenerPorId(pedidoForm.getMesaId()));
        if (pedidoForm.getClienteId() != null) {
            pedido.setCliente(clienteService.obtenerPorId(pedidoForm.getClienteId()));
        }

        DetallePedido detalle = DetallePedido.builder()
                .plato(platoService.obtenerPorId(pedidoForm.getPlatoId()))
                .cantidad(pedidoForm.getCantidad())
                .build();
        pedido.setDetalles(List.of(detalle));

        pedidoService.registrarPedido(pedido);
        return "redirect:/pedidos";
    }

    @PostMapping("/{id}/estado")
    public String actualizarEstado(@PathVariable Long id,
                                   @RequestParam EstadoPedido estado,
                                   @RequestParam(required = false) MetodoPago metodoPago,
                                   @RequestParam(defaultValue = "false") boolean pagado) {
        Pedido pedido = pedidoService.actualizarEstado(id, estado);
        if (estado == EstadoPedido.CERRADO && pedido.getFactura() == null) {
            facturaService.generarFactura(pedido, metodoPago == null ? MetodoPago.EFECTIVO : metodoPago, pagado);
        }
        return "redirect:/pedidos";
    }
}

