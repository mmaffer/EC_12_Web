package com.saborgourmet.gestion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoForm {

    @NotNull
    private Long mesaId;

    private Long clienteId;

    @NotNull
    private Long platoId;

    @NotNull
    @Min(1)
    private Integer cantidad;
}

