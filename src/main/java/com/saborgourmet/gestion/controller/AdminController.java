package com.saborgourmet.gestion.controller;

import com.saborgourmet.gestion.model.enums.Rol;
import com.saborgourmet.gestion.service.BitacoraService;
import com.saborgourmet.gestion.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UsuarioService usuarioService;
    private final BitacoraService bitacoraService;

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("roles", Rol.values());
        return "admin/usuarios";
    }

    @PostMapping("/usuarios")
    public String crearUsuario(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam Rol rol) {
        usuarioService.crearUsuario(username, password, rol);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/bitacora")
    public String bitacora(Model model) {
        model.addAttribute("bitacora", bitacoraService.listar());
        return "admin/bitacora";
    }
}

