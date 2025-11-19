package com.saborgourmet.gestion.controller;

import com.saborgourmet.gestion.model.Plato;
import com.saborgourmet.gestion.model.enums.TipoPlato;
import com.saborgourmet.gestion.service.PlatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','COCINERO')")
public class MenuController {

    private final PlatoService platoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("platos", platoService.listar());
        model.addAttribute("plato", new Plato());
        model.addAttribute("tipos", TipoPlato.values());
        return "menu/lista";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("plato") Plato plato, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("platos", platoService.listar());
            model.addAttribute("tipos", TipoPlato.values());
            return "menu/lista";
        }
        platoService.guardar(plato);
        return "redirect:/menu";
    }
}

