package com.saborgourmet.gestion.controller;

import com.saborgourmet.gestion.model.Cliente;
import com.saborgourmet.gestion.service.ClienteService;
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

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MOZO')")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("cliente", new Cliente());
        return "clientes/lista";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarClientes());
            return "clientes/lista";
        }
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestParam boolean activo) {
        clienteService.cambiarEstado(id, activo);
        return "redirect:/clientes";
    }
}

