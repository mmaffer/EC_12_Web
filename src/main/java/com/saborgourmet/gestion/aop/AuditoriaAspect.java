package com.saborgourmet.gestion.aop;

import com.saborgourmet.gestion.model.Usuario;
import com.saborgourmet.gestion.service.BitacoraService;
import com.saborgourmet.gestion.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditoriaAspect {

    private final BitacoraService bitacoraService;
    private final UsuarioService usuarioService;

    @AfterReturning("@annotation(auditable)")
    public void registrarAccion(JoinPoint joinPoint, Auditable auditable) {
        String username = obtenerUsuarioActual();
        Usuario usuario = username == null ? null : usuarioService.buscarPorNombreUsuario(username);
        String accion = auditable.modulo() + " - " + auditable.accion();
        String detalle = joinPoint.getSignature().toShortString();
        bitacoraService.registrar(accion, detalle, usuario);
        log.info("Auditoría registrada: {} por {}", accion, username == null ? "Sistema" : username);
    }

    private String obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }
}

