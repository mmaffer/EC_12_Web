package com.saborgourmet.gestion.model;

import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proveedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 11, nullable = false, unique = true)
    private String ruc;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 15)
    private String telefono;

    @Column(length = 120)
    private String correo;

    @Column(length = 200)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoRegistro estado;
}

