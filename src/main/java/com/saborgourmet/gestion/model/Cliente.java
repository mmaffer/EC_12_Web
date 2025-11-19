package com.saborgourmet.gestion.model;

import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 8, max = 8)
    @Column(length = 8, nullable = false, unique = true)
    private String dni;

    @Column(nullable = false, length = 80)
    @NotBlank
    private String nombres;

    @Column(nullable = false, length = 80)
    @NotBlank
    private String apellidos;

    @Column(length = 15)
    private String telefono;

    @Column(length = 120)
    @Email
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoRegistro estado;
}

