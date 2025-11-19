package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Cliente;
import com.saborgourmet.gestion.model.enums.EstadoRegistro;

public record ClienteResponse(
        Long id,
        String dni,
        String nombres,
        String apellidos,
        String telefono,
        String correo,
        EstadoRegistro estado
) {
    public static ClienteResponse from(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getDni(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getEstado()
        );
    }
}

