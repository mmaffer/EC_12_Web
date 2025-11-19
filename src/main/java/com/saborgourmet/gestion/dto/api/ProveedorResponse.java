package com.saborgourmet.gestion.dto.api;

import com.saborgourmet.gestion.model.Proveedor;
import com.saborgourmet.gestion.model.enums.EstadoRegistro;

public record ProveedorResponse(
        Long id,
        String ruc,
        String nombre,
        String telefono,
        String correo,
        String direccion,
        EstadoRegistro estado
) {
    public static ProveedorResponse from(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.getId(),
                proveedor.getRuc(),
                proveedor.getNombre(),
                proveedor.getTelefono(),
                proveedor.getCorreo(),
                proveedor.getDireccion(),
                proveedor.getEstado()
        );
    }
}

