package com.saborgourmet.gestion.config;

import com.saborgourmet.gestion.model.Insumo;
import com.saborgourmet.gestion.model.Mesa;
import com.saborgourmet.gestion.model.Plato;
import com.saborgourmet.gestion.model.PlatoInsumo;
import com.saborgourmet.gestion.model.enums.EstadoMesa;
import com.saborgourmet.gestion.model.enums.EstadoRegistro;
import com.saborgourmet.gestion.model.enums.Rol;
import com.saborgourmet.gestion.model.enums.TipoPlato;
import com.saborgourmet.gestion.repository.InsumoRepository;
import com.saborgourmet.gestion.repository.MesaRepository;
import com.saborgourmet.gestion.repository.PlatoRepository;
import com.saborgourmet.gestion.repository.UsuarioRepository;
import com.saborgourmet.gestion.repository.ProveedorRepository;
import com.saborgourmet.gestion.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final MesaRepository mesaRepository;
    private final InsumoRepository insumoRepository;
    private final PlatoRepository platoRepository;
    private final ProveedorRepository proveedorRepository;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioService.crearUsuario("admin", "admin123", Rol.ADMIN);
            usuarioService.crearUsuario("mozo", "mozo123", Rol.MOZO);
            usuarioService.crearUsuario("cocinero", "cocina123", Rol.COCINERO);
            usuarioService.crearUsuario("cajero", "caja123", Rol.CAJERO);
        }

        if (proveedorRepository.count() == 0) {
            proveedorRepository.save(com.saborgourmet.gestion.model.Proveedor.builder()
                    .ruc("20112233445")
                    .nombre("Distribuidora Andina")
                    .telefono("987654321")
                    .correo("ventas@andina.com")
                    .direccion("Av. Siempre Viva 123")
                    .estado(EstadoRegistro.ACTIVO)
                    .build());
        }

        if (mesaRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                mesaRepository.save(Mesa.builder()
                        .numero(i)
                        .capacidad(4)
                        .estado(EstadoMesa.DISPONIBLE)
                        .build());
            }
        }

        if (insumoRepository.count() == 0) {
            List<Insumo> insumos = List.of(
                    Insumo.builder().nombre("Pollo").unidadMedida("kg").stock(20.0).stockMinimo(5.0).precioCompra(BigDecimal.valueOf(12)).estado(EstadoRegistro.ACTIVO).build(),
                    Insumo.builder().nombre("Papas").unidadMedida("kg").stock(50.0).stockMinimo(10.0).precioCompra(BigDecimal.valueOf(4)).estado(EstadoRegistro.ACTIVO).build(),
                    Insumo.builder().nombre("Arroz").unidadMedida("kg").stock(30.0).stockMinimo(8.0).precioCompra(BigDecimal.valueOf(6)).estado(EstadoRegistro.ACTIVO).build()
            );
            insumoRepository.saveAll(insumos);
        }

        if (platoRepository.count() == 0) {
            List<Insumo> insumos = insumoRepository.findAll();
            Plato polloPlancha = Plato.builder()
                    .nombre("Pollo a la plancha")
                    .tipo(TipoPlato.FONDO)
                    .precio(BigDecimal.valueOf(25))
                    .descripcion("Pollo con ensalada y papas")
                    .estado(EstadoRegistro.ACTIVO)
                    .build();
            PlatoInsumo pollo = PlatoInsumo.builder()
                    .plato(polloPlancha)
                    .insumo(insumos.get(0))
                    .cantidadUsada(0.25)
                    .build();
            PlatoInsumo papas = PlatoInsumo.builder()
                    .plato(polloPlancha)
                    .insumo(insumos.get(1))
                    .cantidadUsada(0.3)
                    .build();
            polloPlancha.getInsumos().addAll(List.of(pollo, papas));
            platoRepository.save(polloPlancha);
        }
    }
}

