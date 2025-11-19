package com.saborgourmet.gestion.controller;

import com.saborgourmet.gestion.model.enums.EstadoFactura;
import com.saborgourmet.gestion.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','CAJERO')")
public class VentaController {

    private final FacturaService facturaService;

    @GetMapping
    public String listar(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                         Model model) {
        if (inicio != null && fin != null) {
            model.addAttribute("facturas", facturaService.listarPorRango(inicio, fin));
        } else {
            model.addAttribute("facturas", facturaService.listar());
        }
        model.addAttribute("inicio", inicio);
        model.addAttribute("fin", fin);
        model.addAttribute("estados", EstadoFactura.values());
        return "ventas/lista";
    }

    @PostMapping("/{id}/estado")
    public String actualizarEstado(@PathVariable Long id, @RequestParam EstadoFactura estado) {
        facturaService.actualizarEstado(id, estado);
        return "redirect:/ventas";
    }
}

