package com.saborgourmet.gestion.controller;

import com.saborgourmet.gestion.model.Mesa;
import com.saborgourmet.gestion.model.enums.EstadoMesa;
import com.saborgourmet.gestion.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mesas")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MOZO')")
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mesas", mesaService.listarTodas());
        model.addAttribute("nuevaMesa", new Mesa());
        model.addAttribute("estados", EstadoMesa.values());
        return "mesas/lista";
    }

    @PostMapping
    public String guardar(@ModelAttribute("nuevaMesa") Mesa mesa) {
        mesaService.guardar(mesa);
        return "redirect:/mesas";
    }

    @PostMapping("/{id}/estado")
    public String actualizarEstado(@PathVariable Long id, @RequestParam EstadoMesa estado) {
        mesaService.cambiarEstado(id, estado);
        return "redirect:/mesas";
    }
}

