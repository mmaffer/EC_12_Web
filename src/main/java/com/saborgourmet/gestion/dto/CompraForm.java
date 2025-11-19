package com.saborgourmet.gestion.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CompraForm {

    @NotNull
    private Long proveedorId;

    @NotNull
    private Long insumoId;

    @NotNull
    @DecimalMin("0.1")
    private Double cantidad;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal precioUnitario;
}

