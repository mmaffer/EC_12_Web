package com.saborgourmet.gestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plato_insumos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plato_id")
    private Plato plato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    private Double cantidadUsada;
}

