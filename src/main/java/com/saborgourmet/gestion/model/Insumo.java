package com.saborgourmet.gestion.model;

import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "insumos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(length = 40)
    private String unidadMedida;

    private Double stock;

    private Double stockMinimo;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoRegistro estado;
}

