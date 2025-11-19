package com.saborgourmet.gestion.controller;

import com.saborgourmet.gestion.dto.CompraForm;
import com.saborgourmet.gestion.model.Compra;
import com.saborgourmet.gestion.model.DetalleCompra;
import com.saborgourmet.gestion.service.CompraService;
import com.saborgourmet.gestion.service.InsumoService;
import com.saborgourmet.gestion.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/inventario")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class InventarioController {

    private final InsumoService insumoService;
    private final CompraService compraService;
    private final ProveedorService proveedorService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("insumos", insumoService.listar());
        model.addAttribute("alertas", insumoService.alertasStock());
        model.addAttribute("proveedores", proveedorService.listar());
        model.addAttribute("compraForm", new CompraForm());
        model.addAttribute("compras", compraService.listar());
        return "inventario/dashboard";
    }

    @PostMapping("/compras")
    public String registrarCompra(@Valid @ModelAttribute("compraForm") CompraForm compraForm,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("insumos", insumoService.listar());
            model.addAttribute("alertas", insumoService.alertasStock());
            model.addAttribute("proveedores", proveedorService.listar());
            model.addAttribute("compras", compraService.listar());
            return "inventario/dashboard";
        }

        Compra compra = new Compra();
        compra.setProveedor(proveedorService.obtenerPorId(compraForm.getProveedorId()));
        DetalleCompra detalle = DetalleCompra.builder()
                .insumo(insumoService.obtenerPorId(compraForm.getInsumoId()))
                .cantidad(compraForm.getCantidad())
                .precioUnitario(compraForm.getPrecioUnitario())
                .build();
        compra.setDetalles(List.of(detalle));

        compraService.registrarCompra(compra);
        return "redirect:/inventario";
    }
}

