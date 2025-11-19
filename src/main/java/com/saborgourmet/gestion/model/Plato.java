package com.saborgourmet.gestion.model;

import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import com.saborgourmet.gestion.model.enums.TipoPlato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "platos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPlato tipo;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(length = 255)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoRegistro estado;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<PlatoInsumo> insumos = new HashSet<>();
}

